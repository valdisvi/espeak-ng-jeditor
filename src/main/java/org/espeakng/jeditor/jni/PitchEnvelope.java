package org.espeakng.jeditor.jni;

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
	public short[] env = new short[128];
}
