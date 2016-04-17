package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.postgis.PGgeometry;

import helper.Properties;

public class InsertTileBuildingMappingdao {

	@Deprecated
	public static void mapBuildingsToTiles_bck(Integer tileId, Integer buildingId) {
		Connection connection = Properties.connection;
		if (connection != null) {
			try {
				/*
				 * PreparedStatement prepStmt = connection.prepareStatement(
				 * "INSERT INTO tile_building_mapping(tile_id, building_id)  "+
				 * "(SELECT tile_id , table2.buildingID "+
				 * "FROM tile_geometry , "+
				 * 
				 * "(SELECT building.id as buildingID, thematic_surface.objectclass_id, surface_geometry.geometry as groundPolygon "
				 * + "FROM building LEFT OUTER JOIN thematic_surface ON "+
				 * "building.id = thematic_surface.building_id "+
				 * "LEFT OUTER JOIN surface_geometry ON "+
				 * "surface_geometry.cityobject_id = thematic_surface.id and surface_geometry.geometry!='' "
				 * + "WHERE thematic_surface.objectclass_id = 35) as table2 "+
				 * 
				 * "WHERE ST_IsEmpty(ST_Intersection(table2.groundPolygon , tile_geom)) = FALSE "
				 * +
				 * "and ST_Area(ST_Intersection(table2.groundPolygon , tile_geom))/ST_Area(table2.groundPolygon) >= 0.5 "
				 * + "ORDER BY tile_id); ");
				 */

				String insertSql = "INSERT INTO tile_building_mapping( " + " tile_id, building_id) " + " VALUES ("
						+ tileId + ", " + buildingId + ")";

				PreparedStatement prepStmt = connection.prepareStatement(insertSql);

				int results = prepStmt.executeUpdate();
				if (results > 0) {
					System.out.println("Successfully INSERTEd!!");
				} else {
					System.out.println("FAILED INSERT");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	public static String createInsertStmt(Integer tileId, Integer buildingId) {
		return tileId + "," + buildingId;
		/*
		 * return "INSERT INTO tile_building_mapping( "+
		 * " tile_id, building_id) " + " VALUES ("+ tileId +", "+ buildingId+")"
		 * ;
		 */
	}

	public static void mapBuildingsToTiles(String[] sqlStrings) {
		Connection connection = Properties.connection;
		if (connection != null) {
			try {
				/*
				 * PreparedStatement prepStmt = connection.prepareStatement(
				 * "INSERT INTO tile_building_mapping(tile_id, building_id)  "+
				 * "(SELECT tile_id , table2.buildingID "+
				 * "FROM tile_geometry , "+
				 * 
				 * "(SELECT building.id as buildingID, thematic_surface.objectclass_id, surface_geometry.geometry as groundPolygon "
				 * + "FROM building LEFT OUTER JOIN thematic_surface ON "+
				 * "building.id = thematic_surface.building_id "+
				 * "LEFT OUTER JOIN surface_geometry ON "+
				 * "surface_geometry.cityobject_id = thematic_surface.id and surface_geometry.geometry!='' "
				 * + "WHERE thematic_surface.objectclass_id = 35) as table2 "+
				 * 
				 * "WHERE ST_IsEmpty(ST_Intersection(table2.groundPolygon , tile_geom)) = FALSE "
				 * +
				 * "and ST_Area(ST_Intersection(table2.groundPolygon , tile_geom))/ST_Area(table2.groundPolygon) >= 0.5 "
				 * + "ORDER BY tile_id); ");
				 */

				String insertSql = "INSERT INTO tile_building_mapping( " + " tile_id, building_id) "
						+ " VALUES ( ? , ?)";
				connection.setAutoCommit(false);
				PreparedStatement prepStmt = connection.prepareStatement(insertSql);
				Pattern regex = Pattern.compile("(\\d+),(\\d+)");

				for (String sql : sqlStrings) {
					if (sql == null)
						continue;

					Matcher m = regex.matcher(sql);
					if (m.find()) {
						if("-2147483648".equals(m.group(1))){
							System.out.println(m.group(1));
							System.out.println(m.group(2));
							continue;
						}
						try{
							prepStmt.setInt(1, Integer.parseInt(m.group(1)));
							prepStmt.setInt(2, Integer.parseInt(m.group(2)));
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
					prepStmt.addBatch();
				}

				prepStmt.executeBatch();
				connection.commit();
				connection.setAutoCommit(true);
				System.out.println("Successfully Inserted the tiles in the tile_geometry table!");
				// int results = prepStmt.executeUpdate();
				/*
				 * if(results>0){ System.out.println("Successfully INSERTEd!!");
				 * } else{ System.out.println("FAILED INSERT"); }
				 */
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}
