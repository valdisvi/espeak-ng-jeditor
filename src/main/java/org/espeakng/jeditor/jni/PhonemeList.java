package org.espeakng.jeditor.jni;

/** 
 * @author Andrejs Freiss 29.08.19
 * 
 * This is how Phoneme List looks like in espeak-ng C ../synthesize.h
 * 
 * typedef struct {
	// The first section is a copy of PHONEME_LIST2
	unsigned short synthflags;
	unsigned char phcode;
	unsigned char stresslevel;
	unsigned short sourceix;  // ix into the original source text string, only set at the start of a word
	unsigned char wordstress; // the highest level stress in this word
	unsigned char tone_ph;    // tone phoneme to use with this vowel

	PHONEME_TAB *ph;
	unsigned int length;  // length_mod
	unsigned char env;    // pitch envelope number
	unsigned char type;
	unsigned char prepause;
	unsigned char postpause;
	unsigned char amp;
	unsigned char newword;   // bit flags, see PHLIST_(START|END)_OF_*
	unsigned char pitch1;
	unsigned char pitch2;
	unsigned char std_length;
	unsigned int phontab_addr;
	int sound_param;
} PHONEME_LIST;
*/
public class PhonemeList {

	public int length;
	public char env;
	public char type;
	public char prepause;
	public char postpause;
	public char amp;
	public char newword;
	public char pitch1;
	public char pitch2;
	public char std_length;
	public int phontab_addr;
	public int sound_parm;		
}
