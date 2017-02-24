/*
 * eSpeakService.c
 *
 *  Created on: Feb 21, 2017
 *      Author: Marcis Majors
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>

#include <jni.h>

// using include path to espeak-ng

#include <config.h>

#include <src/include/espeak-ng/speak_lib.h>
#include <src/include/espeak-ng/espeak_ng.h>


#include <src/libespeak-ng/error.h>
#include <src/libespeak-ng/phoneme.h>
#include <src/libespeak-ng/event.h>
#include <src/libespeak-ng/speech.h>
#include <src/libespeak-ng/espeak_command.h>
#include <src/libespeak-ng/fifo.h>
#include <src/libespeak-ng/sintab.h>
#include <src/libespeak-ng/mbrowrap.h>
#include <src/libespeak-ng/klatt.h>

#include <src/libespeak-ng/synthesize.h>
#include <src/libespeak-ng/spect.h>

#include "../include/eSpeakServices_ESpeakService.h"

/*
 * Class:     eSpeakServices_ESpeakService
 * Method:    nativeGetEspeakNgVersion
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_eSpeakServices_ESpeakService_nativeGetEspeakNgVersion
  (JNIEnv * env, jclass clazz){
	/* if function is static then jobject points to class rather than object */

	const char* versionInfo = espeak_Info(NULL);
	jstring newString = (*env)->NewStringUTF(env, versionInfo);
	return newString;

}

