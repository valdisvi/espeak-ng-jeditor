package org.espeakng.jeditor.data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import org.espeakng.jeditor.gui.MainWindow;
import org.espeakng.jeditor.jni.Formant_t;
import org.espeakng.jeditor.jni.Peak_t;
import org.espeakng.jeditor.jni.Wavegen_peaks_t;

public class Graph {
	
	
	static int harm_sqrt_n = 0;
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private JTabbedPane tabbedPaneGraphs;
	private JScrollPane filePanel;
	private double zoomAdjust = 1.1;
	private Map<JPanel, Frame> mapPanels; // each JPanel corresponds to a Frame
	Color[] colors = { new Color(255, 0, 0), new Color(255, 128, 0),
			new Color(255, 225, 0), new Color(0, 225, 0),
			new Color(0, 255, 255), new Color(255, 0, 255),
			new Color(0, 0, 255), new Color(76, 0, 153) };
	int keyframeWidth = 600; // default frame width 1000
	int keyframeHeight = keyframeWidth / 4; // default frame width 150
	int pk_shape1[] = { 255, 254, 254, 254, 254, 254, 253, 253, 252, 251, 251,
			250, 249, 248, 247, 246, 245, 244, 242, 241, 239, 238, 236, 234,
			233, 231, 229, 227, 225, 223, 220, 218, 216, 213, 211, 209, 207,
			205, 203, 201, 199, 197, 195, 193, 191, 189, 187, 185, 183, 180,
			178, 176, 173, 171, 169, 166, 164, 161, 159, 156, 154, 151, 148,
			146, 143, 140, 138, 135, 132, 129, 126, 123, 120, 118, 115, 112,
			108, 105, 102, 99, 96, 95, 93, 91, 90, 88, 86, 85, 83, 82, 80, 79,
			77, 76, 74, 73, 72, 70, 69, 68, 67, 66, 64, 63, 62, 61, 60, 59, 58,
			57, 56, 55, 55, 54, 53, 52, 52, 51, 50, 50, 49, 48, 48, 47, 47, 46,
			46, 46, 45, 45, 45, 44, 44, 44, 44, 44, 44, 44, 43, 43, 43, 43, 44,
			43, 42, 42, 41, 40, 40, 39, 38, 38, 37, 36, 36, 35, 35, 34, 33, 33,
			32, 32, 31, 30, 30, 29, 29, 28, 28, 27, 26, 26, 25, 25, 24, 24, 23,
			23, 22, 22, 21, 21, 20, 20, 19, 19, 18, 18, 18, 17, 17, 16, 16, 15,
			15, 15, 14, 14, 13, 13, 13, 12, 12, 11, 11, 11, 10, 10, 10, 9, 9,
			9, 8, 8, 8, 7, 7, 7, 7, 6, 6, 6, 5, 5, 5, 5, 4, 4, 4, 4, 4, 3, 3,
			3, 3, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0 };
	private int sel_peak = 0;
	private ArrayList<Frame> selectedFrames = new ArrayList<Frame>();
	private ArrayList<Frame> copyFrames = new ArrayList<Frame>();
	int max_x = 0;
	double max_y = 0;
	boolean gridEnable = true;

	public Graph(String fileName, ArrayList<Frame> frameList) {

		tabbedPaneGraphs = MainWindow.tabbedPaneGraphs;

		filePanel = new JScrollPane();

		// for correct order i use LinkedHashMap, because hashMap not guarantee
		// the insertion order.
		mapPanels = new LinkedHashMap<JPanel, Frame>();

		tabbedPaneGraphs = MainWindow.tabbedPaneGraphs;
		
		tabbedPaneGraphs.addTab(fileName, null, filePanel, null);
		tabbedPaneGraphs.setSelectedComponent(filePanel);
		ShowFrames(frameList, filePanel, mapPanels);

	}

	
	
	
	
	
	
	
	class Draw extends JPanel {
		
		
		
		
		
		
		
		
		
		
		public Frame currentFrame;
		int frame_width;
		double scalex;
		int pt;
		int peak;
		int ix;
		double x0, x1;
		int y0, y1;
		int x, x2, x3;
		double xinc;
		double yf;
		double scaley;
		double dx;
		int nx;
		int[] spect;
		double max_x;
		boolean bass_reduction;

		public Draw(Frame currentFrame, int keyframeWidth) {
			this.currentFrame = currentFrame;
			// keyframeWidth =

			max_x = currentFrame.max_x;
			frame_width = (int) (keyframeWidth * max_x) / 9500;
			if (frame_width > keyframeWidth)
				frame_width = keyframeWidth;

			scalex = (double) (frame_width / max_x);
			scaley = keyframeHeight / currentFrame.max_y;
			dx = currentFrame.dx;
			nx = currentFrame.nx;
			spect = currentFrame.spect_data;
			bass_reduction = Frame.bass_reduction;

		}

