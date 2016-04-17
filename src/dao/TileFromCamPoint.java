package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import helper.Properties;

public class TileFromCamPoint {
	
	private final static String WGS84 = "94326";
	
	public static int getTileIDFrompoint(Position position){
		String lat = position.getLatitude().toString();
		String lon = position.getLongitude().toString();
		String geomString = lon.substring(0, lon.length()-1) + " " + lat.substring(0, lat.length()-1) + " " + position.elevation ;
		Connection connection = Properties.connection;
		if (connection != null) {
			try {
				String sql = "SELECT tile_id, ST_ASTEXT(tile_geom) "+
								" FROM tile_geometry " + 
								" WHERE ST_Intersects(tile_geom , ST_Transform(ST_GeomFromText('POINT( " +geomString+ " )' , 94326 ) , "+Properties.SRID +" ))";
/*				String sql = "SELECT tile_id, ST_ASTEXT(tile_geom) "+
						" FROM tile_geometry " + 
						" WHERE ST_Intersects(tile_geom , ST_Transform(ST_GeomFromText('POINT( "+geomString+ ")' ,"+ WGS84 +" )"+ ", "+ Properties.SRID +" ));";*/
				PreparedStatement prepStmt = connection.prepareStatement(sql);
				ResultSet rs = prepStmt.executeQuery();
				int[] tileIDs = new int[4];
				int count = 0;
				while(rs.next()){
					tileIDs[count++] = rs.getInt("tile_id");
				}
				if(count>1){
					System.out.println("The point is on the edge of a grid square");
				}
				return tileIDs[0];
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
		
	}
}
