package org.espeakng.jeditor.jni;

import org.junit.Test;

public class ESpeakServiceTest {

	@Test
	public void testNativeGetEspeakNgVersion() {
		System.out.println(ESpeakService.nativeGetEspeakNgVersion());
	}

	@Test
	public void testNativeTextToPhonemes() {
		System.out.println(ESpeakService.nativeTextToPhonemes("Hello"));
	}

	@Test
	public void testGetSpectSeq() {
		SpectSeq s = new SpectSeq();
		ESpeakService.nativeGetSpectSeq(s, "../espeak-ng/phsource/b/b");
		System.out.println(s.name + ":" + s.amplitude);
	}

}
