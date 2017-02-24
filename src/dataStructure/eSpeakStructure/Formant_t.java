package dataStructure.eSpeakStructure;

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
	
	Formant_t(){
	}
	
	Formant_t(short freq, short bandw){
		this.freq = freq;
		this.bandw = bandw;	
	}
}