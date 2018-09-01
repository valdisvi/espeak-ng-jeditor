package org.espeakng.jeditor.jni;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class ESpeakServiceTest {
	static Logger log = Logger.getLogger(ESpeakServiceTest.class.getName());

	@Test
	public void testNativeGetEspeakNgVersion() {
		log.info(ESpeakService.nativeGetEspeakNgVersion());
	}

	@Test
	public void testNativeTextToPhonemes() {
		// Need to check, that several sentences are spelled
		String input = "Hello world. Hello again.";
		String output = ESpeakService.textToPhonemes(input, "en");
		Assert.assertEquals("Wrong output", "h@l'oU w'3:ld\nh@l'oU a#g'En\n", output);
		System.out.println("NativeTextToPhonemes:" + input + " > " + output);
	}

	@Test
	public void testGetSpectSeq() {
		SpectSeq s = new SpectSeq();
		ESpeakService.nativeGetSpectSeq(s, "../espeak-ng/phsource/b/b");
		System.out.println(s.name + ":" + s.amplitude + s.numframes);
	}

	@Test
	public void testSpeak() {
		ESpeakService.nativeSpeak("en", "Hello from C world!");
	}
}
