package com.emeka.delivery.config;

import java.io.IOException;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PointSerializer extends JsonSerializer<Point> {
    @Override
    public void serialize(Point point, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeNumber(point.getX());
        jsonGenerator.writeNumber(point.getY());
        jsonGenerator.writeEndArray();
    }
}
