package com.asiainfo.biapp.framework.resteasy;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.JsonParser;
//import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.asiainfo.biapp.framework.jackson.JsonpObjectMapper;



@Provider
@Produces(MediaType.APPLICATION_JSON)
public class DefaultJacksonObjectMapper implements ContextResolver<ObjectMapper> {

	private JsonpObjectMapper objectMapper;

	public DefaultJacksonObjectMapper() throws Exception {
		this.objectMapper = new JsonpObjectMapper();
		this.objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		//key可以不加引号
		this.objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		//this.objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
		//this.objectMapper.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
		//this.objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Override
	public ObjectMapper getContext(Class<?> objectType) {
		return objectMapper;
	}
}
