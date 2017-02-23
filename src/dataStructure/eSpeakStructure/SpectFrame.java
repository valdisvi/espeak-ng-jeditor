package dataStructure.eSpeakStructure;

/* C structure:
	typedef struct {
		int keyframe;
		short amp_adjust;
		float length_adjust;
		double rms;

		float time;
		float pitch;
		float length;
		float dx;
		unsigned short nx;
		short markers;
		int max_y;
		USHORT *spect; // sqrt of harmonic amplitudes,  1-nx at 'pitch'

		short klatt_param[N_KLATTP2];

		formant_t formants[N_PEAKS]; // this is just the estimate given by Praat
		peak_t peaks[N_PEAKS];
	} SpectFrame;
*/

public class SpectFrame{
	public int keyframe;
	public short amp_adjust;
	public float length_adjust; // Should we use double instead?
	public double rms;
	
	public float time;
	public float pitch;
	public float length;
	public float dx;
	public int nx;
	public short markers;
	public int max_y;
	public Integer spect; 	// using Integer because spect is a pointer to USHORT 
							// which is just unsigned short
	
	public short[] klaat_param = new short[JeSpeak_Synthesize.N_KLATTP2];
	public Formant_t[] formants = new Formant_t[JeSpeak_Synthesize.N_PEAKS];
	public Peak_t[] peaks = new Peak_t[JeSpeak_Synthesize.N_PEAKS];
}