		protected void paintComponent(Graphics g) {
			Peak_t[] peaks = currentFrame.peaks;
			super.paintComponent(g);
			int[] x = new int[3];
			int[] y = { keyframeHeight, 0, keyframeHeight };
			int[][] points = new int[9][4];
			if (gridEnable) {
				g.setColor(new Color(235, 235, 235)); // light gray
				for (int j = 1; j < 18; j++) {
					g.drawLine(j * keyframeWidth / 18, 0, j * keyframeWidth
							/ 18, keyframeHeight);
				}
			}
			for (int i = 0; i < 9; i++) {
				if (i == sel_peak && currentFrame.selected) {
					points[i][0] = (int) (peaks[i].pkfreq * scalex); // peak x
																		// value
					points[i][1] = -(peaks[i].pkheight >> 6) / 3
							+ keyframeHeight; // peak
												// y
												// value
					points[i][2] = (int) (peaks[i].pkwidth * scalex * 0.44); // left
					// x
					points[i][3] = (int) (peaks[i].pkright * scalex * 0.44); // right
					// x

					if (y[1] < 5)
						y[1] = 5;

					x[0] = points[i][0] - points[i][2];
					x[1] = points[i][0];
					x[2] = points[i][0] + points[i][3];
					y[1] = points[i][1];

					g.setColor(Color.ORANGE);
					Polygon poly = new Polygon(x, y, 3);
					g.fillPolygon(poly);
					g.setColor(Color.RED);
					g.drawPolygon(poly);
				}
			}
			drawFormants(g);
			drawPeaks(peaks, g);
			// draws increments
			g.setColor(Color.BLACK);
			g.setFont(new Font(g.getFont().getFontName(), g.getFont()
					.getStyle(), keyframeHeight / 10 - 2));
			g.drawString(((int) (currentFrame.time * 1000)) + " ms" + "  "
					+ ((int) currentFrame.pitch) + " hz", keyframeWidth
					- keyframeWidth / 5, keyframeHeight / 5);
			// before this rms needs to be calculated
			// g.drawString(currentFrame.rms+" rms", keyframeWidth
			// - keyframeWidth / 5, keyframeHeight / 5+g.getFont().getSize()+2);
			int rectPosY = keyframeHeight / 10;
			int rectPosX = keyframeWidth - keyframeWidth / 5 - rectPosY - 3;

			for (int j = 0; j < 8; j++) {
				if (currentFrame.markers[j]) {
					g.setColor(colors[j]);
					g.fillRect(rectPosX, rectPosY, rectPosY, rectPosY);
					// if zoom
					if ((double) keyframeWidth / 600 > 1.0) {
						g.setColor(Color.WHITE);
						g.drawString("" + j, rectPosX + 2, rectPosY
								+ g.getFont().getSize() - 2);
					}
					rectPosX -= rectPosY + 3;
				}
			}
		}

		public void drawFormants(Graphics g) {
			xinc = dx * scalex;
			x0 = xinc;
			x1 = nx * xinc;
			Formant_t[] formants = currentFrame.formants;
			// draw the measured formants
			g.setColor(Color.BLUE);
			for (peak = 1; peak <= 5; peak++) {

				if (formants[peak].freq != 0) {

					// set height from linear interpolation of the adjacent
					// points in the spectrum
					pt = (int) (formants[peak].freq / dx);
					y0 = spect[pt - 1];
					y1 = spect[pt];
					yf = (y1 - y0) * (formants[peak].freq - pt * dx) / dx;

					y1 = keyframeHeight - (int) ((y0 + yf) * scaley);
					x1 = formants[peak].freq * scalex;
					g.drawLine((int) x1, keyframeHeight, (int) x1, y1);

				}
			}

			g.setColor(Color.BLACK);
			if (spect != null) {
				y0 = keyframeHeight - (int) (spect[0] * scaley);
				for (pt = 1; pt < nx; pt++) {
					x1 = x0 + xinc;
					y1 = keyframeHeight
							- (int) (SpectTilt(spect[pt], (int) (pt * dx),
									Frame.bass_reduction) * scaley);
					g.drawLine(((int) x0), y0, ((int) x1), y1);
					x0 = x1;
					y0 = y1;
				}
			}

		}
		
		
		
		
		
