package helper;

import java.sql.Connection;

import org.citygml4j.model.gml.geometry.primitives.Envelope;

import dao.DBSetup;
import gov.nasa.worldwind.layers.RenderableLayer;
import logic.OutterEnvelope;

public class Properties {
	public static final int tileSize=50;
	public static final Connection connection = DBSetup.setup();
	public static final String filename = "/home/vishal/NWW/sampleData/LOD2_1_F0.gml";
	//Get this from the gml file itself.
	public static final String SRID="93068";
	public static Envelope evelope = OutterEnvelope.getAreaEnvelope();
	public static RenderableLayer buildingsLayer = new RenderableLayer();
	public static RenderableLayer tilesLayer = new RenderableLayer();
}
