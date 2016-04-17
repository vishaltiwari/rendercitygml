package model;

import java.util.Arrays;

import org.postgis.PGgeometry;

public class BuildingSurface {
	
	private PGgeometry geom;
	private String imageURI;
	private float[] texCoords;
	
	public BuildingSurface(){
		
	}
	public BuildingSurface(PGgeometry geom){
		this.geom = geom;
	}
	
	
	public BuildingSurface(PGgeometry geom, String imageURI, float[] texCoords) {
		super();
		this.geom = geom;
		this.imageURI = imageURI;
		this.texCoords = texCoords;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BuildingSurface [geom=" + geom.getValue() + ", imageURI=" + imageURI + ", texCoords=" + Arrays.toString(texCoords)
				+ "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((geom == null) ? 0 : geom.hashCode());
		result = prime * result + ((imageURI == null) ? 0 : imageURI.hashCode());
		result = prime * result + Arrays.hashCode(texCoords);
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuildingSurface other = (BuildingSurface) obj;
		if (geom == null) {
			if (other.geom != null)
				return false;
		} else if (!geom.equals(other.geom))
			return false;
		if (imageURI == null) {
			if (other.imageURI != null)
				return false;
		} else if (!imageURI.equals(other.imageURI))
			return false;
		if (!Arrays.equals(texCoords, other.texCoords))
			return false;
		return true;
	}
	/**
	 * @return the geom
	 */
	public PGgeometry getGeom() {
		return geom;
	}
	/**
	 * @param geom the geom to set
	 */
	public void setGeom(PGgeometry geom) {
		this.geom = geom;
	}
	/**
	 * @return the imageURI
	 */
	public String getImageURI() {
		return imageURI;
	}
	/**
	 * @param imageURI the imageURI to set
	 */
	public void setImageURI(String imageURI) {
		this.imageURI = imageURI;
	}
	/**
	 * @return the texCoords
	 */
	public float[] getTexCoords() {
		return texCoords;
	}
	/**
	 * @param texCoords the texCoords to set
	 */
	public void setTexCoords(float[] texCoords) {
		this.texCoords = texCoords;
	}
}
