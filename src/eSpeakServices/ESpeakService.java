package eSpeakServices;

import dataStructure.eSpeakStructure.SpectSeq;

public class ESpeakService {
	
	static {
		try {
			System.load(System.getProperty("user.dir") + "/jni/lib/libespeakservice.so");
		} catch (UnsatisfiedLinkError e) {
			System.out.println("Native code library failed to load.\n" + e);
		    System.exit(1);
		}
    }
	
	public static native String nativeGetEspeakNgVersion();  

	public static native int nativeGetSpectSeq(SpectSeq spect, String fileName);

	//for testing
	public static void main(String[] args) {
		String s = nativeGetEspeakNgVersion();
		System.out.print(s + "\n");	
		
		SpectSeq spect = new SpectSeq();
		
		System.out.print("Amplitude:" + spect.amplitude + "\n");
		
		nativeGetSpectSeq(spect, "/home/student/workspace-c/espeak-ng/phsource/vowel/a");
		
		System.out.print("Name:" + spect.name + "\n");
		
		System.out.print("Amplitude:" + spect.amplitude + "\n");
		
		System.out.print("Max_y:" + spect.max_y + "\n");

		System.out.print("Jei!\n");  
	}
}
