package org.espeakng.jeditor.jni;

/**
 * 	EspeakService class is used to call native EspeakNG functions
 * 		EspeakService loads libespeakservice.so library witch calls EspeakNG public library functions and 
 *  	use JNI to return values back to this Java class. At this stage libespeakservice.so library
 *  	is written "by hand". 
 * 		
 * 		If this class is changed there should be according changes made to /jni/src/eSpeakService.c and
 * 		libespeakservice.so should be recompiled. To ease this task there is bash script /jni/updateJNIchanges.sh
 * 		Before executing this script for the first time there should be path variables updated in it. It is normal 
 * 		if it shows some warnings for EspeakNG code part (.. espeak-ng/src/libespeak-ng/event.h:66:28: warning: ..).  
 * 
 * 		IMPORTANT! 
 * 		As EspeakNG LoadSpectSeq function was not in its public API ( and there are some other useful functions missing there), 
 * 		to be able to call this function, changes was made to original EspeakNG code ( LoadSpectSeq function was added to its 
 * 		public API). If EspeakNG was cloned from its original repo, those changes should be added:
 * 		in espeak-ng/src/libespeak-ng/spect.c  
 * 		============================================
 * 		...
 * 		#pragma GCC visibility push(default) // this line should be added to expose functions to public API
 * 		SpectSeq *SpectSeqCreate() 
 * 		{
 * 			...
 * 		}
 * 		
 * 		void SpectSeqDestroy(SpectSeq *spect)
 * 		{
 * 			...
 * 		}
 *		#pragma GCC visibility pop 			// this line should be added to stop exposing to public API 
 *		...
 *		#pragma GCC visibility push(default) //this line should be added to expose functions to public API
 *		espeak_ng_STATUS LoadSpectSeq(SpectSeq *spect, const char *filename)
 *		{
 *			...
 *		}
 *		#pragma GCC visibility pop			// this line should be added to stop exposing to public API
 *		===============================================
 *		After changes have been made, EspeakNG should be rebuild ( just rerun espeak-ng-rebuild-script.sh script ). 
 * 		
 * */
public class ESpeakService {

	static {
		try {
			System.load(System.getProperty("user.dir") + "/.lib/libespeakservice.so");
//			System.load(System.getProperty("user.dir") + "/src/main/resources/eSpeakService.o");
			// System.out.println(System.getProperty("user.dir") +
			// "/lib/libespeakservice.so");
		} catch (UnsatisfiedLinkError e) {
			System.out.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}

	/** Function returns string with Espeak-NG version */
	public static native String nativeGetEspeakNgVersion();  
	
	/** Function loads SpectSeq from a given file to a passed SpectSeq object.
	 * 		Function returns 0 (ENS_OK) if was successful, otherwise it returns non 0 integer value (espeak_ng_STATUS)
	 *  */
	public static native int nativeGetSpectSeq(SpectSeq spect, String fileName);

	
	/** Function returns array of phoneme strings which was got by calling EspeakNG espeak_TextToPhonemes with passed string as parameter.
	 * 		As EspeakNG espeak_TextToPhonemes function returns phonemes for the text up to end of a sentence, or comma, semicolon, colon, 
	 * 		or similar punctuation, espeak_TextToPhonemes function is called on remained untranslated string in loop (if needed), till all given text 
	 * 		has been translated. Every string in returned array represents one such call.
	 * 		
	 * 		EspeakNG espeak_TextToPhonemes works only after initialization and language has been selected, so Language is not optional.
	 *  */
	public static native String[] nativeTextToPhonemes(String textToTranslate, String language);
	
	/** 
	 *	Language defaults to "en"
	 * */
	public static String[] nativeTextToPhonemes(String textToTranslate){
		return nativeTextToPhonemes(textToTranslate, "en"); 
	}

}
