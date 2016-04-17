package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.postgis.PGgeometry;

import helper.Properties;

public class GetBuildingEnvelope {
	
	public static Map<Integer,PGgeometry> getBuildingEvnveoples(){
		Map<Integer , PGgeometry> envelopes = new HashMap<>();
		Connection connection = Properties.connection;
		String sql = "SELECT cityobject.id , envelope FROM cityobject WHERE objectclass_id = 26";
		if(connection != null){
			System.out.println("Connection is established, YAY!!!!");
			PreparedStatement prepStmt = null;
			try {
				prepStmt = connection.prepareStatement(sql);
				ResultSet rs = prepStmt.executeQuery();
				while(rs.next()){
					Integer id = rs.getInt("id");
					PGgeometry env = (PGgeometry)rs.getObject("envelope");
					envelopes.put(id, env);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return envelopes;
	}
}
