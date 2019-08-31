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

#include "org_espeakng_jeditor_jni_ESpeakService.h"

// variables for nativeSpeak function
espeak_POSITION_TYPE position_type;
espeak_AUDIO_OUTPUT output;
char *path = NULL;
int buflength = 500, options = 0;
void* user_data;
unsigned int size, position = 0, end_position = 0, flags = espeakCHARS_AUTO,
		*unique_identifier;
/*
 * Class:     org_espeakng_jeditor_jni_ESpeakService
 * Method:    nativeSpeak
 * Signature: (ILjava/lang/String;Ljava/lang/String;)V
 */

JNIEXPORT void JNICALL Java_org_espeakng_jeditor_jni_ESpeakService_nativeSpeak(
		JNIEnv * env, jclass class, jstring language, jstring text) {
	const char *nativeLanguage = (*env)->GetStringUTFChars(env, language, 0);
	const char *nativeText = (*env)->GetStringUTFChars(env, text, 0);
	printf ("nativeLanguage:%s nativeText:%s\n", nativeLanguage, nativeText);
	output = AUDIO_OUTPUT_PLAYBACK;
	espeak_Initialize(output, buflength, path, options);
	espeak_VOICE voice;
	memset(&voice, 0, sizeof(espeak_VOICE)); // Zero out the voice first
	voice.languages = nativeLanguage;
	voice.name = nativeLanguage;
	voice.variant = 1;
	voice.gender = 1;
	espeak_SetVoiceByProperties(&voice);
	size = strlen(nativeText) + 1;
	printf("Saying  '%s'", nativeText);
	espeak_Synth(nativeText, size, position, position_type, end_position, flags,
			unique_identifier, user_data);
	espeak_Synchronize();
	printf("\n:Done\n");
}

/*
 * Class:     org_espeakng_jeditor_jni_ESpeakService
 * Method:    nativeGetPhonemeList
 * Signature: ()I
 *
 *@author Andrejs Freiss 28.08.19
 *
 * This function has not yet implemented in project.
 *
 * This function acquires PHONEME_LIST data form "C" espeak-ng project
 * PHONEME_LIST is not publicly available, so if you want this use this function you need to
 * add this line:

  		extern PHONEME_LIST *getPhonemeList();

 * to synthesize.h in espeak-ng project
 * and add this line:

  		#pragma GCC visibility push(default)
			PHONEME_LIST * getPhonemeList() {
				return &phoneme_list;
			}
		#pragma GCC visibility pop

 * to synthesize.c in espeak-ng project
 * and recompile libespeakservice.so library
 * you can do this with command "./updateJNIchanges.sh" in this project.
 * Note that both "espeak-ng" and "espeak-ng-jeditor" projects should be located in the same folder to compile library.
 * e.g ../workspace/espeak-ng	../workspace/eskeap-ng-jeditor
 *
 * This function not just gives back PHONEME_DATA for each Phoneme in your text, but also voices it out.
 * This conflicts with already existing function, so when you press "Speak" its speaks out twice.
 * Possible solution is to somehow disable speech for this function, or replace existing one (found in EvenHandlers.java:298) with this one.
 * Unfortunately due to Thread problems and time limits i was not able to do this.
 * Also i created 3 classes - PhonemeList.java, PhonemeList2.java and PhonemeTab.java,
 * which right now does nothing, but could be used to store this data
 */
