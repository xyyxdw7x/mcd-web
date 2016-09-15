package com.asiainfo.biapp.framework.cache.memcached;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.cache.interceptor.KeyGenerator;

public class MemcachedKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		int targerHashCode=target.getClass().getName().hashCode();
		int methodHashCode=method.getName().hashCode();
		StringBuffer keyStrBf=new StringBuffer();
		keyStrBf.append(String.valueOf(targerHashCode));
		keyStrBf.append("#");
		keyStrBf.append(String.valueOf(methodHashCode));
		keyStrBf.append("#");
		keyStrBf.append(Arrays.deepHashCode(params));
		String keyName=keyStrBf.toString();
		return keyName;
	}
}
