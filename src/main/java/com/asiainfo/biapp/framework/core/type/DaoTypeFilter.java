package com.asiainfo.biapp.framework.core.type;

import java.io.IOException;

import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class DaoTypeFilter implements TypeFilter {

	//private @Value("${db.type}") String dataBaseType;
	
	@Override
	public boolean match(MetadataReader metadataReader,
			MetadataReaderFactory metadataReaderFactory) throws IOException {
		if(metadataReader instanceof StandardAnnotationMetadata){
			//StandardAnnotationMetadata sam=(StandardAnnotationMetadata)metadataReader;
		}
		//todo 根据数据库类型来判断是否注入
		return true;
	}

}
