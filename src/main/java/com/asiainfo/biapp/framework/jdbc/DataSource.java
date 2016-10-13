package com.asiainfo.biapp.framework.jdbc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

	String name() default DataSource.MASTER;
	 
    public static String MASTER = RoutingDataSource.MASTER;
 
    public static String MASTER_MEM = RoutingDataSource.MASTER_MEM;
 
    public static String SLAVE = RoutingDataSource.SLAVE;
    
    public static String SLAVE_MEM = RoutingDataSource.SLAVE_MEM;
    
}
