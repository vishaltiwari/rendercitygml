package logic;

import java.util.List;

import org.citygml4j.geometry.BoundingBox;
import org.citygml4j.model.gml.geometry.primitives.Envelope;
import org.postgis.LinearRing;
import org.postgis.PGgeometry;
import org.postgis.Point;
import org.postgis.Polygon;

import dao.GroundPolygonEnvelopeGeomdao;

public class OutterEnvelope {
	public static Envelope getAreaEnvelope() {
		Double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE, minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
		List<PGgeometry> list = GroundPolygonEnvelopeGeomdao.getGroundPolygonGeometry();
		for (PGgeometry poly : list) {
			Polygon p1 = (Polygon) poly.getGeometry();
			for (int i = 0; i < p1.numRings(); i++) {
				LinearRing ring = p1.getRing(i);

				for (int j = 0; j < ring.numPoints(); j++) {
					Point pt = ring.getPoint(j);
					if(Double.compare(minX, pt.x) > 0)
						minX = pt.x;
					if(Double.compare(maxX, pt.x) < 0)
						maxX = pt.x;
					if(Double.compare(minY, pt.y) > 0)
						minY = pt.y;
					if(Double.compare(maxY, pt.y) < 0)
						maxY = pt.y;
				}
			}
		}
		BoundingBox box = new BoundingBox();
		box.setLowerCorner(minX, minY, 0.0);
		box.setUpperCorner(maxX, maxY, 0.0);
		
		return  new Envelope(box);
	}
}
