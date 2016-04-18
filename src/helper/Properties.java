package helper;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.citygml4j.model.gml.geometry.primitives.Envelope;

import dao.DBSetup;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Polygon;
import logic.OutterEnvelope;

public class Properties {
	public static final int tileSize=120;
	public static final Connection connection = DBSetup.setup();
	public static final String filename = "/home/vishal/NWW/sampleData/LOD2_1_F0.gml";
	//Get this from the gml file itself.
	public static final String SRID="25833";
	public static Envelope evelope = OutterEnvelope.getAreaEnvelope();
	public static RenderableLayer buildingsLayer = new RenderableLayer();
	public static RenderableLayer tilesLayer = new RenderableLayer();
	public static int batchSize=300;
	public static String appearencePath="/home/vishal/myProjects/rendercitygml/datasets/Lichtenberg/appearance";
	public static boolean textureSwith = true;
	public static Double heightSubtraction = 57.7400016784668;
	public static int level = 2;
	//public static List<Polygon> addedPolygons = new ArrayList<>();
	public static Map<Integer , List<Polygon> > addedPolygons = new HashMap<>();
	
}
