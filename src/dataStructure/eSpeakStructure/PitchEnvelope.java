package dataStructure.eSpeakStructure;

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
