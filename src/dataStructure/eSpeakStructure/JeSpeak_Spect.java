package dataStructure.eSpeakStructure;

	/*	Java class for eSpeak-ng spect.h
	 */
public class JeSpeak_Spect {
	
	
	/*
	/* INNER CLASSES FOR CORRESPINDING C STRUCTURES
	/*
	
	/*	C structure:
	 * 
		typedef struct {
		unsigned short pitch1;
		unsigned short pitch2;
		unsigned char env[128];
		} PitchEnvelope;
	*/
	public class PitchEnvelope{
		public int pitch1;  // to be on safe side use int for unsigned short
		public int pitch2;	
		public short[] env = new short[128];	// TODO not sure if type should be short or char, so - find that out
	}
	
	/* C structure:
	 * 
		typedef struct {
			short freq;
			short bandw;
		} formant_t;
	*/
	public class Formant_t{
		public short freq;	// short in c is the same as short in Java
		public short bandw;
	}
	
	/* C structure:
	 * 
		typedef struct {
			short pkfreq;
			short pkheight;
			short pkwidth;
			short pkright;
			short klt_bw;
			short klt_ap;
			short klt_bp;
		} peak_t;
	*/
	
	public class Peak_t{
		public short pkfreq;
		public short pkheight;
		public short pkwidth;
		public short pkright;
		public short klt_bw;
		public short klt_ap;
		public short klt_bp;
	}
	
	
	/* C structure:
	 * 

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
	
	/* C structure:
	 * 
		 	typedef struct {
			int numframes;
			short amplitude;
			int spare;
			char *name;
	
			SpectFrame **frames;
			PitchEnvelope pitchenv;
			int pitch1;
			int pitch2;
			int duration;
			int grid;
			int bass_reduction;
			int max_x;
			short max_y;
			int file_format;
		} SpectSeq;
	*/
	
	public class SpectSeq{
		int numframes;
		short amplitude;
		int spare;
		String name;
		
		SpectFrame[] frames; // pointer to array of frames, maybe its better to use list
		PitchEnvelope pitchenv;
		int pitch1;
		int pitch2;
		int duration;
		int grid;
		int bass_reduction;
		int max_x;
		short max_y;
		int file_format;
	}
}
