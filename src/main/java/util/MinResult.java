package util;

public class MinResult {

	private int row;
	private int col;
	private double value;
	public MinResult(int c, int s, double value) {
		this.row=c;
		this.col=s;
		this.value=value;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public double getValue() {
		return value;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public void setValue(double value) {
		this.value = value;
	}
}
