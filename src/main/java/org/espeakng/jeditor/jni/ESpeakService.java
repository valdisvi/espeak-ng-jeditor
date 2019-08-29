package org.espeakng.jeditor.jni;

import java.util.Arrays;

import org.apache.log4j.Logger;

/*-
 * EspeakService class is used to call native EspeakNG functions
 * EspeakService loads libespeakservice.so library witch calls EspeakNG public library functions and 
 * use JNI to return values back to this Java class. At this stage libespeakservice.so library
 * is written "by hand". 
 * 
 * If this class is changed there should be according changes made to /jni/src/eSpeakService.c and
 * libespeakservice.so should be recompiled. To ease this task there is bash script updateJNIchanges.sh
 * Before executing this script for the first time there should be path variables updated in it. It is normal 
 * if it shows some warnings for EspeakNG code part (.. espeak-ng/src/libespeak-ng/event.h... warning: ..).  
 * 
 * If some of necessary functions are not exposed in espeak-ng, check *.h file and add in *.c file lines: 
 *     ...
 *     #pragma GCC visibility push(default) // expose following function to public API
 *     exposedFuction() 
 *     ...
 *     #pragma GCC visibility pop 			// stop exposing to public API
 *     ...
 * and rerun updateJNIchanges.sh script.
 * 		
 */
public class ESpeakService {
	private static Logger logger = Logger.getLogger(ESpeakService.class);

	private ESpeakService() {
		throw new IllegalStateException("ESpeakService Utility class");
	}
	

	public static native void nativeSpeak(String language, String text);

	static {
		try {
			System.load(System.getProperty("user.dir") + "/.lib/libespeakservice.so");
		} catch (UnsatisfiedLinkError e) {
			logger.warn(e);
			System.exit(1);
		}
	}
	/**  
	 * 
	 * @author Andrejs Freiss 28.08.19                                                                                                                
                                                                                                                                               
 		This function has not yet implemented in project.                                                                                             
                                                                                                                                               
 		This function acquires PHONEME_LIST data form "C" espeak-ng project                                                                           
 		PHONEME_LIST is not publicly available, so if you want this use this function you need to                                                     
 		add this line:                                                                                                                                
                                                                                                                                               
				extern PHONEME_LIST *getPhonemeList();                                                                                                   
                                                                                                                                               
 		to synthesize.h in espeak-ng project                                                                                                          
 		and add these lines:                                                                                                                            
                                                                                                                                               	
				#pragma GCC visibility push(default)                                                                                                     
			PHONEME_LIST * getPhonemeList() {                                                                                                    
				return &phoneme_list;                                                                                                            
			}                                                                                                                                    
			#pragma GCC visibility pop                                                                                                               
                                                                                                                                               
 		to synthesize.c in espeak-ng project                                                                                                          
 		and recompile libespeakservice.so library                                                                                                     
 		you can do this with command "./updateJNIchanges.sh" in this project.                                                                         
		Note that both "espeak-ng" and "espeak-ng-jeditor" projects should be located in the same folder to compile library.                          
 		e.g ../workspace/espeak-ng	../workspace/eskeap-ng-jeditor                                                                                   
                                                                                                                                               
 		This function not just gives back PHONEME_DATA for each Phoneme in your text, but also voices it out.                                         
 		This conflicts with already existing function, so when you press "Speak" its speaks out twice.                                                
 		Possible solution is to somehow disable speech for this function, or replace existing one (found in EvenHandlers.java:298) with this one.     
 		Unfortunately due to Thread problems and time limits i was not able to do this.                                                               
 		Also i created 3 classes - PhonemeList.java, PhonemeList2.java and PhonemeTab.java,                                                           
 		which right now does nothing, but could be used to store this data                                                                            
	 */
	public static native int nativeGetPhonemeList(String textToTranslate, String language);

	
	/** Function returns string with Espeak-NG version */
	public static native String nativeGetEspeakNgVersion();

	/**
	 * Function loads SpectSeq from a given file to a passed SpectSeq object.
	 * Function returns 0 (ENS_OK) if was successful, otherwise it returns non 0
	 * integer value (espeak_ng_STATUS)
	 */
	public static native int nativeGetSpectSeq(SpectSeq spect, String fileName);

	/**
	 * Function returns array of phoneme strings which was got by calling
	 * EspeakNG espeak_TextToPhonemes with passed string as parameter. As
	 * EspeakNG espeak_TextToPhonemes function returns phonemes for the text up
	 * to end of a sentence, or comma, semicolon, colon, or similar punctuation,
	 * espeak_TextToPhonemes function is called on remained untranslated string
	 * in loop (if needed), till all given text has been translated. Every
	 * string in returned array represents one such call.
	 * 
	 * EspeakNG espeak_TextToPhonemes works only after initialization and
	 * language has been selected, so Language is not optional.
	 */

	public static native String[] nativeTextToPhonemes(String textToTranslate, String language);

	public static String textToPhonemes(String textToTranslate, String language) {
		StringBuilder result = new StringBuilder("");
		for (String line : nativeTextToPhonemes(textToTranslate, language))
			result.append(line + "\n");
		return result.toString();
	}

	/**
	 * Language defaults to "en"
	 */
	public static String[] nativeTextToPhonemes(String textToTranslate) {
		return nativeTextToPhonemes(textToTranslate, "en");
	}

}
