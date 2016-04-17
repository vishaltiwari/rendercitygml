package graphics;

import java.awt.Event;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.citygml4j.xml.io.reader.CityGMLReadException;
import org.postgis.PGgeometry;

import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.WindowUpdateEvent;

import dao.ChangeSRIDTables;
import dao.CleanUpDao;
import dao.GetBuildingEnvelope;
import dao.GetTiles;
import dao.InsertTileBuildingMappingdao;
import dao.PointTransformDao;
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
import helper.Utils;
import logic.BuildingsManager;
import logic.TilesManager;
import model.Tile;

public class RenderFrame extends ApplicationTemplate {

	public static class InnerRenderFrame extends AppFrame {

		public InnerRenderFrame() {
			//Clean the database,
			//Then use CLI to import the data.
			
			super(true, true, false);
			RenderMultipleBuildings.renderAllAtOnce();
			//addTiles();
//			addGeometry();
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
		
		//NOTE:
		//For generating 3d citymodels following the steps:
		/*
		 * 1. TRUNCATE all the tables and make the 3dCITYDB tables clean.
		 * 2. Import the citygml data, using the 3DcityDB importer GUI or the CLI.
		 * 3. Set the SRID code in the Properties.java file in the helper package. Make sure that it is stored in the Database. spatial_ref_sys table.
		 * 4. Run the code as usual. And it will create the tiles, and assign building to each tile.
		 * 5. But if you already have created the tiles, and done the mapping and its there in the DB, comment out the line till: BuildingsManager.buildingMappingToTile(mapping);
		 * 
		 * 6. There are two types on how you can render the buildings. 
		 * 		a. First is render all at once. See the InnerFrame constructor above.
		 * 		b. Second is use the tiling approach for rendering building.
		 * 	
		 */
		
		
		
		
		//TODO:
				//Step 1: After rendering is done. clean the data in the database.
				//Step 2: Read the cityGML file.
				//Step 3: Create tiles, and assign buildings via dao.
		
		//Cleanup the 3dCityDB Database before going forward.
//		CleanUpDao.cleanDataBase();
		
		//TODO: Read the CityGMLFile using the CLI interface.
		
		//Create the tiles and assign via dao.
		
		/*
		 * Comment the part till line "BuildingsManager.buildingMappingToTile(mapping);" if you have tilled the area, and stored in the Database.
		 */
		
		/*ChangeSRIDTables.changeSRID();
		
		TilesManager manager = null;
		try {
			manager = new TilesManager();
			manager.generateTiles();
		} catch (JAXBException | CityGMLReadException e1) {
			e1.printStackTrace();
		}
		//Map the buildings to the indiviual tiles.
		//InsertTileBuildingMappingdao.mapBuildingsToTiles();
		Map<Integer,PGgeometry> mapping = GetBuildingEnvelope.getBuildingEvnveoples();
		BuildingsManager.buildingMappingToTile(mapping);*/
		
		//Get the Center camera position of the starting scene.
		List<Double> centerCameraLOCAL = Utils.getCameraInitCoordinates(Properties.evelope);
		List<Double> centerCamera = PointTransformDao.getTranformation(centerCameraLOCAL);
		
		Double lat = centerCamera.get(1);
		Double log = centerCamera.get(0);
		Double height = centerCamera.get(2);
		
		/*Configuration.setValue(AVKey.INITIAL_LATITUDE, 52.32628405417232);
		Configuration.setValue(AVKey.INITIAL_LONGITUDE, 13.038721881991888);
		Configuration.setValue(AVKey.INITIAL_ALTITUDE, 500);*/
		
		Configuration.setValue(AVKey.INITIAL_LATITUDE, lat);
		Configuration.setValue(AVKey.INITIAL_LONGITUDE, log);
		Configuration.setValue(AVKey.INITIAL_ALTITUDE, height);
		
		ApplicationTemplate.start("Static Water Visualization", RenderFrame.InnerRenderFrame.class);
	}
}
