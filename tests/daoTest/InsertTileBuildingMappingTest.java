package daoTest;

import static org.junit.Assert.*;

import org.junit.Test;

import dao.InsertTileBuildingMappingdao;

public class InsertTileBuildingMappingTest {

	@Test
	public void test() {
		InsertTileBuildingMappingdao.mapBuildingsToTiles();
	}

}
