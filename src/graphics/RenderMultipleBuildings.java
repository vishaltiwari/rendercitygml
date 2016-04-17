package graphics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.GetBuildingsFromTileID;
import gov.nasa.worldwind.geom.Position;
import helper.Properties;
import helper.Utils;
import logic.TilesManager;
import model.Building;

public class RenderMultipleBuildings {

	static Set<Integer> oldTiles = new HashSet<>();

	/**
	 * @param position
	 */
	public static List<List<Integer>> TilesFromPosition(Position position) {
		List<Integer> currentTiles = TilesManager.getRenderingTiles(position);
		if(currentTiles == null) return null;
		List<List<Integer>> addRemList = new ArrayList<>();
		Set<Integer> addedTiles = new HashSet<>();
		Set<Integer> removeTiles = new HashSet<>();
		addRemList.add(currentTiles);
		addRemList.add(new ArrayList<Integer>());
		/*if (currentTiles != null) {
			Set<Integer> newTiles = new HashSet<>(currentTiles);
			Set<Integer> intersectionSet = Utils.intersection(newTiles, oldTiles);
			addedTiles = Utils.difference(newTiles, intersectionSet);
			removeTiles = Utils.difference(oldTiles, intersectionSet);
			oldTiles = newTiles;
			List<Integer> newList = new ArrayList<>(addedTiles);
			List<Integer> remList = new ArrayList<>(removeTiles);
			addRemList.add(newList);
			addRemList.add(remList);
		} else {
			System.out.println("no tiles in the region");
		}*/
		return addRemList;
	}

	public static void render(Position position) {
		List<List<Integer>> addRemTiles = TilesFromPosition(position);
		System.out.println(addRemTiles);
		if(addRemTiles == null) return ;
		System.out.println("New tile numbers to render:" + addRemTiles.toString());
		List<Integer> addList = addRemTiles.get(0);
		Properties.buildingsLayer.removeAllRenderables();
		for (Integer tileID : addList) {
			List<Building> buildings = GetBuildingsFromTileID.getBuildingsFromtileID(tileID, true , Properties.textureSwith);
			for (Building building : buildings) {
				RenderBuilding buildingRenderer = new RenderBuilding(building, Properties.buildingsLayer);
				buildingRenderer.renderObject();
			}
		}
		List<Integer> remList = addRemTiles.get(1);
		for (Integer tileID : remList) {
			List<Building> remBuildings = GetBuildingsFromTileID.getBuildingsFromtileID(tileID, true , Properties.textureSwith);
			for (Building building : remBuildings) {
				RenderBuilding buildingRemover = new RenderBuilding(building, Properties.buildingsLayer );
				buildingRemover.removeObject();
			}
		}
	}

	public static void renderAllAtOnce() {
		List<Building> buildings = GetBuildingsFromTileID.getBuildingsFromtileID(0, false , Properties.textureSwith);
		for (Building building : buildings) {
			RenderBuilding buildingRender = new RenderBuilding(building, Properties.buildingsLayer);
			buildingRender.renderObject();
		}
	}
}