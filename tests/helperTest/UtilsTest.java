package helperTest;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.citygml4j.model.gml.geometry.primitives.DirectPosition;
import org.citygml4j.model.gml.geometry.primitives.Envelope;
import org.citygml4j.xml.io.reader.CityGMLReadException;
import org.junit.Test;

public class UtilsTest {

	@Test
	public void test() {
		String filename = "/home/vishal/NWW/sampleData/LOD2_1_F0.gml";
		List<Double> expectedLowerCoords = Arrays.asList(0.0, 0.0, 0.0);
		List<Double> expectedUpperCoords = Arrays.asList(4000.0, 4000.0, 25.0);
		try {
			Envelope envelope = helper.Utils.getEnvelope(filename);
			DirectPosition lowerPosition = envelope.getLowerCorner();
			List<Double> lowerCoords = lowerPosition.getValue();
			List<Double> upperCoords = envelope.getUpperCorner().getValue();
			assertArrayEquals(expectedLowerCoords.toArray(new Double[0]), lowerCoords.toArray(new Double[0]));
			assertArrayEquals(expectedUpperCoords.toArray(new Double[0]), upperCoords.toArray(new Double[0]));
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (CityGMLReadException e) {
			e.printStackTrace();
		}
	}

}