JNIEXPORT jint JNICALL Java_org_espeakng_jeditor_jni_ESpeakService_nativeGetPhonemeList
  (JNIEnv *env, jclass obj, jstring jTextToTranslate, jstring language) {

	const char* cLanguage = (*env)->GetStringUTFChars(env, language, NULL);

	const char* cTextToTranslate =
			jTextToTranslate ?
					(*env)->GetStringUTFChars(env, jTextToTranslate, NULL) :
					NULL;

	int bufferlength = 500;

	espeak_Initialize(output, bufferlength, path, options);
	espeak_SetVoiceByName(cLanguage);
	size = strlen(cTextToTranslate) + 1;;
	espeak_Synth(cTextToTranslate, size, position, position_type, end_position,
					flags, unique_identifier, user_data);
	PHONEME_LIST *myphlist = getPhonemeList();
	espeak_Synchronize();

	// Loops through each Phoneme and gives back its data
	for (int j = 1; j < strlen(cTextToTranslate)+1; j++) {
		if (myphlist[j].amp == 0) { break; }
		//PHONEME_LIST2
			printf(" synthflags:%04x", myphlist[j].synthflags);
			printf(" phcode:%02x", myphlist[j].phcode);
			printf(" stresslevel:%02x", myphlist[j].stresslevel);
			printf(" sourceix:%04x", myphlist[j].sourceix);
			printf(" wordstress:%02x", myphlist[j].wordstress);
			printf(" tone_ph:%02x\n", myphlist[j].tone_ph);

		//PHONEME_TAB
			printf(" ph->mnemonic:%04x", myphlist[j].ph->mnemonic);
			printf(" ph->phflags:%04x", myphlist[j].ph->phflags);
			printf(" ph->program:%04x", myphlist[j].ph->program);
			printf(" ph->code:%02x", myphlist[j].ph->code);
			printf(" ph->type:%02x", myphlist[j].ph->type);
			printf(" ph->start_type:%02x", myphlist[j].ph->start_type);
			printf(" ph->end_type:%02x", myphlist[j].ph->end_type);
			printf(" ph->std_length:%02x", myphlist[j].ph->std_length);
			printf(" ph->length_mod:%02x\n", myphlist[j].ph->length_mod);

		//PHONEME_LIST
			printf(" length:%04x", myphlist[j].length);
			printf(" env:%02x", myphlist[j].env);
			printf(" type:%02x", myphlist[j].type);
			printf(" prepause:%02x", myphlist[j].prepause);
			printf(" postpause:%02x", myphlist[j].postpause);
			printf(" amp:%02x", myphlist[j].amp);
			printf(" newword:%02x", myphlist[j].newword);
			printf(" pitch1:%02x", myphlist[j].pitch1);
			printf(" pitch2:%02x", myphlist[j].pitch2);
			printf(" std_length:%02x", myphlist[j].std_length);
			printf(" phontab_addr:%04x", myphlist[j].phontab_addr);
			printf(" sound_param:%04x\n", myphlist[j].sound_param);
	}
	espeak_Terminate();
	return 0;
};

/*
 * Class:     org_espeakng_jeditor_jni_ESpeakService
 * Method:    nativeGetEspeakNgVersion
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_espeakng_jeditor_jni_ESpeakService_nativeGetEspeakNgVersion(
		JNIEnv * env, jclass clazz) {
	/* if function is static then jobject points to class rather than object */

	const char* versionInfo = espeak_Info(NULL);
	jstring newString = (*env)->NewStringUTF(env, versionInfo);

	return newString;
}

