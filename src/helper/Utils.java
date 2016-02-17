package helper;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.CityGMLBuilder;
import org.citygml4j.model.citygml.CityGML;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.core.CityModel;
import org.citygml4j.model.gml.feature.BoundingShape;
import org.citygml4j.model.gml.geometry.primitives.DirectPosition;
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
}
