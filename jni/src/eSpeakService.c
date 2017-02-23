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

	status = LoadSpectSeq(spect, "/home/student/workspace-c/espeak-ng/phsource/vowel/a");

	if (status != ENS_OK){
		return status;
	}

	jclass jSpectClass = (*env)->GetObjectClass(env, jSpect); // get object class

	//set int numframes;
	jfieldID fieldID = (*env)->GetFieldID(env, jSpectClass, "numframes", "I"); // I for int
	(*env)->SetIntField(env, jSpect, fieldID, spect->numframes);
	//set short amplitude;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "amplitude", "S"); // S for short
	(*env)->SetShortField(env, jSpect, fieldID, spect->amplitude);
	//set int spare;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "spare", "I"); // I for int
	(*env)->SetIntField(env, jSpect, fieldID, spect->spare);
	// set String name;
	fieldID = (*env)->GetFieldID(env, jSpectClass, "name", "Ljava/lang/String;"); // Ljava/lang/String; for String
	jstring newString = (*env)->NewStringUTF(env, spect->name);
	(*env)->SetObjectField(env, jSpect, fieldID, newString);

	// set SpectFrame[] frames;
	jclass jSpectFrameClass = (*env)->FindClass(env, "dataStructure/eSpeakStructure/SpectFrame");

	jobjectArray jFrames = (*env)->NewObjectArray(env, spect->numframes, jSpectFrameClass, NULL);

	for (int i = 0; i < spect->numframes; i++){

		// "<init>" is used to tell it is constructor
		// ()V is signature for empty constructor
		jmethodID constrSpectFrame = (*env)->GetMethodID(env, jSpectFrameClass, "<init>", "()V");

		jobject jSpectFrame = (*env)->NewObjectA(env, jSpectFrameClass, constrSpectFrame, NULL);

		SpectFrame *sframe = spect->frames[i];

		//set int keyframe;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "keyframe", "I"); // I for int
		(*env)->SetIntField(env, jSpectFrame, fieldID, sframe->keyframe);
		//set short amp_adjust;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "amp_adjust", "S"); // S for short
		(*env)->SetShortField(env, jSpectFrame, fieldID, sframe->amp_adjust);
		//set float length_adjust; // Should we use double instead?
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
		//jintArray frameSpect= (*env)->NewIntArray(env, sframe->nx);

		// somehow not sure abaut this one
		/*for (int j = 0; j< sframe->nx; j++){
			(*env)->SetIntArrayRegion(env, frameSpect, 0, sframe->nx, sframe->spect);
		}*/

		//fieldID = (*env)->GetFieldID(env, frameSpect, "spect", "[I"); // Ljava/lang/String; for String
		//(*env)->SetObjectField(env, jSpectFrame, fieldID, frameSpect);


		//public short[] klaat_param;
		//public Formant_t[] formants;
		//public Peak_t[] peaks;

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