/*
 * Class:     org_espeakng_jeditor_jni_ESpeakService
 * Method:    nativeGetSpectSeq
 * Signature: (Lorg/espeakng/jeditor/jni/SpectSeq;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_espeakng_jeditor_jni_ESpeakService_nativeGetSpectSeq(
		JNIEnv * env, jclass clazz, jobject jSpect, jstring jFileName) {

	SpectSeq *spect = SpectSeqCreate();

	espeak_ng_STATUS status;

// create native C string ( char * ) from jstring
	const char *nativeString = (*env)->GetStringUTFChars(env, jFileName, 0);

	status = LoadSpectSeq(spect, nativeString); // LoadSpecSeq is eSpeak function, that IS NOT in original eSpeak API

//release string after using it
	(*env)->ReleaseStringUTFChars(env, jFileName, nativeString);

	if (status != ENS_OK) {
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
	fieldID = (*env)->GetFieldID(env, jSpectClass, "name",
			"Ljava/lang/String;"); // Ljava/lang/String; for String
	jstring newString = (*env)->NewStringUTF(env, spect->name);
	(*env)->SetObjectField(env, jSpect, fieldID, newString);

// set SpectFrame[] frames;
// as this is array of quite complex objects, creating them is not so straightforward

// get pointer to SpectFrame class
	jclass jSpectFrameClass = (*env)->FindClass(env,
			"org/espeakng/jeditor/jni/SpectFrame");

// create array to store frames (afterwards it will be attached to jSpect object field "frames")
	jobjectArray jFrames = (*env)->NewObjectArray(env, spect->numframes,
			jSpectFrameClass, NULL);

	for (int i = 0; i < spect->numframes; i++) {

		// "<init>" is used to tell it is constructor
		// ()V is signature for empty constructor
		// find constructor
		jmethodID constrSpectFrame = (*env)->GetMethodID(env, jSpectFrameClass,
				"<init>", "()V");
		// use constructor
		jobject jSpectFrame = (*env)->NewObjectA(env, jSpectFrameClass,
				constrSpectFrame, NULL);

		SpectFrame *sframe = spect->frames[i];

		//set int keyframe;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "keyframe", "I"); // I for int
		(*env)->SetIntField(env, jSpectFrame, fieldID, sframe->keyframe);

		//set short amp_adjust;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "amp_adjust", "S"); // S for short
		(*env)->SetShortField(env, jSpectFrame, fieldID, sframe->amp_adjust);

		//set float length_adjust;
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "length_adjust",
				"F"); // F for float
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

		for (int j = 0; j < sframe->nx; j++) {
			arrayElems[j] = sframe->spect[j];
		}

		(*env)->ReleaseIntArrayElements(env, frameSpect, arrayElems, 0); // finished using the array, free memory

		// add created spect array  to frame
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "spect", "[I");
		(*env)->SetObjectField(env, jSpectFrame, fieldID, frameSpect);

		//public short[] klaat_param;

		jshortArray klaatArray = (*env)->NewShortArray(env, N_KLATTP2);
		jshort *klaatArrayElems = (*env)->GetShortArrayElements(env, klaatArray,
				0);
		for (int j = 0; j < N_KLATTP2; j++) {
			klaatArrayElems[j] = sframe->klatt_param[j];
		}

		(*env)->ReleaseShortArrayElements(env, klaatArray, klaatArrayElems, 0); // finished using the array, free memory

		// add created klaat_param array  to frame
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "klaat_param",
				"[S");
		(*env)->SetObjectField(env, jSpectFrame, fieldID, klaatArray);

		//set Formant_t[] formants;

		jclass jFormant_tClass = (*env)->FindClass(env,
				"org/espeakng/jeditor/jni/Formant_t");
		jmethodID constrFormant = (*env)->GetMethodID(env, jFormant_tClass,
				"<init>", "(SS)V");
		jobject jFormant;

		jobjectArray jFormantArray = (*env)->NewObjectArray(env, N_PEAKS,
				jFormant_tClass, NULL);

		for (int j = 0; j < N_PEAKS; j++) {

			// use constructor
			jFormant = (*env)->NewObject(env, jFormant_tClass, constrFormant,
					sframe->formants[j].freq, sframe->formants[j].bandw);

			(*env)->SetObjectArrayElement(env, jFormantArray, j, jFormant);
		}
		// set formants
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "formants",
				"[Lorg/espeakng/jeditor/jni/Formant_t;"); // Ljava/lang/String; for String
		(*env)->SetObjectField(env, jSpectFrame, fieldID, jFormantArray);

		//public Peak_t[] peaks;

		jclass jPeak_tClass = (*env)->FindClass(env,
				"org/espeakng/jeditor/jni/Peak_t");
		jmethodID constrPeak = (*env)->GetMethodID(env, jPeak_tClass, "<init>",
				"(SSSSSSS)V");
		jobject jPeak;

		jobjectArray jPeakArray = (*env)->NewObjectArray(env, N_PEAKS,
				jPeak_tClass, NULL);

		for (int j = 0; j < N_PEAKS; j++) {

			// use constructor (order of parameters is critical here)
			jPeak = (*env)->NewObject(env, jPeak_tClass, constrPeak,
					sframe->peaks[j].pkfreq, sframe->peaks[j].pkheight,
					sframe->peaks[j].pkwidth, sframe->peaks[j].pkright,
					sframe->peaks[j].klt_bw, sframe->peaks[j].klt_ap,
					sframe->peaks[j].klt_bp);

			(*env)->SetObjectArrayElement(env, jPeakArray, j, jPeak);
		}
		// set formants
		fieldID = (*env)->GetFieldID(env, jSpectFrameClass, "peaks",
				"[Lorg/espeakng/jeditor/jni/Peak_t;"); // Ljava/lang/String; for String
		(*env)->SetObjectField(env, jSpectFrame, fieldID, jPeakArray);

		(*env)->SetObjectArrayElement(env, jFrames, i, jSpectFrame);

	}
// set frames
	fieldID = (*env)->GetFieldID(env, jSpectClass, "frames",
			"[Lorg/espeakng/jeditor/jni/SpectFrame;"); // Ljava/lang/String; for String
	(*env)->SetObjectField(env, jSpect, fieldID, jFrames);

// set PitchEnvelope pitchenv;
	jclass jPitchEnvelopeClass = (*env)->FindClass(env,
			"org/espeakng/jeditor/jni/PitchEnvelope");
	jmethodID constrPitchEnvelope = (*env)->GetMethodID(env,
			jPitchEnvelopeClass, "<init>", "()V");
	jobject jPitch;
	jPitch = (*env)->NewObject(env, jPitchEnvelopeClass, constrPitchEnvelope);

//set int pitch1 for PitchEnvelope
	fieldID = (*env)->GetFieldID(env, jPitchEnvelopeClass, "pitch1", "I"); // I for int
	(*env)->SetIntField(env, jPitch, fieldID, spect->pitchenv.pitch1);
//set int pitch2 for PitchEnvelope
	fieldID = (*env)->GetFieldID(env, jPitchEnvelopeClass, "pitch2", "I"); // I for int
	(*env)->SetIntField(env, jPitch, fieldID, spect->pitchenv.pitch2);

	jshortArray envArray = (*env)->NewShortArray(env, 128); // create new java array
	jshort *envElems = (*env)->GetShortArrayElements(env, envArray, 0);
	for (int i = 0; i < 128; i++) {
		envElems[i] = spect->pitchenv.env[i];
	}

//set created array as PitchEnvelope field
	fieldID = (*env)->GetFieldID(env, jPitchEnvelopeClass, "env", "[S");
	(*env)->SetObjectField(env, jPitch, fieldID, envArray);

	(*env)->ReleaseShortArrayElements(env, envArray, envElems, 0); //finished using array, free memory

//set jPitch as SpectSeq field
	fieldID = (*env)->GetFieldID(env, jSpectClass, "pitchenv",
			"Lorg/espeakng/jeditor/jni/PitchEnvelope;");
	(*env)->SetObjectField(env, jSpect, fieldID, jPitch);

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
	fieldID = (*env)->GetFieldID(env, jSpectClass, "file_format", "I"); // I for int
	(*env)->SetIntField(env, jSpect, fieldID, spect->file_format);
// if everything was ok return 0
	return ENS_OK;

}
;

/*
 * Class:     org_espeakng_jeditor_jni_ESpeakService
 * Method:    nativeTextToPhonemes
 * Signature: (Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
 */
