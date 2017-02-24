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
	
	// function returns 0 if was succesful, otherwise it returns non 0 value
	public static native int nativeGetSpectSeq(SpectSeq spect, String fileName);
	
	// TODO implement this in jni/src/eSpeakService.c
	public static native String nativeTextToPhonemes(String textToTranslate);

	//for testing
	public static void main(String[] args) {

	}
}
