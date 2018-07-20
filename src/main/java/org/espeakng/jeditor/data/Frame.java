package org.espeakng.jeditor.data;


import org.espeakng.jeditor.jni.Formant_t;
import org.espeakng.jeditor.jni.Peak_t;
import org.espeakng.jeditor.jni.SpectFrame;

//This class responds for getting information from phoneme file and prepare it for 
//use in GUI

public class Frame {
	public double time;
	public double pitch;
	public double length;
	public double dx;
	int file_format;
	public int nx;
	double rms;
	boolean[] markers = new boolean[] { false, false, false, false, false,
			false, false, false };

	public int primarkers;
	public int amp_adjust;
	public Formant_t[] formants;
	public Peak_t[] peaks;

	// Not used in SPECTSEQ
	public int amplitude;
	public short[] klatt_param;
	public int[] spect_data;
	double max_y = 0;
	int max_x = 3000;
	static int default_freq[] = { 200, 500, 1200, 3000, 3500, 4000, 6900, 7800,
			9000 };
	static int default_width[] = { 750, 500, 550, 550, 600, 700, 700, 700, 700 };
	static int default_klt_bw[] = { 89, 90, 140, 260, 260, 260, 500, 500, 500 };
	public boolean selected = false;
	public static boolean bass_reduction = true;

	public void frameLoader(SpectFrame frames, int file_format, double max_y, int  amplitude) {
		this.amplitude =  amplitude;
		this.file_format = file_format;
		this.max_y = max_y;
		rms = frames.rms;
		time = frames.time;
		pitch = frames.pitch;
		length = frames.length;
		dx = frames.dx;
		nx = frames.nx;
		//markers are stored in integer bits 1(true) & 0(false)
		for (int i = 0; i < 8; i++) {		
			if (((frames.markers >> i) & 1) == 1) {
				markers[i] = true;
			} else {
				markers[i] = false;
			}
		}
		primarkers = frames.markers;
		formants = frames.formants;
		peaks = frames.peaks;
		klatt_param = frames.klaat_param;
		spect_data = frames.spect;
		//max_y = frames.max_y;
		for (int i = 0; i < nx; i++) {
			if (spect_data[i] > max_y)
				max_y = spect_data[i];
			if (nx * dx > max_x)
				max_x = (int) (nx * dx);
		}
	}
}
