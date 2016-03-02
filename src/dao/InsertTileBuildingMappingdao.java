package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.postgis.PGgeometry;

import helper.Properties;

public class InsertTileBuildingMappingdao {
	
	public static void mapBuildingsToTiles(){
		Connection connection = Properties.connection;
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			
			try {
				PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO tile_building_mapping(tile_id, building_id)  "+
						"(SELECT tile_id , table2.buildingID "+
						"FROM tile_geometry , "+

						"(SELECT building.id as buildingID, thematic_surface.objectclass_id, surface_geometry.geometry as groundPolygon "+
					        "FROM building LEFT OUTER JOIN thematic_surface ON "+
					        "building.id = thematic_surface.building_id "+
					        "LEFT OUTER JOIN surface_geometry ON "+
					        "surface_geometry.cityobject_id = thematic_surface.id and surface_geometry.geometry!='' "+
					        "WHERE thematic_surface.objectclass_id = 35) as table2 "+

					        "WHERE ST_IsEmpty(ST_Intersection(table2.groundPolygon , tile_geom)) = FALSE "+
					        "and ST_Area(ST_Intersection(table2.groundPolygon , tile_geom))/ST_Area(table2.groundPolygon) >= 0.5 "+
					        "ORDER BY tile_id); ");
				
				int results = prepStmt.executeUpdate();
				if(results>0){
					System.out.println("Successfully INSERTEd!!");
				}
				else{
					System.out.println("FAILED INSERT");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}
