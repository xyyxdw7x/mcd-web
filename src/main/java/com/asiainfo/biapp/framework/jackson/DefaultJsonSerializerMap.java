package com.asiainfo.biapp.framework.jackson;

import java.util.Map;

import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ser.impl.JsonSerializerMap;
import org.codehaus.jackson.map.ser.impl.SerializerCache.TypeKey;

public class DefaultJsonSerializerMap extends JsonSerializerMap {

	public DefaultJsonSerializerMap(Map<TypeKey, JsonSerializer<Object>> arg0) {
		super(arg0);
	}

	@Override
	public JsonSerializer<Object> find(TypeKey key) {
		return super.find(key);
	}

}
