package helper;

import java.sql.Connection;

import dao.DBSetup;

public class Properties {
	public static final int tileSize=250;
	public static final Connection connection = DBSetup.setup();
	public static final String filename = "/home/vishal/NWW/sampleData/LOD2_1_F0.gml";
}
