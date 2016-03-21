package graphics;

import java.util.List;

import org.postgis.PGgeometry;

import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Polygon;
import model.Building;

public class RenderBuilding extends RenderCityObject{

	private List<PGgeometry> roofSurface;
	private List<PGgeometry> wallSurface;
	private List<PGgeometry> groundSurface;
	private RenderableLayer buildingLayer;
	
	public RenderBuilding(Building building , RenderableLayer layer){
		wallSurface = building.getWallSurface();
		roofSurface = building.getRoofSurface();
		groundSurface = building.getGroundSurface();
		buildingLayer = layer;
	}
	
	@Override
	public void renderObject() {
		for(PGgeometry geom : wallSurface){
			System.out.println("WallGeom:" + geom.getValue());
			RenderableGeometry rendergeom = new RenderablePolygonGeom(Material.YELLOW,2,0.5,true);
			rendergeom.addToRenderableLayer(buildingLayer, geom);
		}
		for(PGgeometry geom : roofSurface){
			System.out.println("RoofGeom:" + geom.getValue());
			RenderableGeometry rendergeom = new RenderablePolygonGeom(Material.RED,2,0.5,true);
			rendergeom.addToRenderableLayer(buildingLayer, geom);
		}
		for(PGgeometry geom : groundSurface){
			System.out.println("GroundGeom:" + geom.getValue());
			RenderableGeometry rendergeom = new RenderablePolygonGeom(Material.BLACK,2,0.5,true);
			rendergeom.addToRenderableLayer(buildingLayer, geom);
		}
	}

	@Override
	public void removeObject() {
		for(PGgeometry geom : wallSurface){
			RenderableGeometry rendergeom = new RenderablePolygonGeom(Material.YELLOW,2,0.5,true);
			rendergeom.removeFromRenderableLayer(buildingLayer, geom);
		}
		for(PGgeometry geom : roofSurface){
			RenderableGeometry rendergeom = new RenderablePolygonGeom(Material.RED,2,0.5,true);
			rendergeom.removeFromRenderableLayer(buildingLayer, geom);
		}
		for(PGgeometry geom : groundSurface){
			RenderableGeometry rendergeom = new RenderablePolygonGeom(Material.BLACK,2,0.5,true);
			rendergeom.removeFromRenderableLayer(buildingLayer, geom);
		}
	}
}