package logic;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.citygml4j.model.gml.geometry.primitives.DirectPosition;
import org.citygml4j.model.gml.geometry.primitives.Envelope;
import org.citygml4j.xml.io.reader.CityGMLReadException;
import org.postgis.Geometry;
import org.postgis.LinearRing;
import org.postgis.PGgeometry;
import org.postgis.Point;
import org.postgis.Polygon;

import com.sun.org.apache.xml.internal.serializer.utils.Utils;

import dao.InsertTileGeometryTo3DcityDBdao;
import helper.Properties;

public class CreateTilesManager {

	public void generateTiles() throws JAXBException, CityGMLReadException{
		Envelope envelope = helper.Utils.getEnvelope(Properties.filename);
		createTilesFromEnvelope(envelope);
	}
	
	public void createTilesFromEnvelope(Envelope envelope) {
		DirectPosition lowerPosition = envelope.getLowerCorner();
		List<Double> lowerCoords = lowerPosition.getValue();
		List<Double> upperCoords = envelope.getUpperCorner().getValue();
		Double x0 = lowerCoords.get(0);
		Double y0 = lowerCoords.get(1);

		Double x1 = upperCoords.get(0);
		Double y1 = upperCoords.get(1);

		int tileCount = 1;
		int tileSize = Properties.tileSize;
		for (Double y = y0; y < y1; y += tileSize) {
			for (Double x = x0; x < x1; x += tileSize) {
				Polygon geo = new Polygon(new LinearRing[] { new LinearRing(new Point[] { new Point(x, y, 0.0d),
						new Point(x + tileSize, y, 0.0d), new Point(x + tileSize, y + tileSize, 0.0d),
						new Point(x, y + tileSize, 0.0d), new Point(x, y, 0.0d) }) });
				PGgeometry geometry = new PGgeometry(geo);
				InsertTileGeometryTo3DcityDBdao.saveTileToDB(tileCount++,geometry);
			}
		}
	}
}
