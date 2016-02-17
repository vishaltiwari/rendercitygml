package reader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.CityGMLBuilder;
import org.citygml4j.model.citygml.CityGML;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.bridge.BoundarySurfaceProperty;
import org.citygml4j.model.citygml.building.AbstractBoundarySurface;
import org.citygml4j.model.citygml.building.Building;
import org.citygml4j.model.citygml.building.GroundSurface;
import org.citygml4j.model.citygml.building.RoofSurface;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.citygml.core.CityModel;
import org.citygml4j.model.citygml.core.CityObjectMember;
import org.citygml4j.model.gml.feature.BoundingShape;
import org.citygml4j.model.gml.geometry.aggregates.MultiSurface;
import org.citygml4j.model.gml.geometry.aggregates.MultiSurfaceProperty;
import org.citygml4j.model.gml.geometry.primitives.AbstractRingProperty;
import org.citygml4j.model.gml.geometry.primitives.DirectPosition;
import org.citygml4j.model.gml.geometry.primitives.DirectPositionList;
import org.citygml4j.model.gml.geometry.primitives.LinearRing;
import org.citygml4j.model.gml.geometry.primitives.Polygon;
import org.citygml4j.model.gml.geometry.primitives.Surface;
import org.citygml4j.model.gml.geometry.primitives.SurfaceProperty;
import org.citygml4j.xml.io.CityGMLInputFactory;
import org.citygml4j.xml.io.reader.CityGMLReader;

import com.sun.xml.internal.messaging.saaj.soap.Envelope;

public class SimpleReader {

	public static void main(String[] args) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("[HH:mm:ss] "); 

		System.out.println(df.format(new Date()) + "setting up citygml4j context and JAXB builder");
		CityGMLContext ctx = new CityGMLContext();
		CityGMLBuilder builder = ctx.createCityGMLBuilder();
		
		System.out.println(df.format(new Date()) + "reading CityGML file LOD2_Buildings_v100.gml completely into main memory");
		CityGMLInputFactory in = builder.createCityGMLInputFactory();
		//CityGMLReader reader = in.createCityGMLReader(new File("./datasets/LOD2_Buildings_v100.gml"));
		CityGMLReader reader = in.createCityGMLReader(new File("./datasets/LOD3_3.gml"));
		System.out.println(reader.getBaseURI());	
		
		
		while (reader.hasNext()) {
			CityGML citygml = reader.nextFeature();
			System.out.println("Found " + citygml.getCityGMLClass() + 
					" version " + citygml.getCityGMLModule().getVersion());
			
			if (citygml.getCityGMLClass() == CityGMLClass.CITY_MODEL) {
				CityModel cityModel = (CityModel)citygml;
				BoundingShape shape = cityModel.getBoundedBy();
				DirectPosition lowerPosition = shape.getEnvelope().getLowerCorner();
				List<Double> lowerCoords = lowerPosition.getValue();
				List<Double> upperCoords = shape.getEnvelope().getUpperCorner().getValue();
				

				System.out.println(df.format(new Date()) + "going through city model and counting building instances");

				int count = 0;
				for (CityObjectMember cityObjectMember : cityModel.getCityObjectMember()) {
					AbstractCityObject cityObject = cityObjectMember.getCityObject();
						Building b = (Building)cityObject;
						if (cityObject.getCityGMLClass() == CityGMLClass.BUILDING){
						for(org.citygml4j.model.citygml.building.BoundarySurfaceProperty surface : b.getBoundedBySurface()){
							System.out.print(surface);
							GroundSurface ground = (GroundSurface)surface.getObject();
							MultiSurfaceProperty abs = ground.getLod3MultiSurface();
							MultiSurface sur = abs.getObject();
							for(SurfaceProperty surMem : sur.getSurfaceMember()){
								Polygon p = (Polygon) surMem.getObject();
								AbstractRingProperty ring = p.getExterior();
								LinearRing lring = (LinearRing) ring.getObject();
								DirectPositionList posList = lring.getPosList();
								ArrayList<Double> l = (ArrayList<Double>) posList.getValue();
								for(Double d : l){
									System.out.print(d + " ");
								}
								System.out.println(posList.getValue());
							}
						}
					}
						count++;
						
				}

				System.out.println("The city model contains " + count + " building features");
			}	
		}			
		
		reader.close();
		System.out.println(df.format(new Date()) + "sample citygml4j application successfully finished");
	}

}
