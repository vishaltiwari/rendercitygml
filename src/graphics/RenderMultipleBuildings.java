package graphics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.GetBuildingsFromTileID;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Polygon;
import helper.Properties;
import helper.Utils;
import logic.TilesManager;
import model.Building;

public class RenderMultipleBuildings {

	public static Set<Integer> oldTiles = new HashSet<>();

	/**
	 * @param position
	 */
	public static List<List<Integer>> TilesFromPosition(Position position) {
		
		List<List<Integer>> addRemList = new ArrayList<>();
		
		List<Integer> currTilesList = TilesManager.getRenderingTiles(position);
		if(currTilesList == null)
			return null;
		
		Set<Integer> currentTileSet = new HashSet<>(currTilesList);
		Set<Integer> renderTiles = Utils.difference(Utils.union(oldTiles, currentTileSet), oldTiles);
		Set<Integer> removeTiles = Utils.difference(Utils.union(oldTiles, currentTileSet), currentTileSet);
		
		List<Integer> addedTiles = new ArrayList<>();
		addedTiles.addAll(renderTiles);
		
		List<Integer> ridList = new ArrayList<>();
		ridList.addAll(removeTiles);
		
		addRemList.add(addedTiles);
		addRemList.add(ridList);
		
		oldTiles = currentTileSet;
		
		return addRemList;
		
		
		/*List<Integer> currentTiles = TilesManager.getRenderingTiles(position);
		if (currentTiles == null)
			return null;
		List<List<Integer>> addRemList = new ArrayList<>();
		Set<Integer> addedTiles = new HashSet<>();
		Set<Integer> removeTiles = new HashSet<>();
		addRemList.add(currentTiles);
		addRemList.add(new ArrayList<Integer>());

		Set<Integer> newTiles = new HashSet<>(currentTiles);
		Set<Integer> intersectionSet = Utils.intersection(newTiles, oldTiles);
		addedTiles = Utils.difference(newTiles, intersectionSet);
		removeTiles = Utils.difference(oldTiles, intersectionSet);
		oldTiles = newTiles;
		List<Integer> newList = new ArrayList<>(addedTiles);
		List<Integer> remList = new ArrayList<>(removeTiles);
		addRemList.add(newList);
		addRemList.add(remList);

		return addRemList;*/
	}

	public static void render(Position position) {
		List<List<Integer>> addRemTiles = TilesFromPosition(position);
		System.out.println(addRemTiles);
		if (addRemTiles == null)
			return;
		if(addRemTiles.get(0).isEmpty() && addRemTiles.get(1).isEmpty())
			return ;
		System.out.println("New tile numbers to render:" + addRemTiles.toString());
		List<Integer> addList = addRemTiles.get(0);

		// Properties.buildingsLayer.removeAllRenderables();

		for (Integer tileID : addList) {
			List<Building> buildings = GetBuildingsFromTileID.getBuildingsFromtileID(tileID, true,
					Properties.textureSwith);
			for (Building building : buildings) {
				RenderBuilding buildingRenderer = new RenderBuilding(building, Properties.buildingsLayer, tileID);
				buildingRenderer.renderObject();
			}
		}
		List<Integer> remList = addRemTiles.get(1);
		for (Integer tileID : remList) {
			List<Polygon> polygonList = Properties.addedPolygons.get(tileID);
			if(polygonList == null) continue;
			for (Polygon p : polygonList) {
				Properties.buildingsLayer.removeRenderable(p);
			}
			Properties.addedPolygons.remove(tileID);
			/*
			 * List<Building> remBuildings =
			 * GetBuildingsFromTileID.getBuildingsFromtileID(tileID, true ,
			 * Properties.textureSwith); for (Building building : remBuildings)
			 * { RenderBuilding buildingRemover = new RenderBuilding(building,
			 * Properties.buildingsLayer , tileID);
			 * buildingRemover.removeObject(); }
			 */
		}
	}

	// TODO: If tileId == 0, render it all. Make changes in the
	// buildingRenderer.
	public static void renderAllAtOnce() {
		List<Building> buildings = GetBuildingsFromTileID.getBuildingsFromtileID(0, false, Properties.textureSwith);
		for (Building building : buildings) {
			RenderBuilding buildingRender = new RenderBuilding(building, Properties.buildingsLayer, 0);
			buildingRender.renderObject();
		}
	}
}