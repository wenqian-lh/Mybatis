/**
 * 
 */
package com.lh.mybatis.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

/**
 * @author lh
 * @data 2021年1月8日
 * Email 2944862497@qq.com
 */
public class MyBatisConfig {
	private Map<String, String> dataSourceMap = new HashMap<String, String>();
	private List<String> mappers = new ArrayList<String>();
	private static Map<String, MapperInfo> mapperInfos = new HashMap<String, MapperInfo>();
	
	public MyBatisConfig(String config) {
		parseXml(config);
		
		parseMapper();
		
		/*
		 * dataSourceMap.forEach((key, val) -> { System.out.println(key + " : " + val);
		 * });
		 * 
		 * mappers.forEach(System.out::println);
		 */
		mapperInfos.forEach((key, val) -> {
			System.out.println(key + "  :  " + val);
		});
	}

	/**
	 * 解析映射文件
	 */
	private void parseMapper() {
		if (mappers == null || mappers.isEmpty()) {
			return;
		}
		
		MapperInfo mapperInfo = null;
		Document doc = null;
		String sql = null;
		String namespace = null;
		String nodeName = null;
		SAXReader read = null;
		XPath xpath = null;
		List<Element> nodes = null;
		Pattern pattern = null;
		Matcher matcher = null;
		List<String> paramsNames = null;		
		
		for(String mapper : mappers) {		
			try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(mapper)) {
				read = new SAXReader();
				doc = read.read(is);
				
				namespace = doc.getRootElement().attributeValue("namespace");
				if(namespace != null && !"".equals(namespace)) {
					namespace += ".";
				}
				
				xpath = doc.createXPath("/mapper/*");
				nodes = xpath.selectNodes(doc);
				
				for(Element el : nodes) {
					mapperInfo = new MapperInfo();
					nodeName = el.getName(); // 获取节点名称
					if ("select".equals(nodeName.toLowerCase())) {
						mapperInfo.setUpdate(false);
					}
					
					mapperInfo.setParameterType(el.attributeValue("parameterType"));
					mapperInfo.setResultType(el.attributeValue("resultType"));
					sql = el.getTextTrim();
					
					pattern = pattern.compile("[#][{]\\w+}");
					matcher = pattern.matcher(sql);
					
					paramsNames = new ArrayList<String>();
					while(matcher.find()) {
						paramsNames.add(matcher.group().replaceAll("[#{}]*", ""));
					}
					mapperInfo.setParamName(paramsNames);
					sql = matcher.replaceAll("?");
					mapperInfo.setSql(sql);
					
					mapperInfos.put(namespace + el.attributeValue("id"), mapperInfo);
				}
				
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
		}
	}

	/**
	 * 解析配置文件
	 * @param config
	 */
	@SuppressWarnings("unchecked")
	private void parseXml(String config) {
		SAXReader read = new SAXReader();	
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(config)) {
			Document doc = read.read(is);
			
			XPath xpath = doc.createXPath("//dataSource//property");
			List<Element> list = xpath.selectNodes(doc);
			
			for(Element el : list) {
				dataSourceMap.put(el.attributeValue("name"), el.attributeValue("value"));
			}
			
			// 获取映射文件
			xpath = doc.createXPath("//mappers//mapper");
			list = xpath.selectNodes(doc);
			
			for(Element el : list) {
				mappers.add(el.attributeValue("resource"));
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public Map<String, String> getDataSourceMap() {
		return dataSourceMap;
	}
	
	public List<String> mappers() {
		return mappers;
	}

	public Map<String, MapperInfo> getMapperInfos() {
		return mapperInfos;
	}
	
	public static MapperInfo getMapperInfos(String key) {
		return mapperInfos.get(key);
	}
	
	
}
