package com.swpu.o2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DynamicDataSourceHolder {
	//这是日志模块
	private static Logger logger =  LoggerFactory.getLogger(DynamicDataSourceHolder.class);
	//线程安全的ThreadLocal模式
	private static ThreadLocal<String> contextHolder=new ThreadLocal<String>();
	//两个key，主服务器，从服务器
	public static final String DB_MASTER="master";
	public static final String DB_SLAVE="slave";
	/**
	 * 获取线程的DbType
	 * @return db
	 */
	public static String getDbType(){
		
		String db=contextHolder.get();
		//如果为空，默认为master，即支持写，也支持读
		if(db==null){
			db=DB_MASTER;
		}
		return db;
	}
	/**
	 * 设置线程的dbType
	 * @param str
	 */
	public static void setDbType(String str){
		logger.debug("所使用的数据源是："+str);
		contextHolder.set(str);
		
	}
	/**
	 * 清理连接类型
	 */
	public static void DbType(){
		contextHolder.remove();
	}
}
