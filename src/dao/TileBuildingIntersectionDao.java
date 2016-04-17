package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.postgis.PGgeometry;

import helper.Properties;

public class TileBuildingIntersectionDao {
	public static Integer getIntesectedTiles(PGgeometry geom){
		String s = geom.getGeometry().toString();
		Pattern p = Pattern.compile("SRID=.*;(.*)");
		Matcher m = p.matcher(s);
		String polygon="";
		if(m.find()){
			polygon = m.group(1);
		}
		Connection connection = Properties.connection;
		
		String sql = "SELECT tile_id, " + 
					" ST_Area(ST_Intersection( ST_GeomFromText('"+ polygon + "' ,  "+ Properties.SRID + ") , tile_geom))/ST_Area( ST_GeomFromText('"+ polygon +"' , "+Properties.SRID+" ) ) as ratio " +
					" FROM tile_geometry " +
					" WHERE ST_IsEmpty(ST_Intersection(tile_geom , ST_GeomFromText('"+ polygon +"' , "+ Properties.SRID +" ) )) = FALSE" ;		
		Integer maxTile = Integer.MIN_VALUE;
		if(connection != null){
			try {
				PreparedStatement prpStmt = connection.prepareStatement(sql);
				ResultSet rs = prpStmt.executeQuery();
				Double maxRation = Double.MIN_VALUE;
				while(rs.next()){
					Integer id = rs.getInt("tile_id");
					Double areaRatio = rs.getDouble("ratio");
					if(Double.compare(areaRatio, maxRation) > 0){
						maxRation = areaRatio;
						maxTile = id;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return maxTile;
		
	}
}