	/*	
		
		
		public double getRms(int seqAmplitude){
			double rms;
			int h;
			float total=0;
			int maxh;
			int height;
			int[] htab = new int[400];
			Wavegen_peaks_t[] wpeaks = new Wavegen_peaks_t[9];

			for(h=0; h<9; h++)
			{
				height = (currentFrame.peaks[h].pkheight * seqAmplitude * currentFrame.amp_adjust)/10000;
				wpeaks[h].height = height << 8;

				wpeaks[h].freq = currentFrame.peaks[h].pkfreq << 16;
				wpeaks[h].left = currentFrame.peaks[h].pkwidth << 16;
				wpeaks[h].right = currentFrame.peaks[h].pkright << 16;
			}
			
			maxh = PeaksToHarmspect(wpeaks,90<<16,htab,0);
			for(h=1; h<maxh; h++)
			{
				total += ((htab[h] * htab[h]) >> 10);
			}
			rms = Math.sqrt(total) / 7.25;
//			DrawPeaks(NULL,0,0,amp);
		return(rms);
			
		}
		
		
		
		
		
		
		
		int PeaksToHarmspect(Wavegen_peaks_t[] peaks, int pitch, int[] htab, int control)
		{//============================================================================
		// Calculate the amplitude of each  harmonics from the formants
		// Only for formants 0 to 5

		// control 0=initial call, 1=every 64 cycles

		   // pitch and freqs are Hz<<16

			int f;
			Wavegen_peaks_t p;
			int fp;   // centre freq of peak
			int fhi;  // high freq of peak
			int h;    // harmonic number
			int pk;
			int hmax;
			int hmax_samplerate;      // highest harmonic allowed for the samplerate
			int x;
			int ix;
			int h1;


			if(harm_sqrt_n > 0)
				return(HarmToHarmspect(pitch,htab));


			// initialise as much of *out as we will need
			if(wvoice == NULL)
				return(1);
			hmax = (peaks[wvoice->n_harmonic_peaks].freq + peaks[wvoice->n_harmonic_peaks].right)/pitch;
			if(hmax >= MAX_HARMONIC)
				hmax = MAX_HARMONIC-1;
			
			// restrict highest harmonic to half the samplerate
			hmax_samplerate = (((samplerate * 19)/40) << 16)/pitch;   // only 95% of Nyquist freq
//			hmax_samplerate = (samplerate << 16)/(pitch*2);

			if(hmax > hmax_samplerate)
				hmax = hmax_samplerate;

			for(h=0;h<=hmax;h++)
				htab[h]=0;

			h=0;
			for(pk=0; pk<=wvoice->n_harmonic_peaks; pk++)
			{
				p = &peaks[pk];
				if((p->height == 0) || (fp = p->freq)==0)
					continue;

				fhi = p->freq + p->right;
				h = ((p->freq - p->left) / pitch) + 1;
				if(h <= 0) h = 1;

				for(f=pitch*h; f < fp; f+=pitch)
				{
					htab[h++] += pk_shape[(fp-f)/(p->left>>8)] * p->height;
				}
				for(; f < fhi; f+=pitch)
				{
					htab[h++] += pk_shape[(f-fp)/(p->right>>8)] * p->height;
				}
			}

		{
		int y;
		int h2;
			// increase bass
			y = peaks[1].height * 10;   // addition as a multiple of 1/256s
			h2 = (1000<<16)/pitch;       // decrease until 1000Hz
			if(h2 > 0)
			{
				x = y/h2;
				h = 1;
				while(y > 0)
				{
					htab[h++] += y;
					y -= x;
				}
			}
		}

			// find the nearest harmonic for HF peaks where we don't use shape
			for(; pk<N_PEAKS; pk++)
			{
				x = peaks[pk].height >> 14;
				peak_height[pk] = (x * x * 5)/2;

				// find the nearest harmonic for HF peaks where we don't use shape
				if(control == 0)
				{
					// set this initially, but make changes only at the quiet point
					peak_harmonic[pk] = peaks[pk].freq / pitch;
				}
				// only use harmonics up to half the samplerate
				if(peak_harmonic[pk] >= hmax_samplerate)
					peak_height[pk] = 0;
			}

			// convert from the square-rooted values
			f = 0;
			for(h=0; h<=hmax; h++, f+=pitch)
			{
				x = htab[h] >> 15;
				htab[h] = (x * x) >> 8;

				if((ix = (f >> 19)) < N_TONE_ADJUST)
				{
					htab[h] = (htab[h] * wvoice->tone_adjust[ix]) >> 13;  // index tone_adjust with Hz/8
				}
			}

			// adjust the amplitude of the first harmonic, affects tonal quality
			h1 = htab[1] * option_harmonic1;
			htab[1] = h1/8;


			// calc intermediate increments of LF harmonics
			if(control & 1)
			{
				for(h=1; h<N_LOWHARM; h++)
				{
					harm_inc[h] = (htab[h] - harmspect[h]) >> 3;
				}
			}

			return(hmax);  // highest harmonic number
		} 
		
		
		
		
		*/
		
		
		
		
		
