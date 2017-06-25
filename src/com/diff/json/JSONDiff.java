package com.diff.json;

public class JSONDiff {

	private JSON left;
	private JSON right;
	
	public JSONDiff() {

	}

	public void setLeft(JSON left) {
		this.left = left;
	}

	public void setRight(JSON right) {
		this.right = right;
	}

	public DiffResult compare() {
		if (left == null && right == null) {
			return new DiffResult(DiffResult.Status.MISSING_BOTH_SIDES, null);
		} else if (left != null && right == null) {
			return new DiffResult(DiffResult.Status.MISSING_RIGHT_SIDE, null);
		} else if (left == null && right != null) {
			return new DiffResult(DiffResult.Status.MISSING_LEFT_SIDE, null);
		}else {
			Boolean isEqual = left.equals(right);

			if (isEqual) {
				return new DiffResult(DiffResult.Status.EQUALS, null);
			} else {
				return new DiffResult(DiffResult.Status.NOT_EQUALS, "position 1");
			}
		}
	}

}
