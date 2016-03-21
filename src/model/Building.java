package model;

import java.util.List;

import org.postgis.PGgeometry;

public class Building {
	private Integer id;
	private String class_;
	private String class_codespace;
	private String function_codespace;
	private String function;
	private String usage;
	private String usage_codespace;
	private String year_of_construction;
	private String year_of_demolition;
	private String roof_type;
	private String roof_type_codespace;
	private Double measured_height;
	private Double measured_height_unit;
	private Integer storeys_above_ground;
	private Integer storeys_below_ground;
	
	private Integer tile_id; 
	
	private List<PGgeometry> groundSurface;
	private List<PGgeometry> roofSurface;
	private List<PGgeometry> wallSurface;
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((class_ == null) ? 0 : class_.hashCode());
		result = prime * result + ((class_codespace == null) ? 0 : class_codespace.hashCode());
		result = prime * result + ((function == null) ? 0 : function.hashCode());
		result = prime * result + ((function_codespace == null) ? 0 : function_codespace.hashCode());
		result = prime * result + ((groundSurface == null) ? 0 : groundSurface.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((measured_height == null) ? 0 : measured_height.hashCode());
		result = prime * result + ((measured_height_unit == null) ? 0 : measured_height_unit.hashCode());
		result = prime * result + ((roofSurface == null) ? 0 : roofSurface.hashCode());
		result = prime * result + ((roof_type == null) ? 0 : roof_type.hashCode());
		result = prime * result + ((roof_type_codespace == null) ? 0 : roof_type_codespace.hashCode());
		result = prime * result + ((storeys_above_ground == null) ? 0 : storeys_above_ground.hashCode());
		result = prime * result + ((storeys_below_ground == null) ? 0 : storeys_below_ground.hashCode());
		result = prime * result + ((tile_id == null) ? 0 : tile_id.hashCode());
		result = prime * result + ((usage == null) ? 0 : usage.hashCode());
		result = prime * result + ((usage_codespace == null) ? 0 : usage_codespace.hashCode());
		result = prime * result + ((wallSurface == null) ? 0 : wallSurface.hashCode());
		result = prime * result + ((year_of_construction == null) ? 0 : year_of_construction.hashCode());
		result = prime * result + ((year_of_demolition == null) ? 0 : year_of_demolition.hashCode());
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
		Building other = (Building) obj;
		if (class_ == null) {
			if (other.class_ != null)
				return false;
		} else if (!class_.equals(other.class_))
			return false;
		if (class_codespace == null) {
			if (other.class_codespace != null)
				return false;
		} else if (!class_codespace.equals(other.class_codespace))
			return false;
		if (function == null) {
			if (other.function != null)
				return false;
		} else if (!function.equals(other.function))
			return false;
		if (function_codespace == null) {
			if (other.function_codespace != null)
				return false;
		} else if (!function_codespace.equals(other.function_codespace))
			return false;
		if (groundSurface == null) {
			if (other.groundSurface != null)
				return false;
		} else if (!groundSurface.equals(other.groundSurface))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (measured_height == null) {
			if (other.measured_height != null)
				return false;
		} else if (!measured_height.equals(other.measured_height))
			return false;
		if (measured_height_unit == null) {
			if (other.measured_height_unit != null)
				return false;
		} else if (!measured_height_unit.equals(other.measured_height_unit))
			return false;
		if (roofSurface == null) {
			if (other.roofSurface != null)
				return false;
		} else if (!roofSurface.equals(other.roofSurface))
			return false;
		if (roof_type == null) {
			if (other.roof_type != null)
				return false;
		} else if (!roof_type.equals(other.roof_type))
			return false;
		if (roof_type_codespace == null) {
			if (other.roof_type_codespace != null)
				return false;
		} else if (!roof_type_codespace.equals(other.roof_type_codespace))
			return false;
		if (storeys_above_ground == null) {
			if (other.storeys_above_ground != null)
				return false;
		} else if (!storeys_above_ground.equals(other.storeys_above_ground))
			return false;
		if (storeys_below_ground == null) {
			if (other.storeys_below_ground != null)
				return false;
		} else if (!storeys_below_ground.equals(other.storeys_below_ground))
			return false;
		if (tile_id == null) {
			if (other.tile_id != null)
				return false;
		} else if (!tile_id.equals(other.tile_id))
			return false;
		if (usage == null) {
			if (other.usage != null)
				return false;
		} else if (!usage.equals(other.usage))
			return false;
		if (usage_codespace == null) {
			if (other.usage_codespace != null)
				return false;
		} else if (!usage_codespace.equals(other.usage_codespace))
			return false;
		if (wallSurface == null) {
			if (other.wallSurface != null)
				return false;
		} else if (!wallSurface.equals(other.wallSurface))
			return false;
		if (year_of_construction == null) {
			if (other.year_of_construction != null)
				return false;
		} else if (!year_of_construction.equals(other.year_of_construction))
			return false;
		if (year_of_demolition == null) {
			if (other.year_of_demolition != null)
				return false;
		} else if (!year_of_demolition.equals(other.year_of_demolition))
			return false;
		return true;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClass_() {
		return class_;
	}
	public void setClass_(String class_) {
		this.class_ = class_;
	}
	public String getClass_codespace() {
		return class_codespace;
	}
	public void setClass_codespace(String class_codespace) {
		this.class_codespace = class_codespace;
	}
	public String getFunction_codespace() {
		return function_codespace;
	}
	public void setFunction_codespace(String function_codespace) {
		this.function_codespace = function_codespace;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getUsage_codespace() {
		return usage_codespace;
	}
	public void setUsage_codespace(String usage_codespace) {
		this.usage_codespace = usage_codespace;
	}
	public String getYear_of_construction() {
		return year_of_construction;
	}
	public void setYear_of_construction(String year_of_construction) {
		this.year_of_construction = year_of_construction;
	}
	public String getYear_of_demolition() {
		return year_of_demolition;
	}
	public void setYear_of_demolition(String year_of_demolition) {
		this.year_of_demolition = year_of_demolition;
	}
	public String getRoof_type() {
		return roof_type;
	}
	public void setRoof_type(String roof_type) {
		this.roof_type = roof_type;
	}
	public String getRoof_type_codespace() {
		return roof_type_codespace;
	}
	public void setRoof_type_codespace(String roof_type_codespace) {
		this.roof_type_codespace = roof_type_codespace;
	}
	public Double getMeasured_height() {
		return measured_height;
	}
	public void setMeasured_height(Double measured_height) {
		this.measured_height = measured_height;
	}
	public Double getMeasured_height_unit() {
		return measured_height_unit;
	}
	public void setMeasured_height_unit(Double measured_height_unit) {
		this.measured_height_unit = measured_height_unit;
	}
	public Integer getStoreys_above_ground() {
		return storeys_above_ground;
	}
	public void setStoreys_above_ground(Integer storeys_above_ground) {
		this.storeys_above_ground = storeys_above_ground;
	}
	public Integer getStoreys_below_ground() {
		return storeys_below_ground;
	}
	public void setStoreys_below_ground(Integer storeys_below_ground) {
		this.storeys_below_ground = storeys_below_ground;
	}

	public List<PGgeometry> getGroundSurface() {
		return groundSurface;
	}

	public void setGroundSurface(List<PGgeometry> groundSurface) {
		this.groundSurface = groundSurface;
	}

	public List<PGgeometry> getRoofSurface() {
		return roofSurface;
	}

	public void setRoofSurface(List<PGgeometry> roofSurface) {
		this.roofSurface = roofSurface;
	}

	public List<PGgeometry> getWallSurface() {
		return wallSurface;
	}

	public void setWallSurface(List<PGgeometry> wallSurface) {
		this.wallSurface = wallSurface;
	}
	/**
	 * @return the tile_id
	 */
	public Integer getTile_id() {
		return tile_id;
	}
	/**
	 * @param tile_id the tile_id to set
	 */
	public void setTile_id(Integer tile_id) {
		this.tile_id = tile_id;
	}
	
	
}