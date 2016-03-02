package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgis.PGgeometry;

import helper.Properties;

public class InsertTileGeometryTo3DcityDBdao {
	
	public static void saveTileToDB(int tileID, PGgeometry geom){
		Connection connection = Properties.connection;
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			
			try {
				PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO tile_geometry(tile_id, tile_geom) VALUES (?, ?)");
				prepStmt.setInt(1, tileID);
				prepStmt.setObject(2, geom);
				
				int results = prepStmt.executeUpdate();
				if(results>0){
					System.out.println("Successfully INSERTEd!!");
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
}
