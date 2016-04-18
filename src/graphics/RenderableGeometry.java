package graphics;

import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Material;
import model.BuildingSurface;

public abstract class RenderableGeometry {
	
	protected Material interiorMaterial;
	protected int outlineWidth;
	protected double outlineOpacity;
	protected boolean drawInterior;
	
	public RenderableGeometry(Material interiorMaterial , int outlineWidth, double outlineOpacity, boolean drawInterior){
		this.interiorMaterial = interiorMaterial;
		this.outlineWidth = outlineWidth;
		this.outlineOpacity = outlineOpacity;
		this.drawInterior = drawInterior;
	}
	
	public abstract void addToRenderableLayer(RenderableLayer layer, BuildingSurface geomList , Integer tileId);
	public abstract void removeFromRenderableLayer(RenderableLayer layer, BuildingSurface geomList , Integer tileId);
	
	
}
