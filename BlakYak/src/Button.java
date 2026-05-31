import java.awt.event.MouseEvent;

public class Button {
	int x, y, bigX, bigY;
	boolean isOn;

	public Button() {
		x = 0;
		y = 0;
		bigX = 0;
		bigY = 0;
		isOn = true;
	}

	public Button(int x1, int y1, int bigX1, int bigY1, boolean toggleOn) {
		x = x1;
		y = y1;
		bigX = bigX1;
		bigY = bigY1;
		isOn = toggleOn;
	}

	public boolean isClicked(MouseEvent me, int clickX, int clickY) {
		if (isOn == false) {
			return false;
		}
		if (clickX >= x && clickX <= bigX && clickY >= y && clickY <= bigY) {
			return true;
		}
		return false;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getbigX() {
		return bigX;
	}

	public void setbigX(int bigX) {
		this.bigX = bigX;
	}

	public int getbigY() {
		return bigY;
	}

	public void setbigY(int bigY) {
		this.bigY = bigY;
	}
}