JNIEXPORT jobjectArray JNICALL Java_org_espeakng_jeditor_jni_ESpeakService_nativeTextToPhonemes(
		JNIEnv * env, jclass clazz, jstring jTextToTranslate, jstring language) {

	espeak_Initialize(AUDIO_OUTPUT_PLAYBACK, 0, NULL, 0);

	const char* cLanguage = (*env)->GetStringUTFChars(env, language, NULL);

	espeak_SetVoiceByName(cLanguage);

	(*env)->ReleaseStringUTFChars(env, language, cLanguage);

// get c string from jstring ( this c string is created by JVM )
	const char* cTextToTranslate =
			jTextToTranslate ?
					(*env)->GetStringUTFChars(env, jTextToTranslate, NULL) :
					NULL;

// copy this c string because otherwise espeak_TextToPhonemes will change where it points and JVM won't like that
	char * copyOfTextToTranslate = malloc(strlen(cTextToTranslate) + 1); // +1 because of '\0' at the end

	strcpy(copyOfTextToTranslate, cTextToTranslate); // JVM don't have to worry about copyOfTextToTranslate

// FIXME translate text to get array size and then translate it again to get phonemes is bad
// but i am quite tired now and it should work. So, do something smarter - e.g. use arrayList or
// cache translated phonemes

	const void **textptr = (void*) &copyOfTextToTranslate;

	int i = 0;

	while (*textptr != NULL) {

		espeak_TextToPhonemes(textptr, espeakCHARS_AUTO, 0);

		i++;
	}

// FIXME i hope espeak_TextToPhonemes takes care of memory for "reduced" part, find out reality

// copy second time, because it was already spent to get array size (i)
	copyOfTextToTranslate = malloc(strlen(cTextToTranslate) + 1); // +1 because of '\0' at the end

	strcpy(copyOfTextToTranslate, cTextToTranslate);

	textptr = (void*) &copyOfTextToTranslate;

// no can release cTextToTranslate so JVM garbage collector can collect it
	if (cTextToTranslate)
		(*env)->ReleaseStringUTFChars(env, jTextToTranslate, cTextToTranslate);

	const char* phonemes;

	jstring phonemesString;

// create java string array to store translated phonemes
	jobjectArray phonemesArray = (*env)->NewObjectArray(env, i,
			(*env)->FindClass(env, "java/lang/String"), 0);

	for (int j = 0; j < i; j++) {

		phonemes = espeak_TextToPhonemes(textptr, espeakCHARS_AUTO, 0);

		phonemesString = (*env)->NewStringUTF(env, phonemes);

		(*env)->SetObjectArrayElement(env, phonemesArray, j, phonemesString);

	}

	espeak_Terminate();
	return phonemesArray;

}

// main is only used for testing
int main() {
	return 0;
}
