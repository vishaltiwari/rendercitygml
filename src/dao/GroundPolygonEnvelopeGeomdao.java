package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgis.PGgeometry;

import helper.Properties;

public class GroundPolygonEnvelopeGeomdao {

	public static List<PGgeometry> getGroundPolygonGeometry() {
		List<PGgeometry> polygonList = new ArrayList<>();
		Connection connection = Properties.connection;
		if (connection != null) {
			String sql = "SELECT envelope FROM cityobject WHERE objectclass_id = 26";
			PreparedStatement prepStmt;
			try {
				prepStmt = connection.prepareStatement(sql);
				ResultSet rs = prepStmt.executeQuery();
				while(rs.next()){
					polygonList.add((PGgeometry) rs.getObject("envelope"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return polygonList;
	}
}
