package com.swpu.o2o.dao.split;

import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 拦截器，实现mybatis中的Interceptor接口
 * 
 * @author ASUS
 *
 */
//指定拦截类型，mybatis会把增删改封装到update中
@Intercepts({@Signature(type=Executor.class,method="update",args={MappedStatement.class,Object.class}),
	@Signature(type=Executor.class,method="query",args={MappedStatement.class,Object.class,RowBounds.class,ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {
	//日志
	private static Logger logger =  LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

	// 匹配的正则表达(增删改\u0020表示空格)
	private static final String REGEX = ".*insert\\u0020.*|.*delete \\u0020.*|.*update\\u0020.*";

	// 主要拦截方法
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 判断当前是不是事务的
		boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
		Object[] objects = invocation.getArgs();
		MappedStatement ms = (MappedStatement) objects[0];
		String lookupKey = DynamicDataSourceHolder.DB_MASTER;
		if (synchronizationActive != true) {
			// 是否为读方法
			if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
				// selectKey为自增id查询主键(SELECT_KEY_SUFFIX())方法，使用主库（更新）
				if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
					lookupKey = DynamicDataSourceHolder.DB_MASTER;
				} else {
					// 格式化sql语句
					BoundSql boundsql = ms.getSqlSource().getBoundSql(objects[1]);
					String sql = boundsql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
					// 使用正则匹配是否是增删改
					if (sql.matches(REGEX)) {
						lookupKey = DynamicDataSourceHolder.DB_MASTER;

					} else {
						lookupKey = DynamicDataSourceHolder.DB_SLAVE;
					}
				}
			}

		} else {
			lookupKey = DynamicDataSourceHolder.DB_SLAVE;
		}
		logger.debug("设置方法[{}]use [{}] Strategy,SqlCommanType[{}]",ms.getId(),lookupKey,ms.getSqlCommandType());
		return invocation.proceed();
	}

	// 返回封装好的对象或代理对象
	@Override
	public Object plugin(Object target) {
		// 如果是mybatis中的Executor对象（增删改查），就通过intercept()封装返回，否则直接防回
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	// 设置相关代理，不是必备的
	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub

	}

}
