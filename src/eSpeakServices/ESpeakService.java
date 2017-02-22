package eSpeakServices;

public class ESpeakService {
	
	static {
        System.loadLibrary("eSpeakService");
    }
	
	public native String getEspeakNgVersion();  

	//for testing
	public static void main(String[] args) {
	      System.out.print("Jei!\n");  // invoke the native method
	}
}
