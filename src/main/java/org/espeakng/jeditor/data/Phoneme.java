package org.espeakng.jeditor.data;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.espeakng.jeditor.jni.ESpeakService;
import org.espeakng.jeditor.jni.SpectSeq;
/**
 * This class is the main object that program works on, contains loaded SpectSeq, and some fields
 * with the same values of this SpectSeq, and generated frames and graphs for this SpectSeq, etc
 * 
 */
public class Phoneme{
	SpectSeq spect = new SpectSeq();
	public String type; // Type-name of file (SPECTSEQ,SPECTSEK,SPECTSQ2)
	public int file_format;
	public int name_length;
	public int n;
	public int amplitude;
	public int max_y;
	public String fileName;
	private ArrayList<Frame> frameList;
	private Graph graph;
	public String path;


	/**
	 * Constructor for Phoneme, it loads SpectSeq, from given file using JNI implementation,
	 * then uses that data to create frames and graph, etc.
	 * 
	 * @param file - phoneme file to be loaded
	 */
	public Phoneme(File file) {
		path = file.getAbsolutePath();
		ESpeakService.nativeGetSpectSeq(spect, path);
		file_format = spect.file_format;
		fileName = spect.name;
		n = spect.numframes;
		amplitude = spect.amplitude;
		max_y = spect.max_y;
		frameList = new ArrayList<Frame>();
		name_length = spect.name.length();

		for (int i = 0; i < n; i++) {
			// SpectFrame *frame = new SpectFrame;
			Frame newFrame = new Frame();
			// Call SPECTSEQ type load
			if ((file_format == 0) || (file_format == 1) || (file_format == 2)) {
				newFrame.frameLoader(spect.frames[i], file_format, max_y, amplitude);
			}
			frameList.add(newFrame);
		}
		if (!file.exists())
			assert false : "From Phoneme class. File doesn't exist.";
		
		graph = new Graph(file.getName(), frameList);

	}
	/**
	 * Getter for graph
	 * 
	 * @return graph
	 */
	public Graph getGraph() {
		return graph;
	}
	/**
	 * Calls graph method of the same name, to load first frame
	 */
	public void loadFirstFrame() {
		graph.loadFirstFrame();
	}
	/**
	 * Calls graph method passing 1 as argument to zoom in
	 */
	public void doZoomIn() {
		graph.zoom(1);
	}
	/**
	 * Calls graph method passing -1 as argument to zoom out
	 */
	public void doZoomOut() {
		graph.zoom(-1);
	}

	/**
	 * Custom byte wrapping method e.g. ByteOrder.LITTLE_ENDIAN
	 * @param inc
	 * @return
	 */
	public static int byteWrapper(byte[] inc) {
		int value = 0;
		byte[] wrpd = new byte[inc.length];
		for (int i = 0; i < inc.length; i++) {
			wrpd[i] = inc[(inc.length - 1) - i];
		}

		if (inc.length >= 4)
			value = ByteBuffer.wrap(wrpd).getInt();
		if (inc.length < 4)
			value = ByteBuffer.wrap(wrpd).getShort();
		return value;
	}

	public ArrayList<Frame> getFrameList() {
		return this.frameList;
	}
}
