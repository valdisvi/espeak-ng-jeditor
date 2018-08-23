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

int main(int argc, char* argv[]) {
	printf("language:%s text:%s\n", argv[1], argv[2]);
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
