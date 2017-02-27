package org.espeakng.jeditor.jni;

/* C structure:
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
	public int numframes;
	public short amplitude;
	public int spare;
	public String name;
	
	public SpectFrame[] frames; // pointer to array of frames, maybe its better to use list
	public PitchEnvelope pitchenv;
	public int pitch1;
	public int pitch2;
	public int duration;
	public int grid;
	public int bass_reduction;
	public int max_x;
	public short max_y;
	public int file_format;
}
