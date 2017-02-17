package dataStructure;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Phoneme {
	// Type-name of file (SPECTSEQ,SPECTSEK,SPECTSQ2)
	public String type;
	public int file_format;
	public int name_length;
	public int n;
	public int amplitude;
	public int max_y;

	public String fileName;
	public ArrayList<Frame> frameList;

	private Graph graph;

	public ArrayList<Frame> getFrameList() {
		return frameList;
	}

	// TODO Add support for SPECSPC2 type phonemes
	public Phoneme(File file) {
		frameList = new ArrayList<Frame>();
		byte[] data = new byte[(int) file.length()];
		try {
			FileInputStream inStream = new FileInputStream(file);
			inStream.read(data);

			ByteArrayInputStream inRead = new ByteArrayInputStream(data);
			byte[] buffer = new byte[8];
			inRead.read(buffer, 0, 8);

			type = new String(buffer);
			if (type.equals("SPECTSPC2")) {
				// TODO implement support of old SPECTSPC2 files loading
			} else if (type.equals("SPECTSEQ")) {
				file_format = 0;
			} else if (type.equals("SPECTSEK")) {
				file_format = 1;
			} else if (type.equals("SPECTSQ2")) {
				file_format = 2;
			} else {
				// TODO case when format is smth else
			}

			// Reading 4 bytes to get the byte-length of file-name
			buffer = new byte[4];
			inRead.read(buffer, 0, 4);
			// Wrapping bytes e.g. by default (01 00 00 00) after wrapping (00 00 00 01)
			name_length = byteWrapper(buffer);

			// Reading %name_length% bytes for file-name
			buffer = new byte[name_length];
			inRead.read(buffer, 0, name_length);
			fileName = new String(buffer);

			// Reading 2 bytes n
			buffer = new byte[2];
			inRead.read(buffer, 0, 2);
			n = byteWrapper(buffer);

			// Reading 2 bytes amplitude
			inRead.read(buffer, 0, 2);
			amplitude = byteWrapper(buffer);
			// Reading 2 bytes max_y
			inRead.read(buffer, 0, 2);
			max_y = byteWrapper(buffer);

			inRead.read(buffer, 0, 2);

			for (int i = 0; i < n; i++) {
				// SpectFrame *frame = new SpectFrame;
				Frame newFrame = new Frame();
				// Call SPECTSEQ type load
				if ((file_format == 0) || (file_format == 1) || (file_format == 2)) {
					newFrame.frameLoader(inRead, file_format);
				}
				// TODO implement case when format is smth else
				frameList.add(newFrame);
			}
			inStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		graph = new Graph(file.getName(), frameList);
	}

	public Graph getGraph() {
		return graph;
	}

	public void loadFirstFrame() {
		graph.loadFirstFrame();
	}

	//Custom byte wrapping method e.g. ByteOrder.LITTLE_ENDIAN
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
}
