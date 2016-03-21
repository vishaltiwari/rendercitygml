package daoTest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.postgis.PGgeometry;

import dao.GroundPolygonEnvelopeGeomdao;

public class GroundPolygonEnvelopeGeomdaoTest {

	@Test
	public void test() {
		List<PGgeometry> list = GroundPolygonEnvelopeGeomdao.getGroundPolygonGeometry();
	}

}
