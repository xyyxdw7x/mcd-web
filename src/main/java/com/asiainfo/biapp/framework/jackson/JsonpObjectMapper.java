package com.asiainfo.biapp.framework.jackson;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * @author hjn
 *
 */
public class JsonpObjectMapper extends ObjectMapper {

	@Override
	public void writeValue(JsonGenerator jgen, Object value)
			throws IOException, JsonGenerationException, JsonMappingException {
		super.writeValue(jgen, value);
	}
}
