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

public class RenderablePolygonGeom extends RenderableGeometry {

	private Polygon polygon = null;
	ShapeAttributes normalAttributes = new BasicShapeAttributes();
	private static List<Polygon> addedPolygons = new ArrayList<>();

	public RenderablePolygonGeom(Material interiorMaterial, int outlineWidth, double outlineOpacity,
			boolean drawInterior) {
		super(interiorMaterial, outlineWidth, outlineOpacity, drawInterior);
		normalAttributes.setInteriorMaterial(interiorMaterial);
		normalAttributes.setOutlineWidth(outlineWidth);
		normalAttributes.setOutlineOpacity(outlineOpacity);
		normalAttributes.setDrawInterior(drawInterior);
	}

	@Override
	public void addToRenderableLayer(RenderableLayer layer, PGgeometry geom) {
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
			layer.addRenderable(polygon);
			addedPolygons.add(polygon);
		}
	}

	@Override
	public void removeFromRenderableLayer(RenderableLayer layer, PGgeometry geom) {
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
			if(addedPolygons.contains(polygon)){
				layer.removeRenderable(polygon);
			}
			//System.out.println(layer.getNumRenderables());
		}
	}

}
