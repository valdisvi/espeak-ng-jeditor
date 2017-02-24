package dataStructure.eSpeakStructure;

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
	
	Peak_t(){
	}
	
	Peak_t(short pkfreq, short pkheight, short pkwidth, short pkright, short klt_bw, short klt_ap, short klt_bp){
		this.pkfreq = pkfreq;
		this.pkheight = pkheight;
		this.pkwidth = pkwidth;
		this.pkright = pkright;
		this.klt_bw = klt_bw;
		this.klt_ap = klt_ap;
		this.klt_bp = klt_bp;
	}
}