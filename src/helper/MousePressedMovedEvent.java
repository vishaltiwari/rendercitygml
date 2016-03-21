package helper;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.view.BasicView;
import graphics.RenderFrame;
import graphics.RenderMultipleBuildings;
import logic.TilesManager;

public class MousePressedMovedEvent implements MouseListener{

	private WorldWindow wwd;
	public MousePressedMovedEvent(WorldWindow wwd){
		this.wwd = wwd;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("blah");
		BasicView view = (BasicView) wwd.getView();
		Position center = view.getCenterPosition();
		
		System.out.println(center);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("blah");
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		//System.out.println("blah");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// Get the center coordinate of the globe from the view.
		// Get the tiles which needs to be render from the DB. (call the dao).
		//Return the tile number which are to rendered.
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		//System.out.println("blah");
		// TODO Auto-generated method stub
		System.out.println("Mouse is pressed.");
		BasicView view = (BasicView) wwd.getView();
		Position center = view.getCenterPosition();
		RenderMultipleBuildings.render(center);
		System.out.println(center.toString());
	}

}
