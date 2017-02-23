package eSpeakServices;

public class ESpeakService {
	
	static {
		try {
			System.load("/home/marcis/workspace/eSpeak-Jedit/jni/lib/libespeakservice.so");
		} catch (UnsatisfiedLinkError e) {
			System.out.println("Native code library failed to load.\n" + e);
		    System.exit(1);
		}
    }
	
	public static native String nativeGetEspeakNgVersion();  

	//for testing
	public static void main(String[] args) {
		String s = nativeGetEspeakNgVersion();
		System.out.print(s);	
		System.out.print("Jei!\n");  
	}
}
