#include <string.h>
#include <malloc.h>
#include <espeak-ng/speak_lib.h>

espeak_POSITION_TYPE position_type;
espeak_AUDIO_OUTPUT output;
char *path = NULL;
int buflength = 500, options = 0;
void* user_data;

unsigned int size, position = 0, end_position = 0, flags = espeakCHARS_AUTO,
		*unique_identifier;

/* This is simple example, how to call espeak-ng API from C program.
 * It should be compiled by passing reference to registered library of espeak-ng, e.g.:
 * gcc speak.c -lespeak-ng -o speak
 */

int main(int argc, char* argv[]) {
	if(argc == 1) {
		fprintf(stderr, "pass two words as parameters e.g.:\nspeak \"en\" \"Hello world!\"\n");
		return 1;
	}
	printf("language:%s\n", argv[1]);
	output = AUDIO_OUTPUT_PLAYBACK;
	espeak_Initialize(output, buflength, path, options);
	espeak_VOICE voice;
	memset(&voice, 0, sizeof(espeak_VOICE)); // Zero out the voice first
	voice.languages = argv[1];
	voice.name = argv[1];
	voice.variant = 1;
	voice.gender = 1;
	espeak_SetVoiceByProperties(&voice);
	size = strlen(argv[2]) + 1;
	printf("Saying  '%s'", argv[2]);
	espeak_Synth(argv[2], size, position, position_type, end_position, flags,
			unique_identifier, user_data);
	espeak_Synchronize();
	printf("\n:Done\n");
	return 0;
}
