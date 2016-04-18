package graphics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.postgis.LinearRing;
import org.postgis.PGgeometry;
import org.postgis.Point;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Polygon;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.WWTexture;
import helper.Properties;
import model.BuildingSurface;

public class RenderablePolygonGeom extends RenderableGeometry {

	private Polygon polygon = null;
	ShapeAttributes normalAttributes = new BasicShapeAttributes();

	public RenderablePolygonGeom(Material interiorMaterial, int outlineWidth, double outlineOpacity,
			boolean drawInterior) {
		super(interiorMaterial, outlineWidth, outlineOpacity, drawInterior);
		normalAttributes.setInteriorMaterial(interiorMaterial);
		normalAttributes.setOutlineWidth(outlineWidth);
		normalAttributes.setOutlineOpacity(outlineOpacity);
		normalAttributes.setDrawInterior(drawInterior);
		
		normalAttributes.setInteriorOpacity(1);
		normalAttributes.setDrawOutline(true);
		normalAttributes.setEnableLighting(true);
		
	}

	//Change PGgeometry To BuildingSurface. 
	@Override
	public void addToRenderableLayer(RenderableLayer layer, BuildingSurface surface , Integer tileId) {
		//Add a PGgeometry var, float[] , and a String variable.
		PGgeometry geom = surface.getGeom();
		float[] texCoords = surface.getTexCoords();
		String imageURI = surface.getImageURI();
		ArrayList<Position> geomPositions = new ArrayList<>();
		org.postgis.Polygon p = (org.postgis.Polygon) geom.getGeometry();
		for (int i = 0; i < p.numRings(); i++) {
			LinearRing ring = p.getRing(i);

			for (int j = 0; j < ring.numPoints(); j++) {
				Point pt = ring.getPoint(j);
				geomPositions.add(Position.fromDegrees(pt.y, pt.x, pt.z - Properties.heightSubtraction));
			}
			polygon = new Polygon(geomPositions);
			polygon.setAttributes(normalAttributes);
			//polygon.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
			if(texCoords != null && texCoords.length != 0)
				polygon.setTextureImageSource(imageURI, texCoords, texCoords.length /2);
			polygon.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
			layer.addRenderable(polygon);
			
			if(Properties.addedPolygons.containsKey(tileId)){
				Properties.addedPolygons.get(tileId).add(polygon);
			}
			else{
				List<Polygon> polygonList = new ArrayList<>();
				polygonList.add(polygon);
				Properties.addedPolygons.put(tileId, polygonList);
			}
		}
	}
	
	@Deprecated
	public void removeFromRenderableLayer_bck(RenderableLayer layer, BuildingSurface surface) {
		PGgeometry geom = surface.getGeom();
		float[] textCoords = surface.getTexCoords();
		String imageURI = surface.getImageURI();
		
		ArrayList<Position> geomPositions = new ArrayList<>();
		org.postgis.Polygon p = (org.postgis.Polygon) geom.getGeometry();
		for (int i = 0; i < p.numRings(); i++) {
			LinearRing ring = p.getRing(i);

			for (int j = 0; j < ring.numPoints(); j++) {
				Point pt = ring.getPoint(j);
				geomPositions.add(Position.fromDegrees(pt.y, pt.x, pt.z));
			}
			polygon = new Polygon(geomPositions);
			polygon.setAttributes(normalAttributes);
			polygon.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
			layer.removeRenderable(polygon);
			//System.out.println(layer.getNumRenderables());
			
			//System.out.println(layer.getNumRenderables());
		}
	}

	@Override
	public void removeFromRenderableLayer(RenderableLayer layer, BuildingSurface geomList, Integer tileId) {
		
	}

}
