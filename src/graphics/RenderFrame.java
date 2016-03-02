package graphics;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.view.BasicView;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

public class RenderFrame extends ApplicationTemplate {

	public static class InnerRenderFrame extends AppFrame {
		public InnerRenderFrame() {
			super(true, true, false);
		}

		private void addGeometry() {
			WorldWindow wwd = getWwd();
			//this.addMouseListener();
			//addMouseListener();
			BasicView view = (BasicView) wwd.getView();
			Position center = view.getCenterPosition();
		}
	}

	public static void main(String[] args) {
		ApplicationTemplate.start("Static Water Visualization", RenderFrame.InnerRenderFrame.class);
	}
}
