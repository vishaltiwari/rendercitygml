package graphics;

import java.awt.Event;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import dao.GetTiles;
import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.PositionEvent;
import gov.nasa.worldwind.event.PositionListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.view.BasicView;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import helper.MousePressedMovedEvent;
import helper.Properties;
import model.Tile;

public class RenderFrame extends ApplicationTemplate {

	public static class InnerRenderFrame extends AppFrame {

		public InnerRenderFrame() {
			super(true, true, false);
			//RenderMultipleBuildings.renderAllAtOnce();
			addTiles();
			addGeometry();
			insertBeforePlacenames(getWwd(), Properties.tilesLayer);
			insertBeforePlacenames(getWwd(), Properties.buildingsLayer);
			
			this.getLayerPanel().update(this.getWwd());
			//List<Double> lowerCoords = Properties.evelope.getLowerCorner().getValue();
			//List<Double> upperCoords = Properties.evelope.getUpperCorner().getValue();
			//Configuration.setValue(AVKey.INITIAL_LATITUDE, lowerCoords.get(1) + upperCoords.get(1));
	        //Configuration.setValue(AVKey.INITIAL_LONGITUDE, upperCoords.get(0) + upperCoords.get(0));
	        //Configuration.setValue(AVKey.INITIAL_ALTITUDE, 100);
		}

		private void addGeometry() {
			WorldWindow wwd = getWwd();
			//this is listening to the mouse events::
			this.getWwd().getInputHandler().addMouseListener(new MousePressedMovedEvent(wwd));
			System.out.println("added the mouse listener");
		}
		private void addTiles(){
			List<Tile> tiles = GetTiles.getTiles();
			RenderTilesGrid.renderTiles(tiles);
		}
	}

	public static void main(String[] args) {
		Configuration.setValue(AVKey.INITIAL_LATITUDE, 52.32628405417232);
		Configuration.setValue(AVKey.INITIAL_LONGITUDE, 13.038721881991888);
		Configuration.setValue(AVKey.INITIAL_ALTITUDE, 500);
		ApplicationTemplate.start("Static Water Visualization", RenderFrame.InnerRenderFrame.class);
	}
}
