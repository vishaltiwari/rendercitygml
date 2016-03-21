package model;

import java.util.List;

import org.postgis.PGgeometry;

public class Tile {
	private Integer tileID;
	private PGgeometry tileGeometry;
	
	public Tile(Integer tileID, PGgeometry tileGeometry) {
		super();
		this.tileID = tileID;
		this.tileGeometry = tileGeometry;
	}
	/**
	 * @return the tileID
	 */
	public Integer getTileID() {
		return tileID;
	}
	/**
	 * @param tileID the tileID to set
	 */
	public void setTileID(Integer tileID) {
		this.tileID = tileID;
	}
	/**
	 * @return the tileGeometry
	 */
	public PGgeometry getTileGeometry() {
		return tileGeometry;
	}
	/**
	 * @param tileGeometry the tileGeometry to set
	 */
	public void setTileGeometry(PGgeometry tileGeometry) {
		this.tileGeometry = tileGeometry;
	}
	
}
