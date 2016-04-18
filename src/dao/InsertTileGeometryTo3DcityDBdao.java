package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.postgis.PGgeometry;

import helper.Properties;

public class InsertTileGeometryTo3DcityDBdao {
	
	@Deprecated
	public static void saveTileToDB_bck(int tileID, PGgeometry geom){
		Connection connection = Properties.connection;
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			String s = geom.getGeometry().toString();
			Pattern p = Pattern.compile("SRID=.*;(.*)");
			Matcher m = p.matcher(s);
			String polygon="";
			if(m.find()){
				polygon = m.group(1);
			}
			try {
				PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO tile_geometry(tile_id, tile_geom) VALUES ("+ tileID +", ST_GeomFromText('"+ polygon +"' , 93068))");
				//prepStmt.setInt(1, tileID);
				//prepStmt.setObject(2, geom);
				
				int results = prepStmt.executeUpdate();
				//prepStmt.bat
				if(results>0){
					System.out.println("Successfully INSERTEd!");
				}
				else{
					System.out.println("FAILED INSERT");
				}
//				Statement stmt = connection.createStatement();
//				ResultSet results = stmt.executeQuery("SELECT * from building");
//				while(results.next()){
//					Integer id = results.getInt("id");
//					System.out.println(id);
//				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	public static String createInsertStmt(int tileID, PGgeometry geom){
		String s = geom.getGeometry().toString();
		Pattern p = Pattern.compile("SRID=.*;(.*)");
		Matcher m = p.matcher(s);
		String polygon="";
		if(m.find()){
			polygon = m.group(1);
		}
		
		return tileID+","+polygon;
		//return "INSERT INTO tile_geometry(tile_id, tile_geom) VALUES ("+ tileID +", ST_GeomFromText('"+ polygon +"' , 93068))";
		
	}
	public static void saveTileToDB(String[] sqlStrings){
		Connection connection = Properties.connection;
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			try {
				//String sqlString = "INSERT INTO tile_geometry(tile_id, tile_geom) VALUES ( ? , ST_GeomFromText( ? , 93068))";
				String sqlString = "INSERT INTO tile_geometry(tile_id, tile_geom) VALUES ( ? , ST_GeomFromText( ? , "+ Properties.SRID + "))";
				connection.setAutoCommit(false);
				PreparedStatement prepStmt = connection.prepareStatement(sqlString);
				Pattern regex = Pattern.compile("(\\d+),(.*)");
				for(String sql : sqlStrings){
					if(sql == null) continue;
					Matcher m = regex.matcher(sql);
					if(m.find()){
						prepStmt.setInt(1, Integer.parseInt(m.group(1)));
						prepStmt.setString(2, m.group(2));
					}
					prepStmt.addBatch();
				}
				
				prepStmt.executeBatch();
				connection.commit();
				
				connection.setAutoCommit(true);
				System.out.println("Successfully Inserted the tiles in the tile_geometry table!!");
				
				//PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO tile_geometry(tile_id, tile_geom) VALUES ("+ tileID +", ST_GeomFromText('"+ polygon +"' , 93068))");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}
