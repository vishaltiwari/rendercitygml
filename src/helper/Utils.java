package helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBException;

import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.CityGMLBuilder;
import org.citygml4j.model.citygml.CityGML;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.core.CityModel;
import org.citygml4j.model.gml.feature.BoundingShape;
import org.citygml4j.model.gml.geometry.primitives.Envelope;
import org.citygml4j.xml.io.CityGMLInputFactory;
import org.citygml4j.xml.io.reader.CityGMLReadException;
import org.citygml4j.xml.io.reader.CityGMLReader;

public class Utils {

	public static Envelope getEnvelope(String filename) throws JAXBException, CityGMLReadException {
		CityGMLContext ctx = new CityGMLContext();
		CityGMLBuilder builder = null;

		builder = ctx.createCityGMLBuilder();
		CityGMLInputFactory in = builder.createCityGMLInputFactory();
		CityGMLReader reader = in.createCityGMLReader(new File(filename));

		CityGML citygml = reader.nextFeature();
		
		if (citygml.getCityGMLClass() == CityGMLClass.CITY_MODEL) {
			CityModel cityModel = (CityModel) citygml;
			BoundingShape shape = cityModel.getBoundedBy();
			return shape.getEnvelope();
		}
		else
			return null;

	}
	
	
	public static List<Double> getCameraInitCoordinates(Envelope env){
		List<Double> lowerPt = env.getLowerCorner().getValue();
		List<Double> upperPt = env.getUpperCorner().getValue();
		List<Double> centerPoint = new ArrayList<Double>();
		centerPoint.add((lowerPt.get(0) + upperPt.get(0)) /2);
		centerPoint.add((lowerPt.get(1) + upperPt.get(1)) /2);
		centerPoint.add(1000.0);
		return centerPoint;
	}
	
	public static <T> Set<T> union(Set<T> setA, Set<T> setB) {
	    Set<T> tmp = new TreeSet<T>(setA);
	    tmp.addAll(setB);
	    return tmp;
	  }

	  public static <T> Set<T> intersection(Set<T> setA, Set<T> setB) {
	    Set<T> tmp = new TreeSet<T>();
	    for (T x : setA)
	      if (setB.contains(x))
	        tmp.add(x);
	    return tmp;
	  }

	  public static <T> Set<T> difference(Set<T> setA, Set<T> setB) {
	    Set<T> tmp = new TreeSet<T>(setA);
	    tmp.removeAll(setB);
	    return tmp;
	  }

}