		public void drawPeaks(Peak_t[] peaks, Graphics g) {

			int x1, x2, x3, width, ix;
			int y1, y2;
			double yy;
			int max_ix;
			int height;
			int pkright;
			int pkwidth;
			int[] buf = new int[4000];
			double max_x = currentFrame.max_x;

			int frame_width = (int) ((keyframeWidth * max_x) / 9500);
			// if (frame_width > keyframeWidth)
			// frame_width = keyframeWidth;
			double scalex = (double) frame_width / max_x;

			max_ix = (int) (9000 * scalex);
			Arrays.fill(buf, 0);

			g.setColor(Color.GREEN);

			for (peak = 0; peak < 9; peak++) {

				if ((peaks[peak].pkfreq == 0) || (peaks[peak].pkheight == 0))
					continue;

				height = peaks[peak].pkheight;
				pkright = peaks[peak].pkright;
				pkwidth = peaks[peak].pkwidth;

				x1 = (int) (peaks[peak].pkfreq * scalex);
				x2 = (int) ((peaks[peak].pkfreq + pkright) * scalex);
				x3 = (int) ((peaks[peak].pkfreq - pkwidth) * scalex);

				if (x3 >= keyframeWidth)
					continue; // whole peak is off the scale

				if ((width = x2 - x1) <= 0)
					continue;
				for (ix = 0; ix < width; ix++) {
					buf[x1 + ix] += height * pk_shape1[(ix * 256) / width];
				}

				if ((width = x1 - x3) <= 0)
					continue;
				for (ix = 1; ix < width; ix++) {
					if (x3 + ix >= 0) {
						buf[x3 + ix] += height
								* pk_shape1[((width - ix) * 256) / width];
					}
				}
			}

			// rms = buf[0] >> 12;
			// rms = rms * rms * 23;
			// rms = rms * rms;

			x1 = 0;
			y1 = keyframeHeight - ((buf[0] * keyframeHeight) >> 21);
			for (ix = 1; ix < max_ix; ix++) {

				yy = buf[ix] >> 12;
				yy = yy * yy * 23;
				// rms += (yy * yy);

				x2 = ix;
				y2 = keyframeHeight - ((buf[ix] * keyframeHeight) >> 21);
				// if(dc != NULL) dc->DrawLine(x1,y1,x2,y2);

				g.drawLine(x1, y1, x2, y2);
				x1 = x2;
				y1 = y2;
			}

			// rms = Math.sqrt(rms) / 200000.0;
			// apply adjustment from spectseq amplitude
			// rms = rms * seq_amplitude * currentFrame.amp_adjust / 10000.0;

			// rms = GetRms(seq_amplitude);
		} // end of SpectFrame::DrawPeaks
	}

	double SpectTilt(int value, int freq, boolean bass_reduction) {
		double x;
		double y;
		if (bass_reduction)
			return value;
		y = value * value * 2;

		if (freq < 600) {
			return (Math.sqrt(y / 2.5));
		} else if (freq < 1050) {
			x = 1.0 + ((1050.0 - freq) * 1.5) / 450.0;
			return (Math.sqrt(y / x));
		} else {
			return (Math.sqrt(y));
		}
	}

	public JScrollPane getjPanelOfGraph() {
		return filePanel;
	}

	// to get first element in mapPanels
	public void loadFirstFrame() {
		// load first frame

		Map.Entry<JPanel, Frame> entry = mapPanels.entrySet().iterator().next();
		entry.getKey().requestFocus();
		loadFrame(entry.getKey());
	}

