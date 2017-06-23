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
		if (left == null || right == null) {
			return new DiffResult("Both sides must be informed to be compared",
					(left == null ? "Left" : "Right") + " side must be informed");
		} else {
			Boolean isEqual = left.equals(right);

			if (isEqual) {
				return new DiffResult("Both sides are equal", null);
			} else {
				return new DiffResult("Both sides are not equal", "position 1");
			}
		}
	}

}
