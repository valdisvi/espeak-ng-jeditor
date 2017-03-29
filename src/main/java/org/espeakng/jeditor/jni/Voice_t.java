package org.espeakng.jeditor.jni;


/**
 * This class is not implemented anywhere yet
*/
public class Voice_t {

	char[] v_name = new char[40];
	char[] language_name= new char[20];

	int phoneme_tab_ix;  // phoneme table number
	int pitch_base;    // Hz<<12
	int pitch_range;   // standard = 0x1000

	int speedf1;
	int speedf2;
	int speedf3;

	int speed_percent;      // adjust the WPM speed by this percentage
	int flutter;
	int roughness;
	int echo_delay;
	int echo_amp;
	int n_harmonic_peaks;  // highest formant which is formed from adding harmonics
	int peak_shape;        // alternative shape for formant peaks (0=standard 1=squarer)
	int voicing;           // 100% = 64, level of formant-synthesized sound
	int formant_factor;      // adjust nominal formant frequencies by this  because of the voice's pitch (256ths)
	int consonant_amp;     // amplitude of unvoiced consonants
	int consonant_ampv;    // amplitude of the noise component of voiced consonants
	int samplerate;
	int[] klattv = new int[8];

	// parameters used by Wavegen
	short[] freq = new short[JeSpeak_Synthesize.N_PEAKS];    // 100% = 256
	short[] height= new short[JeSpeak_Synthesize.N_PEAKS];  // 100% = 256
	short[] width= new short[JeSpeak_Synthesize.N_PEAKS];   // 100% = 256
	short[] freqadd= new short[JeSpeak_Synthesize.N_PEAKS];  // Hz

	// copies without temporary adjustments from embedded commands
	short[] freq2= new short[JeSpeak_Synthesize.N_PEAKS];    // 100% = 256
	short[] height2= new short[JeSpeak_Synthesize.N_PEAKS];  // 100% = 256
	short[] width2= new short[JeSpeak_Synthesize.N_PEAKS];   // 100% = 256

	int[] breath= new int [JeSpeak_Synthesize.N_PEAKS];  // amount of breath for each formant. breath[0] indicates whether any are set.
	int[] breathw= new int [JeSpeak_Synthesize.N_PEAKS];  // width of each breath formant

	// This table provides the opportunity for tone control.
	// Adjustment of harmonic amplitudes, steps of 8Hz
	// value of 128 means no change
	//#define N_TONE_ADJUST  1000  (c++)
 byte[] tone_adjust = new  byte[1000]; //  8Hz steps * 1000 = 8kHz

}
