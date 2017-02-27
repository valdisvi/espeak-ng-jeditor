package org.espeakng.jeditor.data;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.util.ArrayList;

public class PhonemeSave {
	//TODO implement calling of this method on File->Save and File->Save as buttons
	//TODO implement saving of SPECSPC2 phonemes (currently should work for SPECTSEQ, SPETSEK and SPECTSQ2)
	public static void saveToDirectory(Phoneme phoneme, File file) {
		byte[] temp;
		try {
			
			FileOutputStream fos = new FileOutputStream(file);
			DataOutputStream dos = new DataOutputStream(fos);
			
			temp = phoneme.type.getBytes();
			dos.write(temp);
			temp = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(phoneme.name_length).array();
			dos.write(temp);
			
			temp = phoneme.fileName.getBytes();
			dos.write(temp);

			temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) phoneme.n).array();
			dos.write(temp);

			temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) phoneme.amplitude)
					.array();
			dos.write(temp);

			temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) phoneme.max_y)
					.array();
			dos.write(temp);

			dos.write(0);
			dos.write(0);

			ArrayList<Frame> frameList = phoneme.getFrameList();

			for (int i = 0; i < phoneme.n; i++) {
				Frame currentFrame = frameList.get(i);
				if ((phoneme.file_format == 0) || (phoneme.file_format == 1)
						|| (phoneme.file_format == 2)) {
					writeFrame(currentFrame, dos, phoneme.file_format);
				}
			}

			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeFrame(Frame frame, DataOutputStream dos,
			int file_format) {

		byte[] temp;
		try {

			temp = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(frame.time).array();
			dos.write(temp);
			dos.write(0);
			dos.write(0);

			temp = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(frame.pitch).array();
			dos.write(temp);
			dos.write(0);
			dos.write(0);

			temp = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(frame.length).array();
				dos.write(temp);
			dos.write(0);
			dos.write(0);
			dos.flush();

			temp = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(frame.dx).array();
				dos.write(temp);
			dos.write(0);
			dos.write(0);

			temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) frame.nx).array();
			dos.write(temp);

			temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) frame.primarkers)
					.array();
			dos.write(temp);

			temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) frame.amp_adjust)
					.array();
			dos.write(temp);

			if (file_format == 2) {
				dos.write(0);
				dos.write(0);
			}

			for (int i = 0; i < 9; i++) {

				temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
						.putShort((short) frame.formants[i].freq).array();
				dos.write(temp);

				temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
						.putShort((short) frame.formants[i].bandw).array();
				dos.write(temp);

				temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
						.putShort((short) frame.peaks[i].pkfreq).array();
				dos.write(temp);

				temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
						.putShort((short) frame.peaks[i].pkheight).array();
				dos.write(temp);

				temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
						.putShort((short) frame.peaks[i].pkright).array();
				dos.write(temp);


				temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
						.putShort((short) frame.peaks[i].pkwidth).array();
				dos.write(temp);

				if (file_format == 2) {
					temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
							.putShort((short) frame.peaks[i].klt_bw).array();
					dos.write(temp);
					
					temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
							.putShort((short) frame.peaks[i].klt_ap).array();
					dos.write(temp);

					temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
							.putShort((short) frame.peaks[i].klt_bp).array();
					dos.write(temp);
				}
			}

			if (file_format > 0) {
				for (int i = 0; i < 14; i++) {
					temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
							.putShort((short) frame.klatt_param[i]).array();
					dos.write(temp);
				}
			}

			for (int i = 0; i < frame.nx; i++) {
				temp = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
						.putShort((short) frame.spect_data[i]).array();
				dos.write(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveToCustomDirectory(Phoneme phoneme, Path path) {

	}
}
