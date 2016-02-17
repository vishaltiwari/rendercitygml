package daoTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.postgis.LinearRing;
import org.postgis.PGgeometry;
import org.postgis.Point;
import org.postgis.Polygon;

import dao.InsertTileGeometryTo3DcityDBdao;

public class InsertTo3DCityDBTest {

	
	@Test
	public void test() {
		int tileID = 1;
		Polygon geo = new Polygon(
                new LinearRing[] {
                    new LinearRing(
                        new Point[] {
                            new Point(-1.0d, -1.0d,  0.5d),
                            new Point( 1.0d, -1.0d,  0.0d),
                            new Point( 1.0d,  1.0d, -0.5d),
                            new Point(-1.0d,  1.0d,  0.0d),
                            new Point(-1.0d, -1.0d,  0.5d)
                        }
                    )
                }
            );
		PGgeometry geometry = new PGgeometry(geo);
		InsertTileGeometryTo3DcityDBdao.saveTileToDB(tileID, geometry);
	}

}
