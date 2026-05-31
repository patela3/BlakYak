public class Cards {
	private int value;
	private String suite;

	public Cards() {
		value = (int) (Math.random() * 13) + 2;
		int temp = (int) (Math.random() * 4);

		if (temp == 0) {
			suite = "Spade";
		} else if (temp == 1) {

			suite = "Diamond";
		} else if (temp == 2) {

			suite = "Club";
		} else {
			suite = "Heart";
		}
	}

	public Cards(int val, String su) {
		suite = su;
		value = val;
	}

	public Cards(int val, int su) {
		if (su == 1) {
			suite = "Spade";
		} else if (su == 2) {

			suite = "Diamond";
		} else if (su == 3) {

			suite = "Club";
		} else {
			suite = "Heart";
		}
		value = val;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getSuite() {
		return suite;
	}

	public void setSuite(String suite) {
		this.suite = suite;
	}

}