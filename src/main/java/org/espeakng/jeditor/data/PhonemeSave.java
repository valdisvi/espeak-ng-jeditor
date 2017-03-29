package org.espeakng.jeditor.data;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import org.espeakng.jeditor.jni.ESpeakService;
import org.espeakng.jeditor.jni.SpectSeq;

public class PhonemeSave {
	/*FIXME this class and it's methods are used by calling  File->Save and File->SaveAs buttons,
	but action listeners are commented out for them, because it is not complete
	known issues: 1. (at current stage of JNI implementation) file_format is always 0, so that affects how the file
	is written; 2. espeak at one point of loading file reads 10 bytes for reading double (time, pitch, lenght and dx)
	and java uses just 8 bytes for doubles; 3. also this is not confirmed but there might be problems with unsigned
	types, because in signed variables, first byte is used to determine if its positive or negative number,
	could not find a way to write unsigned variables in DataStream
	*/
	public static void saveToDirectory(Phoneme phoneme, File file) {
		byte[] temp;
		try (FileOutputStream fos = new FileOutputStream(file,false); DataOutputStream dos = new DataOutputStream(fos)){
			
			temp = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(1128616019).array();
			dos.write(temp);
			
			if(phoneme.file_format == 0){
				temp = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(1363497812).array();
			}else if(phoneme.file_format == 1){
				temp = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(1262834516).array();
			}else{
				temp = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(844190548).array();
			}
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
	
	public static void saveToCustomDirectory(Phoneme phoneme, String path) {
		saveToDirectory(phoneme, new File(path));
	}
	
	/**
	 * Main method for testing purposes.
	 */
	public static void main(String[] args){
		
		SpectSeq s = new SpectSeq();
//		ESpeakService.nativeGetSpectSeq(s, "../savedPhonems/i");
		ESpeakService.nativeGetSpectSeq(s, "../espeak-ng/phsource/vowel/0");
		
		System.out.println("namelenght " + s.name.length());
		System.out.println("SpectSeq [numframes=" + s.numframes + ", amplitude=" + 
		s.amplitude + ", spare=" + s.spare + ", name=" + s.name
				+ ", frames=" + s.frames.length + "\n, pitchenv=" + 
		s.pitchenv + ", pitch1=" + s.pitch1 + ", pitch2="
				+ s.pitch2 + "\n, duration=" + s.duration + ", grid=" + s.grid + 
				"\n, bass_reduction=" + s.bass_reduction
				+ ", max_x=" + s.max_x + ", max_y=" + s.max_y + "\n, file_format=" 
				+ s.file_format + "]");
		
	}
}
