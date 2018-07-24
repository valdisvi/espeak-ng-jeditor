package org.espeakng.jeditor.jni;

import org.apache.log4j.Logger;
import org.junit.Test;

public class ESpeakServiceTest {
	static Logger log = Logger.getLogger(ESpeakServiceTest.class.getName());

	@Test
	public void testNativeGetEspeakNgVersion() {
		log.info(ESpeakService.nativeGetEspeakNgVersion());
	}

	@Test
	public void testNativeTextToPhonemes() {
		//System.out.println(ESpeakService.nativeTextToPhonemes("Hello").toString());
	}

	@Test
	public void testGetSpectSeq() {
		SpectSeq s = new SpectSeq();
		ESpeakService.nativeGetSpectSeq(s, "../espeak-ng/phsource/b/b");
		System.out.println(s.name + ":" + s.amplitude+s.numframes);
	}

}
