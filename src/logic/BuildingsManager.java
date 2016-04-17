package logic;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.postgis.LinearRing;
import org.postgis.PGgeometry;
import org.postgis.Point;
import org.postgis.Polygon;

import dao.GetBuildingEnvelope;
import dao.InsertTileBuildingMappingdao;
import dao.InsertTileGeometryTo3DcityDBdao;
import dao.TileBuildingIntersectionDao;
import helper.Properties;

public class BuildingsManager {

	public static void buildingMappingToTile(Map<Integer, PGgeometry> buildingEnvelope) {
		Set<Entry<Integer, PGgeometry>> mapping = buildingEnvelope.entrySet();

		String[] sqlStrings = new String[Properties.batchSize];
		int added = 0;
		for (Entry<Integer, PGgeometry> entry : mapping) {
			Integer buildingId = entry.getKey();
			PGgeometry env = entry.getValue();

			// Set the Z value of each polygons LinearRing to be zero.
			Polygon p1 = (Polygon) env.getGeometry();
			for (int i = 0; i < p1.numRings(); i++) {
				LinearRing ring = p1.getRing(i);

				for (int j = 0; j < ring.numPoints(); j++) {
					ring.getPoint(j).setZ(0);
				}
			}

			Integer tileId = TileBuildingIntersectionDao.getIntesectedTiles(env);
			if(tileId < 0) continue;
			System.out.println(tileId);
			// Save tileId, and BuildingId
			sqlStrings[added++] = InsertTileBuildingMappingdao.createInsertStmt(tileId, buildingId);
			if (added == Properties.batchSize) {
				InsertTileBuildingMappingdao.mapBuildingsToTiles(sqlStrings);
				added = 0;
			}
		}
		// because you have to insert the last batch
		if (added != 0) {
			String[] lastSqls = new String[added];
			for (int i=0 ; i<added ; ++i) {
				lastSqls[i] = sqlStrings[i];
			}
			InsertTileBuildingMappingdao.mapBuildingsToTiles(lastSqls);
		}
	}

	public static void main(String args[]) {
		Map<Integer, PGgeometry> mapping = GetBuildingEnvelope.getBuildingEvnveoples();
		buildingMappingToTile(mapping);
	}
}
