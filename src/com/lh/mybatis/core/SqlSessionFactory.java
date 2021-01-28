/**
 * 
 */
package com.lh.mybatis.core;

/**
 * @author lh
 * @data 2021年1月9日
 * Email 2944862497@qq.com
 */
public class SqlSessionFactory {
	
	private MyBatisConfig mybatisConfig;
	
	public SqlSessionFactory(MyBatisConfig mybatisConfig) {
		this.mybatisConfig = mybatisConfig;
	}
	
	public SqlSession openSession() {
		return new SqlSession(mybatisConfig.getDataSourceMap());
	}

}
