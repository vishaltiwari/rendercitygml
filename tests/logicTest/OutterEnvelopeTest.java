package logicTest;

import static org.junit.Assert.*;

import org.citygml4j.model.gml.geometry.primitives.Envelope;
import org.junit.Test;

import logic.OutterEnvelope;

public class OutterEnvelopeTest {

	@Test
	public void test() {
		Envelope e = OutterEnvelope.getAreaEnvelope();
	}

}
