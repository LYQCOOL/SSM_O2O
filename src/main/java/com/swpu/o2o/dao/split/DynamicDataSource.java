package com.swpu.o2o.dao.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * 继承AbstractRoutingDataSource实现抽象方法
 * @author ASUS
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.getDbType();
	}
	

}
