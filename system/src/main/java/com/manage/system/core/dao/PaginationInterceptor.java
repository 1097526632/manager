package com.manage.system.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.manage.system.common.utils.Reflections;
import com.manage.system.core.entity.Page;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * 分页拦截器mybatis
 *
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class , Integer.class}) })
public class PaginationInterceptor implements Interceptor{

	private static final Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);

	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
	private static String dialect = "mysql";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 获得拦截的对象
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		// 待执行的sql的包装对象
		BoundSql boundSql = statementHandler.getBoundSql();
		// 判断是否是查询语句
		if (isSelect(boundSql.getSql())) {
			// 获得参数集合
			Object params = boundSql.getParameterObject();

			 Page page=convertParameter(params);
			if(page!=null){
				pageHandlerExecutor(invocation,boundSql,page); //处理分页
			}
		}
		return invocation.proceed();
	}

	protected static Page<Object> convertParameter(Object parameterObject) {
		try{
			if (parameterObject instanceof Page) {
				return (Page<Object>) parameterObject;
			} else {
				return (Page<Object>) Reflections.getFieldValue(parameterObject, "page");
			}
		}catch (Exception e) {
			return null;
		}
	}

	private boolean isSelect(String sql) {
		if (!StringUtils.isEmpty(sql) && sql.toUpperCase().trim().startsWith("SELECT")) {
			return true;
		}
		return false;
	}

	private Object pageHandlerExecutor(Invocation invocation, BoundSql boundSql, Page page) throws Throwable {
		// 获得数据库连接
		Connection connection = (Connection) invocation.getArgs()[0];
		// 使用Mybatis提供的MetaObject，该对象主要用于获取包装对象的属性值
		MetaObject statementHandler = MetaObject.forObject(invocation.getTarget(), DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);

		// 获取该sql执行的结果集总数
		int maxSize = getTotalSize(connection, (MappedStatement) statementHandler.getValue("delegate.mappedStatement"),
				boundSql,page);

		// 生成分页sql
		page.setTotalRecord(maxSize);
		String wrapperSql = getPageSql(boundSql.getSql(), page);

		MetaObject boundSqlMeta = MetaObject.forObject(boundSql, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,
				DEFAULT_REFLECTOR_FACTORY);
		// 修改boundSql的sql
		boundSqlMeta.setValue("sql", wrapperSql);
		return invocation.proceed();
	}

	private int getTotalSize(Connection connection, MappedStatement mappedStatement, BoundSql boundSql,Page page) {
		String countSql = getCountSql(boundSql.getSql(),page);
		PreparedStatement countStmt;
		ResultSet rs;
		List<AutoCloseable> closeableList = new ArrayList<AutoCloseable>();

		try {
			countStmt = connection.prepareStatement(countSql);
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
					boundSql.getParameterMappings(), boundSql.getParameterObject());
			setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
			rs = countStmt.executeQuery();

			if (rs.next()) {
				int cnt=rs.getInt("cnt");
				page.setTotalRecord(cnt);
				if(page.getSumParams()!=null&&page.getSumParams().size()>0){
					Map<String,String> sumResult=new HashMap<String,String>();
					Set<Map.Entry<String,String>> entrys=page.getSumParams().entrySet();
					for(Map.Entry<String,String> entry:entrys) {
						String key=entry.getKey();
						sumResult.put(key,rs.getString("cnt_"+key));
					}
					page.setSumResult(sumResult);
				}
				return cnt;
			}
			closeableList.add(countStmt);
			closeableList.add(rs);
		} catch (SQLException e) {
			logger.error("append an exception[{}] when execute sql[{}] with {}", e, countSql,
					boundSql.getParameterObject());
		} finally {
			for (AutoCloseable closeable : closeableList) {
				try {
					if (closeable != null)
						closeable.close();
				} catch (Exception e) {
					logger.error("append an exception[{}] when close resource[{}] ", e, closeable);
				}
			}
		}
		return 0;
	}

	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
		parameterHandler.setParameters(ps);
	}

	@Override
	public Object plugin(Object target) {
		// 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
	}

	@Override
	public void setProperties(Properties properties) {

	}

	public String getCountSql(String sql,Page page) {
		if("mysql".equals(dialect)){
			StringBuilder countSql=new StringBuilder(" select count(1) as cnt ");
			if(page.getSumParams()!=null&&page.getSumParams().size()>0){
				Iterator<String> iter=page.getSumParams().keySet().iterator();
				while(iter.hasNext()){
					String key=iter.next();
					String value= (String) page.getSumParams().get(key);
					countSql.append(" ,").append(com.manage.system.common.utils.StringUtils.isNotBlank(value)?value:"sum")
							.append("(").append(key).append(") as cnt_").append(key).append(" ");
				}
			}
			return countSql.toString()+"  from (" + sql + ") as total";
		}
		return sql;
	}

	public String getPageSql(String sql, Page page) {
		if (page.getPageSize()==-1||page.getPageSize()==-2){ // -1 -2 不统计
			return sql;
		}
		if(page.getPage()<=0){
			page.setPage(1);
		}
		if(page.getPageSize()<=0){
			page.setPageSize(20);
		}
		int startRow = (page.getPage()-1)*page.getPageSize();

		if(startRow>=page.getTotalRecord()){
			page.setPage(1);
			startRow=0;
		}
		if("mysql".equals(dialect)){
			return sql+" limit "+startRow+", "+page.getPageSize();
		}
		return sql;
	}
}
