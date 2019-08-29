package org.espeakng.jeditor.jni;

/**
 * 
 * @author Andrejs Freiss 29.08.19
 * 
 *	typedef struct {
	unsigned int mnemonic;       // Up to 4 characters.  The first char is in the l.s.byte
	unsigned int phflags;        // bits 16-19 place of articulation
	unsigned short program;      // index into phondata file
	unsigned char code;          // the phoneme number
	unsigned char type;          // phVOWEL, phPAUSE, phSTOP etc
	unsigned char start_type;
	unsigned char end_type;      // vowels: endtype; consonant: voicing switch
	unsigned char std_length;    // for vowels, in mS/2;  for phSTRESS phonemes, this is the stress/tone type
	unsigned char length_mod;    // a length_mod group number, used to access length_mod_tab
} PHONEME_TAB;
 */

public class PhonemeTab {
	int mnemonic;   
	int phflags;    
	short program;  
	char code;      
	char type;      
	char start_type;
	char end_type;  
	char std_length;
	char length_mod;
}
