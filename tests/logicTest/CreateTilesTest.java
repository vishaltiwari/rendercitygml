package logicTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.citygml4j.xml.io.reader.CityGMLReadException;
import org.junit.Ignore;
import org.junit.Test;

import logic.TilesManager;

public class CreateTilesTest {

	@Test
	public void test() {
		TilesManager manager;
		try {
			manager = new TilesManager();
			manager.generateTiles();
		} catch (JAXBException | CityGMLReadException e1) {
			e1.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void testD8Neighbors(){
		TilesManager manager;
		try {
			
			manager = new TilesManager();
			List<Integer> list = manager.getTileList(1,1);
			assertArrayEquals((new ArrayList<Integer>(Arrays.asList(6, 5, 7, 22, 23, 21))).toArray(new Integer[1]), list.toArray(new Integer[1]));
		} catch (JAXBException | CityGMLReadException e) {
			e.printStackTrace();
		}
	}
}