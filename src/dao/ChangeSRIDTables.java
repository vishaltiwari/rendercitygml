package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import helper.Properties;

public class ChangeSRIDTables {
	public static void changeSRID(){
		Connection connection = Properties.connection;
		String changeSRIDCityObject = "select UpdateGeometrySRID('citydb' , 'cityobject' , 'envelope' , "+Properties.SRID+")";
		String changeSRIDSurfaceGeometry = "select UpdateGeometrySRID('citydb' , 'surface_geometry' , 'geometry' , " +Properties.SRID+" )";
		String changeSRIDTileGeometry = "select UpdateGeometrySRID('citydb' , 'tile_geometry' , 'tile_geom' , " + Properties.SRID +" )";
		if(connection != null){
			System.out.println("Changing the SRID of geom tables");
			
			try {
				PreparedStatement prepStmt = connection.prepareStatement(changeSRIDCityObject);
				ResultSet rs = prepStmt.executeQuery();
				System.out.println(rs.next());
				
				PreparedStatement prepStmt2 = connection.prepareStatement(changeSRIDSurfaceGeometry);
				ResultSet rs2 = prepStmt2.executeQuery();
				System.out.println(rs2.next());
				
				PreparedStatement prepStmt3 = connection.prepareStatement(changeSRIDTileGeometry);
				ResultSet rs3 = prepStmt3.executeQuery();
				System.out.println(rs3.next());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
