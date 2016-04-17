package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import helper.Properties;

public class CleanUpDao {
	
	public static int cleanDataBase(){
		Connection connection = Properties.connection;
		String truncateSql = "﻿TRUNCATE tile_geometry , tile_building_mapping , thematic_surface , surface_geometry , building , cityobject RESTART IDENTITY CASCADE";
		if (connection != null) {			
			try {
				Statement statement  = connection.createStatement();
				int result = statement.executeUpdate(truncateSql);
				connection.commit();
				return result;
			}
			catch(Exception e){
				e.printStackTrace();
				return 0;
			}
		}
		return 1;
	}
}
