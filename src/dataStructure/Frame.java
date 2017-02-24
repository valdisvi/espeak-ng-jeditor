package dataStructure;


import java.io.ByteArrayInputStream;

import dataStructure.eSpeakStructure.Formant_t;
import dataStructure.eSpeakStructure.Peak_t;
import dataStructure.eSpeakStructure.SpectFrame;
import dataStructure.eSpeakStructure.SpectSeq;

public class Frame {
	public double time;
	public double pitch;
	public double length;
	public double dx;
	public int nx;
//	boolean[] markers = new boolean[] { false, false, false, false, false,
//			false, false, false };

	public int primarkers;
	public int amp_adjust;
	// [][0] freq [][1] bandw
//	public int[][] formants;
	public Formant_t[] formants;
	// [][0]pkfreq [][1]pkheight [][2]pkright [][3]pkwidth [][4]klt_bw
	// [][5]klt_ap [][6]klt_bp
//	public int[][] peaks;
	public Peak_t[] peaks;

	// Not used in SPECTSEQ
	public short[] klatt_param;
	public int[] spect_data;
	double max_y = 0;
	int max_x = 3000;
	static int default_freq[] = { 200, 500, 1200, 3000, 3500, 4000, 6900, 7800,
			9000 };
	static int default_width[] = { 750, 500, 550, 550, 600, 700, 700, 700, 700 };
	static int default_klt_bw[] = { 89, 90, 140, 260, 260, 260, 500, 500, 500 };
	public boolean selected = false;
	public static boolean bass_reduction = false;
	public void frameLoader(SpectFrame frames, int file_format) {
		time = frames.time;
		System.out.println("time "+time);
		pitch = frames.pitch;
		System.out.println("pitch "+pitch);

		length = frames.length;
		System.out.println("length "+length);
		dx = frames.dx;
		System.out.println("dx "+dx);
		nx = frames.nx;
		System.out.println("nx "+nx);
		primarkers = frames.markers;
		System.out.println("primarkers "+primarkers);
		formants = frames.formants;
		System.out.println("formants "+formants);
		peaks = frames.peaks;
		for(Peak_t peak : peaks){
			System.out.println("peak.pkfreq "+peak.pkfreq);

		}
		klatt_param = frames.klaat_param;
		System.out.println("klatt_param "+klatt_param);
		spect_data = frames.spect;
		System.out.println("spect_data "+spect_data);
		max_y = frames.max_y;
		System.out.println("max_y "+max_y);
		for (int i = 0; i < nx; i++) {
			if (spect_data[i] > max_y)
				max_y = spect_data[i];
			if (nx * dx > max_x)
				max_x = (int) (nx * dx);
		}
		System.out.println("max_x "+max_x);
		
	}
	/*	public void frameLoader(ByteArrayInputStream inRead, int file_format) {


		formants = new int[9][2];
		// There are 7 elements in SPECTSQ2 files, only 4 in TSEQ and TSEK
		peaks = new int[9][7];
		klatt_param = new int[14];

		for (int i = 0; i < 9; i++) {
			formants[i][0] = 0;
			peaks[i][0] = default_freq[i];
			peaks[i][1] = 0;
			peaks[i][2] = default_width[i];
			peaks[i][3] = default_width[i];
			peaks[i][4] = default_klt_bw[i];
			peaks[i][5] = 0;
			peaks[i][6] = default_klt_bw[i];
		}

		byte[] buffer = new byte[8];
		inRead.read(buffer, 0, 8);
		time = Phoneme.byteWrapper(buffer);
		// System.out.println("time "+time);

		inRead.read(buffer, 0, 8);
		pitch = Phoneme.byteWrapper(buffer);
		// System.out.println("pitch "+pitch);

		inRead.read(buffer, 0, 8);
		length = Phoneme.byteWrapper(buffer);
		// System.out.println("length "+length);

		inRead.read(buffer, 0, 8);
		dx = Phoneme.byteWrapper(buffer);
		// System.out.println("dx "+dx);
		inRead.read(buffer, 0, 8);

		buffer = new byte[2];
		inRead.read(buffer, 0, 2);
		nx = Phoneme.byteWrapper(buffer);
		// System.out.println("nx "+nx);

		inRead.read(buffer, 0, 2);
		primarkers = Phoneme.byteWrapper(buffer);
		// System.out.println("primarkers "+primarkers);

		inRead.read(buffer, 0, 2);
		amp_adjust = Phoneme.byteWrapper(buffer);
		// System.out.println("amp_adjust "+amp_adjust+"\n");

		if (file_format == 2) {
			inRead.read(buffer, 0, 2);
			inRead.read(buffer, 0, 2);
		}

		// N_PEAKS defined as 9
		for (int i = 0; i < 9; i++) {

			inRead.read(buffer, 0, 2);
			formants[i][0] = Phoneme.byteWrapper(buffer);

			inRead.read(buffer, 0, 2);
			formants[i][1] = Phoneme.byteWrapper(buffer);

			inRead.read(buffer, 0, 2);
			peaks[i][0] = Phoneme.byteWrapper(buffer);

			inRead.read(buffer, 0, 2);
			peaks[i][1] = Phoneme.byteWrapper(buffer);

			inRead.read(buffer, 0, 2);
			peaks[i][2] = Phoneme.byteWrapper(buffer);

			inRead.read(buffer, 0, 2);
			peaks[i][3] = Phoneme.byteWrapper(buffer);

			if (file_format == 2) {
				inRead.read(buffer, 0, 2);
				peaks[i][4] = Phoneme.byteWrapper(buffer);

				inRead.read(buffer, 0, 2);
				peaks[i][5] = Phoneme.byteWrapper(buffer);

				inRead.read(buffer, 0, 2);
				peaks[i][6] = Phoneme.byteWrapper(buffer);
			}
		}

		// N_KLATTP2 defined as 14
		if (file_format > 0) {
			for (int i = 0; i < 14; i++) {
				inRead.read(buffer, 0, 2);
				klatt_param[i] = Phoneme.byteWrapper(buffer);
			}
		}

		spect_data = new int[nx];
		while (dx > 10 || dx < -10) {
			dx /= 10;
		}
		for (int i = 0; i < nx; i++) {
			inRead.read(buffer, 0, 2);
			spect_data[i] = Phoneme.byteWrapper(buffer);
			if (spect_data[i] > max_y)
				max_y = spect_data[i];
			if (nx * dx > max_x)
				max_x = (int) (nx * dx);
		}
		while (max_x > 100 || max_x < -100) {
			max_x /= 10;
		}

	}

	public Peak_t[] getPeaks() {
		return peaks;
	}

	public Formant_t[] getFormants() {
		return formants;
	}

	public int[] getSpect() {
		return spect_data;
	}
*/
}