/*
 * Class:     eSpeakServices_ESpeakService
 * Method:    nativeGetSpectSeq
 * Signature: (LdataStructure/eSpeakStructure/SpectSeq;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_eSpeakServices_ESpeakService_nativeGetSpectSeq
  (JNIEnv * env, jclass clazz, jobject jSpect, jstring jFileName){

	SpectSeq *spect = SpectSeqCreate();

	espeak_ng_STATUS status;

	// create native C string ( char * ) from jstring
	const char *nativeString = (*env)->GetStringUTFChars(env, jFileName, 0);

	status = LoadSpectSeq(spect, nativeString);  // LoadSpecSeq is eSpeak function, that IS NOT in original eSpeak API

	//release string after using it
	(*env)->ReleaseStringUTFChars(env, jFileName, nativeString);

	if (status != ENS_OK){
		return status;
	}

	jclass jSpectClass = (*env)->GetObjectClass(env, jSpect); // get pointer to object class

	//set int numframes; These two JNI lines is like "jSpect.numframes = spect.numframes" in Java
	jfieldID fieldID = (*env)->GetFieldID(env, jSpectClass, "numframes", "I"); // find field's "numframes" ID; "I" stands for int
	(*env)->SetIntField(env, jSpect, fieldID, spect->numframes); // calling appropriate JNI Set<type>Field style function

	//set short amplitude;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "amplitude", "S"); // S for short
	(*env)->SetShortField(env, jSpect, fieldID, spect->amplitude); //

	//set int spare;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "spare", "I"); // I for int
	(*env)->SetIntField(env, jSpect, fieldID, spect->spare);

	// set String name;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "name", "Ljava/lang/String;"); // Ljava/lang/String; for String
	jstring newString = (*env)->NewStringUTF(env, spect->name);
	(*env)->SetObjectField(env, jSpect, fieldID, newString);

	// set SpectFrame[] frames;
	// as this is array of quite complex objects, creating them is not so straightforward

	// get pointer to SpectFrame class
	jclass jSpectFrameClass = (*env)->FindClass(env, "dataStructure/eSpeakStructure/SpectFrame");

	// create array to store frames (afterwards it will be attached to jSpect object field "frames")
	jobjectArray jFrames = (*env)->NewObjectArray(env, spect->numframes, jSpectFrameClass, NULL);

	for (int i = 0; i < spect->numframes; i++){

		// "<init>" is used to tell it is constructor
		// ()V is signature for empty constructor
		// find constructor
		jmethodID constrSpectFrame = (*env)->GetMethodID(env, jSpectFrameClass, "<init>", "()V");
		// use constructor
		jobject jSpectFrame = (*env)->NewObjectA(env, jSpectFrameClass, constrSpectFrame, NULL);

		SpectFrame *sframe = spect->frames[i];

		//set int keyframe;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "keyframe", "I"); // I for int
		(*env)->SetIntField(env, jSpectFrame, fieldID, sframe->keyframe);

		//set short amp_adjust;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "amp_adjust", "S"); // S for short
		(*env)->SetShortField(env, jSpectFrame, fieldID, sframe->amp_adjust);

		//set float length_adjust;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "length_adjust", "F"); // F for float
		(*env)->SetFloatField(env, jSpectFrame, fieldID, sframe->length_adjust);

		//set double rms;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "rms", "D"); // D for double
		(*env)->SetDoubleField(env, jSpectFrame, fieldID, sframe->rms);

		//set float time;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "time", "F"); // F for float
		(*env)->SetFloatField(env, jSpectFrame, fieldID, sframe->time);

		//set float pitch;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "pitch", "F"); // F for float
		(*env)->SetFloatField(env, jSpectFrame, fieldID, sframe->pitch);

		//set float length;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "length", "F"); // F for float
		(*env)->SetFloatField(env, jSpectFrame, fieldID, sframe->length);

		//set float dx;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "dx", "F"); // F for float
		(*env)->SetFloatField(env, jSpectFrame, fieldID, sframe->dx);

		//set int nx;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "nx", "I"); // I for int
		(*env)->SetIntField(env, jSpectFrame, fieldID, sframe->nx);

		//set short markers;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "markers", "S"); // S for short
		(*env)->SetShortField(env, jSpectFrame, fieldID, sframe->markers);

		//public int max_y;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "max_y", "I"); // I for int
		(*env)->SetIntField(env, jSpectFrame, fieldID, sframe->max_y);

		//set int[] spect;
		jintArray frameSpect = (*env)->NewIntArray(env, sframe->nx); // create new java array
		jint *arrayElems = (*env)->GetIntArrayElements(env, frameSpect, 0); // obtain a pointer to the elements of the array

		for (int j = 0; j< sframe->nx; j++){
			arrayElems[j] = sframe->spect[j];
		}

		(*env)->ReleaseIntArrayElements(env, frameSpect, arrayElems, 0);  // finished using the array, free memory

		// add created spect array  to frame
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "spect", "[I");
		(*env)->SetObjectField(env, jSpectFrame, fieldID, frameSpect);


		//public short[] klaat_param;

		jshortArray klaatArray = (*env)->NewShortArray(env, N_KLATTP2);
		jshort *klaatArrayElems = (*env)->GetShortArrayElements(env, klaatArray, 0);
		for (int j=0; j<N_KLATTP2; j++){
			klaatArrayElems[j] = sframe->klatt_param[j];
		}

		(*env)->ReleaseShortArrayElements(env, klaatArray, klaatArrayElems, 0);  // finished using the array, free memory

		// add created klaat_param array  to frame
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "klaat_param", "[S");
		(*env)->SetObjectField(env, jSpectFrame, fieldID, klaatArray);

		//set Formant_t[] formants;

		jclass jFormant_tClass = (*env)->FindClass(env, "dataStructure/eSpeakStructure/Formant_t");
		jmethodID constrFormant = (*env)->GetMethodID(env, jFormant_tClass, "<init>", "(SS)V");
		jobject jFormant;

		jobjectArray jFormantArray = (*env)->NewObjectArray(env, N_PEAKS, jFormant_tClass, NULL);

		for (int j = 0; j < N_PEAKS; j++){

			// use constructor
			jFormant = (*env)->NewObject(env, jFormant_tClass, constrFormant,
											sframe->formants[j].freq,
											sframe->formants[j].bandw
										);

			(*env)->SetObjectArrayElement( env, jFormantArray, j, jFormant);
		}
		// set formants
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "formants", "[LdataStructure/eSpeakStructure/Formant_t;"); // Ljava/lang/String; for String
		(*env)->SetObjectField(env, jSpectFrame, fieldID, jFormantArray);


		//public Peak_t[] peaks;

		jclass jPeak_tClass = (*env)->FindClass(env, "dataStructure/eSpeakStructure/Peak_t");
		jmethodID constrPeak = (*env)->GetMethodID(env, jPeak_tClass, "<init>", "(SSSSSSS)V");
		jobject jPeak;

		jobjectArray jPeakArray = (*env)->NewObjectArray(env, N_PEAKS, jPeak_tClass, NULL);

		for (int j = 0; j < N_PEAKS; j++){

			// use constructor (order of parameters is critical here)
			jPeak = (*env)->NewObject(env, jPeak_tClass, constrPeak,
											sframe->peaks[j].pkfreq,
											sframe->peaks[j].pkheight,
											sframe->peaks[j].pkwidth,
											sframe->peaks[j].pkright,
											sframe->peaks[j].klt_bw,
											sframe->peaks[j].klt_ap,
											sframe->peaks[j].klt_bp
										);

			(*env)->SetObjectArrayElement( env, jPeakArray, j, jPeak);
		}
		// set formants
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "peaks", "[LdataStructure/eSpeakStructure/Peak_t;"); // Ljava/lang/String; for String
		(*env)->SetObjectField(env, jSpectFrame, fieldID, jPeakArray);



		(*env)->SetObjectArrayElement( env, jFrames, i, jSpectFrame);

	}
	// set frames
	fieldID = (*env)->GetFieldID(env, jSpectClass, "frames", "[LdataStructure/eSpeakStructure/SpectFrame;"); // Ljava/lang/String; for String
	(*env)->SetObjectField(env, jSpect, fieldID, jFrames);

	// set PitchEnvelope pitchenv;
	// TODO code this a bit later :)

	//set int pitch1;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "pitch1", "I"); // I for int
	(*env)->SetIntField(env, jSpect, fieldID, spect->pitch1);
	//set int pitch2;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "pitch2", "I"); // I for int
	(*env)->SetIntField(env, jSpect, fieldID, spect->pitch2);
	//set int duration;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "duration", "I"); // I for int
	(*env)->SetIntField(env, jSpect, fieldID, spect->duration);
	//set int grid;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "grid", "I"); // I for int
	(*env)->SetIntField(env, jSpect, fieldID, spect->grid);
	//set int bass_reduction;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "bass_reduction", "I"); // I for int
	(*env)->SetIntField(env, jSpect, fieldID, spect->bass_reduction);
	//set int max_x;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "max_x", "I"); // I for int
	(*env)->SetIntField(env, jSpect, fieldID, spect->max_x);
	//set short max_y;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "max_y", "S"); // S for short
	(*env)->SetShortField(env, jSpect, fieldID, spect->max_y);
	//set int file_format;



	return ENS_OK;

};

int main(){

	/*
	const char *data_path = NULL; //  use default path for espeak-data

	int samplerate;

	const char* versionInfo = espeak_Info(NULL);

	printf("Espeak version: %s\n", versionInfo);

	samplerate = espeak_Initialize(AUDIO_OUTPUT_PLAYBACK,0,data_path,0);

	printf("%i \n", samplerate);



	static char word[200] = "Hello World" ;
	strcpy(word, "Hello!");

	espeak_Synth( (char*) word, strlen(word)+1, 0, POS_CHARACTER, 0, espeakCHARS_AUTO, NULL, NULL);
	espeak_Synchronize();

	espeak_ERROR err = espeak_SetVoiceByName("latvian");

	if (err == EE_OK){
		printf("latvian voice selected\n");
	}

	espeak_Synth( (char*) word, strlen(word)+1, 0, POS_CHARACTER, 0, espeakCHARS_AUTO, NULL, NULL);
	espeak_Synchronize();

	const void **textptr;

	const void *someText = "Zirgs iet. Ko nu tagad?";

	printf("%s\n", (char*)someText);

	textptr = &someText;

	const char *phonemes = espeak_TextToPhonemes(textptr, espeakCHARS_AUTO, 0);

	printf("%s\n", (char*)*textptr);

	printf("%s\n", phonemes);

	phonemes = espeak_TextToPhonemes(textptr, espeakCHARS_AUTO, 0);

	printf("%s\n", (char*)*textptr);

	printf("%s\n", phonemes);

	*/

	/*

	SpectSeq *spect = SpectSeqCreate();

	LoadSpectSeq(spect, "/home/student/workspace-c/espeak-ng/phsource/vowel/a");

	printf("%s\n",spect->name);

	printf("%i\n", spect->duration);

	printf("%i\n",spect->numframes);

	printf("%i\n", spect->amplitude);

	SpectFrame *spectFrame;

	spectFrame = spect->frames[2];

	printf("%i\n", spectFrame->amp_adjust);

	SpectSeqDestroy(spect);





	espeak_Terminate();

	printf("Jei!\n");

	*/

	return 0;
}