	public void loadFrame(JPanel currentPanel) {

		Frame frameToLoad = mapPanels.get(currentPanel);
		currentPanel.requestFocus();
		frameToLoad.selected = true;
		selectedFrames.clear();
		selectedFrames.add(frameToLoad);

		for (Map.Entry<JPanel, Frame> entry : mapPanels.entrySet()) {
			if (!entry.getValue().equals(frameToLoad)) {
				Border raisedbevel = BorderFactory.createRaisedBevelBorder();
				Border loweredbevel = BorderFactory.createLoweredBevelBorder();
				entry.getValue().selected = false;
				entry.getKey().setBorder(
						BorderFactory.createCompoundBorder(raisedbevel,
								loweredbevel));
			}
		}
		currentPanel.requestFocus();
		currentPanel.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1,
				Color.red));

		Peak_t[] peaks = frameToLoad.peaks;
		String value;

		for (int i = 0; i < 7; i++) {
			MainWindow.tfFreq.get(i).setText("" + peaks[i].pkfreq);
		}
		for (int i = 0; i < 8; i++) {
			MainWindow.tfHeight.get(i).setText("" + (peaks[i].pkheight >> 6));
		}
		for (int i = 0; i < 6; i++) {
			value = (peaks[i].pkright == peaks[i].pkwidth) ? ("" + (peaks[i].pkright / 2))
					: ("" + (peaks[i].pkright / 2) + "/" + (peaks[i].pkwidth / 2));
			MainWindow.tfWidth.get(i).setText(value);
		}
		for (int i = 0; i < 3; i++) {
			MainWindow.tfBw.get(i).setText("" + (peaks[i + 1].klt_bw));
		}
		for (int i = 0; i < 6; i++) {
			MainWindow.tfAp.get(i).setText("" + (peaks[i].klt_ap));
		}
		for (int i = 0; i < 6; i++) {
			MainWindow.tfBp.get(i).setText("" + (peaks[i + 1].klt_bp));
		}

		MainWindow.tfmS
				.setText(String.valueOf((int) (frameToLoad.time * 1000)));
		MainWindow.spampF.setValue(frameToLoad.amp_adjust);

	}

	KeyListener keyListener = new KeyListener() {

		public void keyPressed(KeyEvent ke) {
			int x_incr = 0;
			int y_incr = 0;
			final int[] incr_1 = { 4, 4, 4, 8, 8, 8, 8, 8, 8 };
			final int[] incr_2 = { 8, 8, 20, 20, 20, 20, 25, 25, 25 };

			final int MAX_DISPLAY_FREQ = 9500;

			JPanel prev = null, curr, next = null;
			curr = (JPanel) ke.getSource();

			next = curr;
			prev = curr;

			for (Iterator<JPanel> i = mapPanels.keySet().iterator(); i
					.hasNext();) {
				JPanel element = i.next();

				if (element.equals(curr)) {
					if (i.hasNext()) {
						next = i.next();
					}
					break;
				}
				prev = element;

			}

			Frame frameToLoad = mapPanels.get(curr);

			boolean shift = ke.isShiftDown();
			boolean control = ke.isControlDown();

			if (shift) {
				x_incr = incr_1[sel_peak];
				y_incr = 0x20;
			} else {
				x_incr = incr_2[sel_peak];
				y_incr = 0x80;
			}

			switch (ke.getKeyCode()) { // frequency ++
			case KeyEvent.VK_RIGHT: {
				frameToLoad.peaks[sel_peak].pkfreq += x_incr;
				if (frameToLoad.peaks[sel_peak].pkfreq >= MAX_DISPLAY_FREQ) {
					frameToLoad.peaks[sel_peak].pkfreq = MAX_DISPLAY_FREQ;
				}
				adjustPeaks(frameToLoad, sel_peak, 1);
				loadFrame(curr);
				curr.repaint();
				curr.validate();
				break;
			}
			case KeyEvent.VK_LEFT: { // frequency --
				frameToLoad.peaks[sel_peak].pkfreq -= x_incr;
				if (frameToLoad.peaks[sel_peak].pkfreq < 50) {
					frameToLoad.peaks[sel_peak].pkfreq = 50;
				}
				adjustPeaks(frameToLoad, sel_peak, -1);
				loadFrame(curr);
				curr.validate();
				curr.repaint();
				break;
			}
			case KeyEvent.VK_UP: {// height ++
				frameToLoad.peaks[sel_peak].pkheight += y_incr;
				loadFrame(curr);
				curr.validate();
				curr.repaint();
				break;
			}
			case KeyEvent.VK_DOWN: { // height --
				if ((mapPanels.get(curr).peaks[sel_peak].pkfreq - y_incr) >= 0) {
					frameToLoad.peaks[sel_peak].pkheight -= y_incr;
					loadFrame(curr);
					curr.validate();
					curr.repaint();
				}
				break;
			}
			case KeyEvent.VK_PERIOD: { // ++ witdth
				if ((mapPanels.get(curr).peaks[sel_peak].pkfreq - 5) < 0) {
					frameToLoad.peaks[sel_peak].pkright = 0;
				} else {
					if (control) {
						frameToLoad.peaks[sel_peak].pkright -= 5; // pkwidth
						frameToLoad.peaks[sel_peak].pkwidth += 5; // pkright
					} else {
						frameToLoad.peaks[sel_peak].pkright += 10;
						frameToLoad.peaks[sel_peak].pkwidth += 10;
					}
					loadFrame(curr);
					curr.validate();
					curr.repaint();
				}
				break;
			}
			case KeyEvent.VK_COMMA: { // -- width
				if ((mapPanels.get(curr).peaks[sel_peak].pkfreq - 5) < 0) {
					frameToLoad.peaks[sel_peak].pkright = 0;
				} else {
					if (control) {
						frameToLoad.peaks[sel_peak].pkright += 5; // pkwidth
						frameToLoad.peaks[sel_peak].pkwidth -= 5; // pkright
					} else {
						frameToLoad.peaks[sel_peak].pkwidth -= 10;
						frameToLoad.peaks[sel_peak].pkright -= 10;
						if (frameToLoad.peaks[sel_peak].pkwidth < 0) {
							frameToLoad.peaks[sel_peak].pkwidth = 0;
						}
						if (frameToLoad.peaks[sel_peak].pkright < 0) {
							frameToLoad.peaks[sel_peak].pkright = 0;
						}
					}
					loadFrame(curr);
					curr.validate();
					curr.repaint();
				}
				break;
			}

			case KeyEvent.VK_OPEN_BRACKET: { // width--
				frameToLoad.peaks[sel_peak].pkwidth -= 10;
				if (frameToLoad.peaks[sel_peak].pkwidth < 0) {
					frameToLoad.peaks[sel_peak].pkwidth = 0;
				}
				loadFrame(curr);
				curr.validate();
				curr.repaint();
				break;
			}
			case KeyEvent.VK_CLOSE_BRACKET: { // width++
				frameToLoad.peaks[sel_peak].pkwidth += 10;
				loadFrame(curr);
				curr.validate();
				curr.repaint();
				break;
			}
			case KeyEvent.VK_SLASH: { // make peak symetrical
				int i = frameToLoad.peaks[sel_peak].pkright
						+ frameToLoad.peaks[sel_peak].pkwidth;
				frameToLoad.peaks[sel_peak].pkright = frameToLoad.peaks[sel_peak].pkright = (short) (i / 2);
				loadFrame(curr);
				curr.validate();
				curr.repaint();
				break;
			}
			case KeyEvent.VK_PAGE_UP: { // previous frame
				prev.requestFocus();
				loadFrame(prev);
				for (Iterator<JPanel> i = mapPanels.keySet().iterator(); i
						.hasNext();) {
					JPanel element = i.next();

					element.repaint();
					element.revalidate();
				}

				break;
			}
			case KeyEvent.VK_PAGE_DOWN: { // next frame
				next.requestFocus();
				loadFrame(next);

				for (Iterator<JPanel> i = mapPanels.keySet().iterator(); i
						.hasNext();) {
					JPanel element = i.next();

					element.repaint();
					element.revalidate();
				}
				break;
			}

			case KeyEvent.VK_Z: // previous peak
				if (sel_peak > 0) {
					sel_peak--;
					curr.repaint();
					curr.revalidate();
				}
				break;

			case KeyEvent.VK_X: // next peak
				if (sel_peak < 7) {
					sel_peak++;
					curr.repaint();
					curr.revalidate();
				}
				break;
			default: {
				if (ke.getKeyCode() >= 0x30 && ke.getKeyCode() < 0x39) {
					sel_peak = ke.getKeyCode() - 48;
					curr.repaint();
					curr.revalidate();
				}
				break;
			}
			}

			// control pressed down
			if (control) {
				switch (ke.getKeyCode()) {
				case KeyEvent.VK_A: { // CTRL-A
					selectedFrames.clear();
					selectedFrames = new ArrayList<Frame>(mapPanels.values());

					for (Iterator<JPanel> i = mapPanels.keySet().iterator(); i
							.hasNext();) {
						JPanel element = i.next();
						element.setBorder(BorderFactory.createMatteBorder(1, 5,
								1, 1, Color.red));
						mapPanels.get(element).selected = true;
					}

					break;
				}
				case KeyEvent.VK_C: { // CTRL-C
					copyFrames.clear();
					copyFrames = new ArrayList<Frame>(selectedFrames);
					selectedFrames.clear();
					break;
				}
				case KeyEvent.VK_X: { // CTRL-X
					copyFrames.clear();
					copyFrames.addAll(mapPanels.values());
					copyFrames.removeAll(selectedFrames);
					ArrayList<Frame> temp = new ArrayList<Frame>(selectedFrames);
					ShowFrames(copyFrames, filePanel, mapPanels);
					copyFrames.clear();
					copyFrames.addAll(temp);
					temp.clear();
					break;
				}
				case KeyEvent.VK_V: { // CTRL-V
					if (!copyFrames.isEmpty()) {
						if (shift) // shift pressed down
						{
							selectedFrames = new ArrayList<Frame>(
									mapPanels.values());
							selectedFrames.addAll(copyFrames);
							copyFrames = new ArrayList<Frame>(selectedFrames);
							ShowFrames(copyFrames, filePanel, mapPanels);
						} else {
							ShowFrames(copyFrames, filePanel, mapPanels);
						}
					}
					break;
				}
				case KeyEvent.VK_Z: { // CTRL-Z
					for (int i = 0; i < 8; i++) {
						frameToLoad.peaks[i].pkheight = 0;
						curr.repaint();
						curr.revalidate();
					}
					break;
				}
				case KeyEvent.VK_I: { // CTRL-I

					if (curr == prev) {
						JOptionPane.showMessageDialog(curr,
								"No previous keyframe!");
						break;
					} else if (curr == next) {
						JOptionPane.showMessageDialog(curr,
								"No subsequent keyframe!");
						break;
					} else {
						interpolateAdjacent(mapPanels.get(prev),
								mapPanels.get(curr), mapPanels.get(next));
						loadFrame(curr);
						curr.repaint();
						curr.revalidate();
					}
					break;
				}
				case KeyEvent.VK_B: { // CTRL-B

					if (Frame.bass_reduction) {
						Frame.bass_reduction = false;
					} else {
						Frame.bass_reduction = true;
						;
					}
					loadFrame(curr);
					curr.repaint();
					curr.revalidate();
					break;
				}
				case KeyEvent.VK_G: { // CTRL-G toggle grid
					if (gridEnable) {
						gridEnable = false;
					} else {
						gridEnable = true;
					}
					loadFrame(curr);
					curr.repaint();
					curr.revalidate();
					break;
				}
				case KeyEvent.VK_N: { // CTRL-N, put in new marker

					String[] possibleValues = { "0", "1", "2", "3", "4", "5",
							"6", "7" };
					int inputValue = Integer.parseInt((String) JOptionPane
							.showInputDialog(null, "Marker", "Toggle marker",
									JOptionPane.PLAIN_MESSAGE, null,
									possibleValues, possibleValues[0]));
					if (mapPanels.get(curr).markers[inputValue]) {
						mapPanels.get(curr).markers[inputValue] = false;
					} else {
						mapPanels.get(curr).markers[inputValue] = true;
					}
					loadFrame(curr);
					curr.repaint();
					curr.revalidate();
					break;
				}
				case KeyEvent.VK_M: { // CTRL-M

					if (mapPanels.get(curr).markers[0]) {
						mapPanels.get(curr).markers[0] = false;
					} else {
						mapPanels.get(curr).markers[0] = true;
					}
					loadFrame(curr);
					curr.repaint();
					curr.revalidate();
					break;
				}
				case KeyEvent.VK_S: { // CTRL-S

					// public String type; // Type-name of file
					// (SPECTSEQ,SPECTSEK,SPECTSQ2)
					// public int file_format;
					// public int name_length;
					// public int n;
					// public int amplitude;
					// public int max_y;
					// public String fileName;
					// public ArrayList<Frame> frameList;
					// private Graph graph;
					//
					// String type frameToLoad.;
					// int file_format;
					// int name_length;
					// int n;
					// int amplitude = frameToLoad.amp_adjust;
					// int max_y = frameToLoad.max_y;
					// String fileName = filePanel.getName();
					// ArrayList<Frame> frameList = new
					// ArrayList<Frame>(mapPanels.values());
					//
					// if(type.equals("SPECTSPC2")) {
					// // implement support of old SPECTSPC2 files loading
					// } else if (type.equals("SPECTSEQ")) {
					// file_format = 0;
					// } else if (type.equals("SPECTSEK")) {
					// file_format = 1;
					// } else if (type.equals("SPECTSQ2")) {
					// file_format = 2;
					// }
					//

					// p.amplitude =
					// p.frameList = new ArrayList<Frame>(mapPanels.values());
					// p.max_y =
					// p.file_format = 1;
					// p.fileName = filePanel.getName();

					// saveToDirectory(p, file);
				}
				}
			}
		}

		public void keyReleased(KeyEvent ke) {
		}

		public void keyTyped(KeyEvent ke) {
		}

	};

	public void ShowFrames(ArrayList<Frame> frames,
			final JScrollPane filePanel2, final Map<JPanel, Frame> mapPanels) {
		filePanel2.removeAll();
		mapPanels.clear();
		filePanel2.setPreferredSize(new Dimension(0, 165 * frames.size()));         // PANEL RESIZING DEPENDING ON GRAPH COUNT

		int y = 25;
		if (!frames.isEmpty()) {
			for (int i = 0; i < frames.size(); i++) {

				Frame currentFrame = frames.get(i);
				currentFrame.selected = false;
				if (max_x < currentFrame.max_x)
					max_x = currentFrame.max_x;
				max_y = currentFrame.max_y;
				final JPanel keyframe = new Draw(currentFrame, keyframeWidth);

				Border raisedbevel = BorderFactory.createRaisedBevelBorder();
				Border loweredbevel = BorderFactory.createLoweredBevelBorder();
				keyframe.setBounds(10, y, keyframeWidth, keyframeHeight);
				keyframe.setBackground(new Color(255, 253, 250)); // light
																	// yellow
																	// (creamy)
																	// color
				keyframe.setBorder(BorderFactory.createCompoundBorder(
						raisedbevel, loweredbevel));
				keyframe.setVisible(true);
				y += keyframeHeight + 5;
				keyframe.addKeyListener(keyListener);

				keyframe.addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent e) {

						if ((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
							Frame f = mapPanels.get(keyframe);

							if (!f.selected) {
								keyframe.setBorder(BorderFactory
										.createMatteBorder(1, 5, 1, 1,
												Color.red));
								f.selected = true;
								selectedFrames.add(f);
							} else {
								Border raisedbevel = BorderFactory
										.createRaisedBevelBorder();
								Border loweredbevel = BorderFactory
										.createLoweredBevelBorder();
								keyframe.setBorder(BorderFactory
										.createCompoundBorder(raisedbevel,
												loweredbevel));
								selectedFrames.remove(f);
								f.selected = false;
							}
						} else {
							Border raisedbevel = BorderFactory
									.createRaisedBevelBorder();
							Border loweredbevel = BorderFactory
									.createLoweredBevelBorder();
							keyframe.setBorder(BorderFactory
									.createCompoundBorder(raisedbevel,
											loweredbevel));
							loadFrame((JPanel) e.getSource());
							keyframe.requestFocus();
						}

						if (e.getSource().equals(filePanel2)) {
							selectedFrames.clear();

							for (Map.Entry<JPanel, Frame> entry : mapPanels
									.entrySet()) {

								Border raisedbevel = BorderFactory
										.createRaisedBevelBorder();
								Border loweredbevel = BorderFactory
										.createLoweredBevelBorder();
								entry.getValue().selected = false;
								entry.getKey().setBorder(
										BorderFactory.createCompoundBorder(
												raisedbevel, loweredbevel));
							}

						}
					}

					public void mouseEntered(MouseEvent arg0) {
					}

					public void mouseExited(MouseEvent arg0) {
					}

					public void mousePressed(MouseEvent arg0) {
					}

					public void mouseReleased(MouseEvent arg0) {
					}
				});

				filePanel2.add(keyframe);
				mapPanels.put(keyframe, currentFrame);

			}
			loadFirstFrame();
		}
		filePanel2.revalidate();
		filePanel2.repaint();
		filePanel2.addKeyListener(keyListener);
		// filePanel.requestFocus();

	}

	//
	public void adjustPeaks(Frame currentFrame, int curr_peak, int i) {
		if (i < 0) {
			for (int n = curr_peak - 1; n >= 0; n--) {
				if (currentFrame.peaks[n].pkfreq > currentFrame.peaks[n + 1].pkfreq - 100) {
					currentFrame.peaks[n].pkfreq = (short) (currentFrame.peaks[n + 1].pkfreq - 100);
				}
			}
		}
		if (i > 0) {
			for (int n = curr_peak + 1; n < 9; n++) {
				if (currentFrame.peaks[n].pkfreq < currentFrame.peaks[n - 1].pkfreq + 100) {
					currentFrame.peaks[n].pkfreq = (short) (currentFrame.peaks[n - 1].pkfreq + 100);
				}
			}
		}
	}

	public void interpolateAdjacent(Frame prev, Frame curr, Frame next) {
		int inputValue;
		float ratio;
		inputValue = Integer.parseInt(JOptionPane
				.showInputDialog("Input interpolation percentage!"));
		ratio = (float) (inputValue / 100.0);
		for (int n = 0; n < 9; n++) {
			curr.peaks[n].pkfreq += ((next.peaks[n].pkfreq - prev.peaks[n].pkfreq) * ratio);
			curr.peaks[n].pkheight += ((next.peaks[n].pkheight - prev.peaks[n].pkheight) * ratio);
			curr.peaks[n].pkright += ((next.peaks[n].pkright - prev.peaks[n].pkright) * ratio);
			curr.peaks[n].pkwidth += ((next.peaks[n].pkwidth - prev.peaks[n].pkwidth) * ratio);
		}
	}

	public void zoom(int flag) {
		final ArrayList<Frame> tmp = new ArrayList<Frame>(mapPanels.values());

		if (flag < 0) {
			if (!(keyframeWidth / zoomAdjust < 335)) {
				keyframeWidth /= zoomAdjust;
				keyframeHeight = keyframeWidth / 4;
				ShowFrames(tmp, filePanel, mapPanels);
			}
		}
		if (flag > 0) {
			keyframeWidth *= zoomAdjust;
			keyframeHeight = keyframeWidth / 4;
			ShowFrames(tmp, filePanel, mapPanels);
		}
	}
}
