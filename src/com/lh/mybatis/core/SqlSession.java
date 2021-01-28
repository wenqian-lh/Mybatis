/**
 * 
 */
package com.lh.mybatis.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

/**
 * @author lh
 * @data 2021年1月9日
 * Email 2944862497@qq.com
 */
public class SqlSession {
	private DBHelper db = null;
	
	public SqlSession(Map<String, String> map) {
		db = new DBHelper(map);
		
	}
	
	public <T> List<T> selectList(String sqlId, Object obj) throws Exception {
		MapperInfo mapperInfo = MyBatisConfig.getMapperInfos(sqlId);
		if (mapperInfo == null) {
			throw	new RuntimeException("没有您要执行的" + sqlId + "....");
		}
		
		String sql = mapperInfo.getSql();
		String parameterType = mapperInfo.getParameterType();
		List<String> paramsNames = mapperInfo.getParamName();
		String ResultType = mapperInfo.getResultType();
		List<Object> params = this.getParams(parameterType, paramsNames, obj);
		
		return null;
		
	}
	
	/**
	 * 更新数据的方法
	 * @param sqlId
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	public int update(String sqlId, Object obj) throws Exception {
		MapperInfo mapperInfo = MyBatisConfig.getMapperInfos(sqlId);
		if(mapperInfo == null) {
			throw	new RuntimeException("没有您要执行的" + sqlId + "....");
		}
		
		String sql = mapperInfo.getSql();
		List<String> paramsNames = mapperInfo.getParamName(); // 获取占位符属性名
		if (paramsNames == null || paramsNames.isEmpty()) {
			return db.update(sql);
		}
		
		// 说明有占位符
		String parameterType = mapperInfo.getParameterType(); // 获取用户给定的参数类型
		List<Object> params = this.getParams(parameterType, paramsNames, obj);
		return db.update(sql, params);
	}

	/**
	 * 提取用户给定的参数到map
	 * @param parameterType
	 * @param paramsNames
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private List<Object> getParams(String parameterType, List<String> paramsNames, Object obj) throws Exception {
		if (obj == null) {
			return Collections.emptyList();
		}
		if (paramsNames == null || paramsNames.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<Object> params = new ArrayList<Object>();
		if ("int".equalsIgnoreCase(parameterType) || "float".equalsIgnoreCase(parameterType) 
				|| "double".equalsIgnoreCase(parameterType) || "string".equalsIgnoreCase(parameterType)) {
			params.add(obj);
		} else if ("map".equalsIgnoreCase(parameterType)) {
			System.out.println("map ：" + paramsNames);
			Map<Integer, Object> map =  (Map<Integer, Object>) obj;			
			for(String name : paramsNames) {
				params.add(map.get(name));
			}
		} else { // 基于实体类
			Class<?> clazz = Class.forName(parameterType);
			Method[] methods = clazz.getDeclaredMethods(); // 获取这个类的所有方法
			Map<String, Method> getters = new HashMap<String, Method>();
			String methodName = null;
			for (Method md : methods) {
				methodName = md.getName(); // 获取当前方法的方法名
				if (!methodName.startsWith("get")) {
					continue;
				}
				getters.put(methodName, md);
			}
			
			// 根据参数名来获取对应的方法
			Method method = null;
			for (String name : paramsNames) {
				methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
				
				method = getters.getOrDefault(methodName, null);
				if (method == null) {
					continue;
				}
				
				params.add(method.invoke(obj)); // 如果这个方法存在， 则反向激活这个方法获取这个方法的返回值
			}
		}
		return params;
	}

}
