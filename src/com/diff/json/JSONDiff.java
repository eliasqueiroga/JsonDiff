package com.diff.json;

import java.util.ArrayList;
import java.util.List;

public class JSONDiff {

	private BinaryJSON left;
	private BinaryJSON right;

	public JSONDiff() {

	}

	public void setLeft(BinaryJSON left) {
		this.left = left;
	}

	public void setRight(BinaryJSON right) {
		this.right = right;
	}

	public DiffResult compare() {
		if (left == null && right == null) {
			return new DiffResult(DiffResult.Status.MISSING_BOTH_SIDES, null);
		} else if (left != null && right == null) {
			return new DiffResult(DiffResult.Status.MISSING_RIGHT_SIDE, null);
		} else if (left == null && right != null) {
			return new DiffResult(DiffResult.Status.MISSING_LEFT_SIDE, null);
		} else {
			Boolean isEqual = left.equals(right);

			byte[] leftJsonBytes = left.getBytes();
			byte[] rightJesonBytes = right.getBytes();

			List<Integer> offsets = new ArrayList<Integer>();

			if (isEqual) {
				for (int i = 0; i < leftJsonBytes.length; i++) {
					if (!(new Byte(leftJsonBytes[i]).equals(new Byte(rightJesonBytes[i])))) {
						offsets.add(i);
					}
				}
				
				DiffResult diffResult = null;
				
				if (offsets.size() > 0){
					diffResult = new DiffResult(DiffResult.Status.SIZE_EQUAL_WITH_DIFFERENT_CONTENT, offsets);
				}else{
					diffResult = new DiffResult(DiffResult.Status.SIZE_EQUAL, null);
				}
				
				return diffResult;				
			}else{
				return new DiffResult(DiffResult.Status.NOT_SIZE_EQUALS, null);
			}
		}
	}

}
