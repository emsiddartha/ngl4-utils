package com.bheaver.ngl4.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ExceptionJSONSerializer extends StdSerializer<NGLException> {
    public ExceptionJSONSerializer() {
        this(null);
    }

    public ExceptionJSONSerializer(Class<NGLException> t) {
        super(t);
    }

    @Override
    public void serialize(NGLException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("errorCode",e.getErrorCode());
        jsonGenerator.writeStringField("message",e.getMessage());
        jsonGenerator.writeEndObject();
    }
}
