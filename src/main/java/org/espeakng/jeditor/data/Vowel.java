package org.espeakng.jeditor.data;


public class Vowel {
	
	private String name;
	private int b;
	private int Xb;
	private int Yb;
	private int max1;
	private int Xe;
	private int Ye;
	private int max2;
		
	public Vowel(String name, int b, int y0, int x0, int max1, int y1, int x1, int max2 ){
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
}
