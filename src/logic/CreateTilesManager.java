package logic;

import java.util.ArrayList;
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
import dao.TileFromCamPoint;
import gov.nasa.worldwind.geom.Position;
import helper.Properties;

public class CreateTilesManager {

	//private static int srid = 93068;
	public void generateTiles() throws JAXBException, CityGMLReadException{
		Envelope envelope = helper.Utils.getEnvelope(Properties.filename);
		Properties.evelope = envelope;
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
				geo.setSrid(Integer.parseInt(Properties.SRID));
				PGgeometry geometry = new PGgeometry(geo);
				InsertTileGeometryTo3DcityDBdao.saveTileToDB(tileCount++,geometry);
			}
		}
	}
	
	public List<Integer> getRenderingTiles(Position position){
		
		int centerTile = TileFromCamPoint.getTileIDFrompoint(position);
		//get the D8 tiles for rendering:
		List<Integer> tileList = getTileList(centerTile);
		return tileList;
	}
	public List<Integer> getTileList(int tileID){
		List<Double> lower = Properties.evelope.getLowerCorner().getValue();
		List<Double> upper = Properties.evelope.getUpperCorner().getValue();
		int rows = (int) ((upper.get(0) - lower.get(0))/Properties.tileSize);
		int cols = (int) ((upper.get(1) - lower.get(1))/Properties.tileSize);
		int tileCount = rows * cols;
		
		List<Integer> tileList = new ArrayList<>();
		tileList.add(tileID);
		//D4 neighbors:
		if((tileID-1)%rows != 0){
			tileList.add(tileID-1);
		}
		if((tileID)%rows != 0){
			tileList.add(tileID+1);
		}
		if((tileID+rows) < tileCount){
			tileList.add(tileID+rows);
		}
		if(tileID-rows > 0){
			tileList.add(tileID-rows);
		}
		// Other four neighbors
		if((tileID+rows+1) < tileCount && (tileID+rows)%rows !=0){
			tileList.add(tileID + rows + 1);
		}
		if((tileID+rows-1) < tileCount && (tileID+rows-1)%rows !=0){
			tileList.add(tileID + rows - 1);
		}
		if((tileID-rows-1) > 0 && (tileID - rows -1)%rows !=0){
			tileList.add(tileID - rows - 1);
		}
		if((tileID - rows + 1) > 0 && (tileID - rows)%rows !=0){
			tileList.add(tileID - rows + 1);
		}
		return tileList;
	}
}
