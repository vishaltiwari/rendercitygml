package graphics;

import java.util.List;

import org.postgis.PGgeometry;

import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Material;
import helper.Properties;
import model.BuildingSurface;
import model.Tile;

public class RenderTilesGrid extends RenderCityObject {

	private Tile tile;
	private RenderableLayer tileLayer;

	public RenderTilesGrid(Tile tile, RenderableLayer layer) {
		this.tile = tile;
		this.tileLayer = layer;
	}

	@Override
	public void renderObject() {
		RenderableGeometry renderGeom = new RenderablePolygonGeom(Material.WHITE, 5, 0.5, true);
		renderGeom.addToRenderableLayer(tileLayer, new BuildingSurface(tile.getTileGeometry()) , tile.getTileID());
	}

	@Override
	public void removeObject() {
		// TODO Auto-generated method stub

	}

	public static void renderTiles(List<Tile> tiles) {
		for (Tile t : tiles) {
			RenderTilesGrid renderGrid = new RenderTilesGrid(t, Properties.tilesLayer);
			renderGrid.renderObject();
		}
	}

}