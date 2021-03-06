package graphics;

import java.util.List;

import org.postgis.PGgeometry;

import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Polygon;
import helper.Properties;
import model.Building;
import model.BuildingSurface;

public class RenderBuilding extends RenderCityObject{

	private List<BuildingSurface> roofSurface;
	private List<BuildingSurface> wallSurface;
	private List<BuildingSurface> groundSurface;
	private RenderableLayer buildingLayer;
	private Integer tileId;
	
	public RenderBuilding(Building building , RenderableLayer layer , Integer tileId){
		wallSurface = building.getWallSurface();
		roofSurface = building.getRoofSurface();
		groundSurface = building.getGroundSurface();
		buildingLayer = layer;
		this.tileId = tileId;
	}
	
	@Override
	public void renderObject() {
		//For all the Three loops change PgGeometry to BuildingSurface, 
		for(BuildingSurface surface : wallSurface){
			//System.out.println("WallGeom:" + surface.toString());
			RenderableGeometry rendergeom = null;
			if(Properties.textureSwith == false)
				rendergeom = new RenderablePolygonGeom(Material.YELLOW,2,0.5,true);
			else
				rendergeom = new RenderablePolygonGeom(Material.WHITE,2,0.5,true);
			
			rendergeom.addToRenderableLayer(buildingLayer, surface , tileId);
		}
		for(BuildingSurface surface : roofSurface){
			//System.out.println("RoofGeom:" + surface.toString());
			RenderableGeometry rendergeom = null;
			if(Properties.textureSwith == false)
				rendergeom = new RenderablePolygonGeom(Material.RED,2,0.5,true);
			else
				rendergeom = new RenderablePolygonGeom(Material.WHITE,2,0.5,true);
				
			rendergeom.addToRenderableLayer(buildingLayer, surface , tileId);
		}
		for(BuildingSurface surface : groundSurface){
			//System.out.println("GroundGeom:" + surface.toString());
			RenderableGeometry rendergeom = new RenderablePolygonGeom(Material.WHITE,2,0.5,true);
			rendergeom.addToRenderableLayer(buildingLayer, surface, tileId);
		}
	}

	@Override
	public void removeObject() {
		for(BuildingSurface surface : wallSurface){
			RenderableGeometry rendergeom = new RenderablePolygonGeom(Material.YELLOW,2,0.5,true);
			rendergeom.removeFromRenderableLayer(buildingLayer, surface, tileId);
		}
		for(BuildingSurface surface : roofSurface){
			RenderableGeometry rendergeom = new RenderablePolygonGeom(Material.RED,2,0.5,true);
			rendergeom.removeFromRenderableLayer(buildingLayer, surface, tileId);
		}
		for(BuildingSurface surface : groundSurface){
			RenderableGeometry rendergeom = new RenderablePolygonGeom(Material.BLACK,2,0.5,true);
			rendergeom.removeFromRenderableLayer(buildingLayer, surface, tileId);
		}
	}
}