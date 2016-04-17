package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgis.PGgeometry;

import helper.Properties;

public class PointTransformDao {
	public static List<Double> getTranformation(List<Double> coord){
		String geomString = coord.get(0) + " " + coord.get(1);
		//String sqlString = "SELECT ST_TRANSFORM(ST_TRANSFORM(ST_GeomFromText('POINT("+ geomString +")' , 93068 ) ,  "+ Properties.SRID+ " ), 94326) as coords";
		String sqlString = "SELECT ST_TRANSFORM(ST_GeomFromText('POINT("+ geomString +")' ,  "+Properties.SRID+"  ) ,  94326 ) as coords";
		Connection connection = Properties.connection;
		List<Double> transformedPoint = new ArrayList<>();
		if (connection != null) {
			PreparedStatement prepStmt;
			try {
				prepStmt = connection.prepareStatement(sqlString);
				ResultSet rs = prepStmt.executeQuery();
				if(rs.next()){
					PGgeometry Geom = (PGgeometry)rs.getObject("coords");
					transformedPoint.add(Geom.getGeometry().getPoint(0).x);
					transformedPoint.add(Geom.getGeometry().getPoint(0).y);
					transformedPoint.add(coord.get(2));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return transformedPoint;
	}
}
