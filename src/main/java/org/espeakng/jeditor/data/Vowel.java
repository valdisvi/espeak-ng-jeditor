package org.espeakng.jeditor.data;


public class Vowel {
	
	private String name;
	private int b;
	private int Xb;
	private int Yb;
	private double max1;
	private int Xe;
	private int Ye;
	private double max2;
		
	public Vowel(String name, int b, int y0, int x0, double max1, int y1, int x1, double max2 ){
		this.name = name;
		this.b = b;
		Xb = x0;
		Yb = y0;
		this.max1 = max1;
		Xe = x1;
		Ye = y1;
		this.max2 = max2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getXb() {
		return Xb;
	}

	public void setXb(int xb) {
		Xb = xb;
	}

	public int getYb() {
		return Yb;
	}

	public void setYb(int yb) {
		Yb = yb;
	}

	public int getXe() {
		return Xe;
	}

	public void setXe(int xe) {
		Xe = xe;
	}

	public int getYe() {
		return Ye;
	}

	public void setYe(int ye) {
		Ye = ye;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public double getMax1() {
		return max1;
	}

	public void setMax1(double max1) {
		this.max1 = max1;
	}

	public double getMax2() {
		return max2;
	}

	public void setMax2(double max2) {
		this.max2 = max2;
	}
}
