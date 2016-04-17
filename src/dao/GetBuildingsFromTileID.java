package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgis.LinearRing;
import org.postgis.PGgeometry;
import org.postgis.Polygon;

import helper.Properties;
import model.Building;

public class GetBuildingsFromTileID {
	
	public static List<Building> getBuildingsFromtileID(int tileID , boolean positionFlag){
		
		List<Building> buildingList = new ArrayList<>();
		Connection connection = Properties.connection;
		String sql = "";
		if(positionFlag == true){
			sql = "SELECT  ST_TRANSFORM(surface_geometry.geometry, 94326) , "+
					" building.id, " + 
					" thematic_surface.objectclass_id, " + 
					" tile_id " +
					" FROM building LEFT OUTER JOIN thematic_surface ON "+  
					" building.id = thematic_surface.building_id " + 
					" LEFT OUTER JOIN surface_geometry ON "+
					" surface_geometry.cityobject_id = thematic_surface.id and surface_geometry.geometry!='' "+
					" LEFT OUTER JOIN tile_building_mapping on " + 
					" building.id = tile_building_mapping.building_id " +

    				" WHERE tile_id = " + tileID +
    				" ORDER BY building.id";

			/*sql = "SELECT  ST_TRANSFORM(ST_Transform(surface_geometry.geometry , " + Properties.SRID + " ) , 94326) , "+
					" building.id, " + 
					" thematic_surface.objectclass_id, " + 
					" tile_id " +
					" FROM building LEFT OUTER JOIN thematic_surface ON "+  
					" building.id = thematic_surface.building_id " + 
					" LEFT OUTER JOIN surface_geometry ON "+
					" surface_geometry.cityobject_id = thematic_surface.id and surface_geometry.geometry!='' "+
					" LEFT OUTER JOIN tile_building_mapping on " + 
					" building.id = tile_building_mapping.building_id " +

	        		" WHERE tile_id = " + tileID +
	        		" ORDER BY building.id";*/
		}
		else{
			sql = "SELECT  ST_TRANSFORM(surface_geometry.geometry , 94326) , "+
					" building.id, " + 
					" thematic_surface.objectclass_id, " + 
					" tile_id " +
					" FROM building LEFT OUTER JOIN thematic_surface ON "+  
					" building.id = thematic_surface.building_id " + 
					" LEFT OUTER JOIN surface_geometry ON "+
					" surface_geometry.cityobject_id = thematic_surface.id and surface_geometry.geometry!='' "+
					" LEFT OUTER JOIN tile_building_mapping on " + 
					" building.id = tile_building_mapping.building_id ";
			/*sql = "SELECT  ST_TRANSFORM(ST_Transform(surface_geometry.geometry , " + Properties.SRID + " ) , 94326) , "+
					" building.id, " + 
					" thematic_surface.objectclass_id, " + 
					" tile_id " +
					" FROM building LEFT OUTER JOIN thematic_surface ON "+  
					" building.id = thematic_surface.building_id " + 
					" LEFT OUTER JOIN surface_geometry ON "+
					" surface_geometry.cityobject_id = thematic_surface.id and surface_geometry.geometry!='' "+
					" LEFT OUTER JOIN tile_building_mapping on " + 
					" building.id = tile_building_mapping.building_id ";*/
		}
		if (connection != null) {
						PreparedStatement prepStmt = null;
			try {
				prepStmt = connection.prepareStatement(sql);
				ResultSet rs = prepStmt.executeQuery();
				List<PGgeometry> roofSurface = null;
				List<PGgeometry> wallSurface = null;
				List<PGgeometry> groundSurface = null;
				
				//There will never be a -1 id for a building.
				int prevId = -1;
				Building build = null;
				while(rs.next()){
					int currId = rs.getInt("id");
					if(currId != prevId){
						//save the previous building:
						if(prevId != -1){
							
							build.setGroundSurface(groundSurface);
							build.setRoofSurface(roofSurface);
							build.setWallSurface(wallSurface);
							
							buildingList.add(build);
						}
						//reset the values:
						build = new Building();
						roofSurface = new ArrayList<>();
						wallSurface = new ArrayList<>();
						groundSurface = new ArrayList<>();
						
						prevId = currId;
					}
					PGgeometry surfaceGeom = (PGgeometry)rs.getObject("st_transform");
					
					//Set the Z coordinate to zero.
					/*if(surfaceGeom != null){
						//Set the Z value of each polygons LinearRing to be zero.
						Polygon p1 = (Polygon) surfaceGeom.getGeometry();
						for (int i = 0; i < p1.numRings(); i++) {
							LinearRing ring = p1.getRing(i);

							for (int j = 0; j < ring.numPoints(); j++) {
								ring.getPoint(j).setZ(0);
							}
						}
					}*/
					Integer classId = rs.getInt("objectclass_id");
					build.setId(currId);
					//Get wallSurface:
					if(classId == 34){
						wallSurface.add(surfaceGeom);
					}
					else if(classId == 33){
						roofSurface.add(surfaceGeom);
					}
					else if(classId == 35){
						groundSurface.add(surfaceGeom);
					}
					
					//polygonList.add((PGgeometry) rs.getObject("envelope"));
				}
				if(prevId != -1){
					build.setGroundSurface(groundSurface);
					build.setRoofSurface(roofSurface);
					build.setWallSurface(wallSurface);
					
					buildingList.add(build);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return buildingList;
	}
}
