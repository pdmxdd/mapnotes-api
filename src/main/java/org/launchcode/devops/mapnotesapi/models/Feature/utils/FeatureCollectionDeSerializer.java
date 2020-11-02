package org.launchcode.devops.mapnotesapi.models.Feature.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Geometry;

import org.launchcode.devops.mapnotesapi.models.Feature.Feature;
import org.launchcode.devops.mapnotesapi.models.Feature.FeatureCollection;

public class FeatureCollectionDeSerializer extends JsonDeserializer<FeatureCollection> {
    
    public FeatureCollectionDeSerializer() {}

    @Override
    public FeatureCollection deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode root = objectCodec.readTree(jsonParser);
        List<Feature> features = new ArrayList<>();
        JsonNode nodeFeatures = root.findValue("features");
        for(JsonNode nodeFeature : nodeFeatures) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JtsModule());
            JsonNode featureGeometry = nodeFeature.findValue("geometry");
            Geometry featureGeom = mapper.treeToValue(featureGeometry, Geometry.class);
            Feature newFeature = new Feature(featureGeom);
            features.add(newFeature);
        }

        return new FeatureCollection(features);
        
    }

}
