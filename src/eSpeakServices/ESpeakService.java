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
		
		/*String s = nativeGetEspeakNgVersion();
		
		System.out.print(s + "\n");	
		
		SpectSeq spect = new SpectSeq();
		
		System.out.print("Returned with: " + nativeGetSpectSeq(spect, "/home/marcis/workspace-c/espeak-ng/phsource/vowel/a") + "\n");
		
		System.out.print("Frame 1 nx:" + spect.frames[1].nx + "\n");
		
		System.out.print("Frame 1 klaat_param[0]:" + spect.frames[1].klaat_param[0] + "\n");
		
		System.out.print("Frame 1 formant 1 bandw:" + spect.frames[1].formants[1].bandw + "\n");
		
		System.out.print("Frame 1 peak 1 pkheight:" + spect.frames[1].peaks[1].pkheight + "\n");

		System.out.print("Jei!\n");  */
		
	}
}
