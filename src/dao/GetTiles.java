package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.postgis.PGgeometry;

import helper.Properties;
import model.Building;
import model.Tile;

public class GetTiles {

	public static List<Tile> getTiles(){
		List<Tile> tileList = new ArrayList<>();
		Connection connection = Properties.connection;
		String sql = "SELECT tile_id, ST_Transform(tile_geom , 94326) FROM tile_geometry";
		if (connection != null) {
						PreparedStatement prepStmt = null;
						
			try {
				prepStmt = connection.prepareStatement(sql);
				ResultSet rs = prepStmt.executeQuery();
				while(rs.next()){
					int id = rs.getInt("tile_id");
					PGgeometry Geom = (PGgeometry)rs.getObject("st_transform");
					Tile tile = new Tile(id,Geom);
					tileList.add(tile);
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		return tileList;
	}
}
