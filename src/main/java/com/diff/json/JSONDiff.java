package com.diff.json;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is able to compare two different Jsons (represented by BinaryJSON class) and 
 * give details of this comparison. 
 * 
 * Before comparing both jsons (left and Right sides) must be informed, the order does not matter.
 * After that the method compare can be invoked returned an instance of JSONDiffResult with information
 * about the comparison such as:
 * 
 *  - MISSING_BOTH_SIDES: If no jsons are informed before performing the comparison.
 *  - MISSING_RIGHT_SIDE: If only left json has been informed.
 *  - MISSING_LEFT_SIDE: If only right json has been informed.
 *  - SIZE_EQUAL: If both jsons have the same size.
 *  - SIZE_EQUAL_WITH_DIFFERENT_CONTENT: If both jsons have the same size, but the content is different.
 *  - NOT_SIZE_EQUALS: If jsons do not have the same size.
 *  
 * 
 * @author Elias
 */
public class JSONDiff {

	private BinaryJSON left;
	private BinaryJSON right;

	public JSONDiff() {

	}

	/**
	 * Inform the left json to be compared.
	 * 
	 * @param left An instance of BinaryJSON 
	 */
	public void setLeft(BinaryJSON left) {
		this.left = left;
	}

	/**
	 * Inform the right json to be compared.
	 * 
	 * @param right An instance of BinaryJSON
	 */
	public void setRight(BinaryJSON right) {
		this.right = right;
	}

	/**
	 * Execute the comparison of jsons informed in setLeft and setRight
	 * methods. 
	 * 
	 * First, this method verifies if both left and right jsons have been informed.
	 * If one of them is missing, the method stops the execution informing that one 
	 * side is missing.
	 * 
	 * If all jsons are present, their sizes are verified. If they have different sizes, 
	 * the method returns the result saying that both are different.
	 * 
	 * However, if they have the same size, their content are checked and if something is
	 * different, data offset is added to a list and set in the JSONDiffResult.
	 * 
	 * @return An instance of 
	 */
	public JSONDiffResult compare() {
		if (left == null && right == null) {
			return new JSONDiffResult(JSONDiffResult.Status.MISSING_BOTH_SIDES, null);
		} else if (left != null && right == null) {
			return new JSONDiffResult(JSONDiffResult.Status.MISSING_RIGHT_SIDE, null);
		} else if (left == null && right != null) {
			return new JSONDiffResult(JSONDiffResult.Status.MISSING_LEFT_SIDE, null);
		} else {
			Boolean isEqual = left.equals(right);

			byte[] leftJsonBytes = left.getBytes();
			byte[] rightJesonBytes = right.getBytes();

			List<Integer> offsets = new ArrayList<Integer>();

			//Checking if the arrays are equal
			if (isEqual) {				
				//If so, verifying the array contents
				for (int i = 0; i < leftJsonBytes.length; i++) {
					if (!(new Byte(leftJsonBytes[i]).equals(new Byte(rightJesonBytes[i])))) {
						offsets.add(i);
					}
				}
				
				JSONDiffResult diffResult = null;
				
				if (offsets.size() > 0){
					diffResult = new JSONDiffResult(JSONDiffResult.Status.SIZE_EQUAL_WITH_DIFFERENT_CONTENT, offsets);
				}else{
					diffResult = new JSONDiffResult(JSONDiffResult.Status.SIZE_EQUAL, null);
				}
				
				return diffResult;				
			}else{
				//If not, return saying the jsons are different. 
				return new JSONDiffResult(JSONDiffResult.Status.NOT_SIZE_EQUAL, null);
			}
		}
	}

}
