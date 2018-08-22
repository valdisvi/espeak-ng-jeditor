#include <string.h>
#include <malloc.h>
#include <espeak-ng/speak_lib.h>



espeak_POSITION_TYPE position_type;
espeak_AUDIO_OUTPUT output;
char *path=NULL;
int Buflength = 500, Options=0;
void* user_data;
t_espeak_callback *SynthCallback;
espeak_PARAMETER Parm;



//char Voice[] = {"lt+klatt2"};
 char Voice[] = {"English"};

char text[30] = {"this is a english test"};
unsigned int Size,position=0, end_position=0, flags=espeakCHARS_AUTO, *unique_identifier;




int main(int argc, char* argv[] ) 
{
    output = AUDIO_OUTPUT_PLAYBACK;
    int I, Run = 1, L;    
    espeak_Initialize(output, Buflength, path, Options ); 
    espeak_SetVoiceByName(Voice);
    //const char *langNativeString = "lt"; //Default to US English
    const char *langNativeString = "en"; //Default to US English
    espeak_VOICE voice;
    memset(&voice, 0, sizeof(espeak_VOICE)); // Zero out the voice first
    voice.languages = langNativeString;
    //voice.name = "klatt";
    voice.name = "US";
    voice.variant = 2;
    voice.gender = 1;
    espeak_SetVoiceByProperties(&voice);
    Size = strlen(text)+1;    
    printf("Saying  '%s'",text);
    espeak_Synth( text, Size, position, position_type, end_position, flags,
    unique_identifier, user_data );
    espeak_Synchronize( );
    printf("\n:Done\n"); 
    return 0;
}
