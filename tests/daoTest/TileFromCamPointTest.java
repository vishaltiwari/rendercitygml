package daoTest;

import static org.junit.Assert.*;

import org.junit.Test;

import gov.nasa.worldwind.geom.Position;

public class TileFromCamPointTest {

	@Test
	public void test() {
		Position position = Position.fromDegrees(13.0423637991259 , 52.3281893855082 , 41.6721502970904);
		int tileID = dao.TileFromCamPoint.getTileIDFrompoint(position);
		assertEquals(1, tileID);
	}

}
