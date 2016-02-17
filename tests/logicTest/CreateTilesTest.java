package logicTest;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;

import org.citygml4j.xml.io.reader.CityGMLReadException;
import org.junit.Test;

import logic.CreateTilesManager;

public class CreateTilesTest {

	@Test
	public void test() {
		CreateTilesManager manager = new CreateTilesManager();
		try {
			manager.generateTiles();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CityGMLReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
