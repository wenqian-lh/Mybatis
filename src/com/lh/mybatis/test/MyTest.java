/**
 * 
 */
package com.lh.mybatis.test;

import org.junit.Test;

import com.lh.mybatis.bean.TypeInfo;
import com.lh.mybatis.core.MyBatisConfig;
import com.lh.mybatis.core.SqlSession;
import com.lh.mybatis.core.SqlSessionFactory;

/**
 * @author lh
 * @data 2021年1月9日
 * Email 2944862497@qq.com
 */
public class MyTest {
	
	@Test
	public void test() throws Exception {
		SqlSessionFactory factory = new SqlSessionFactory( new MyBatisConfig("mybatis-config.xml"));
		SqlSession session = factory.openSession();
		int result = session.update("GoodsTypeMapper.add1", "突然袭击");
		System.out.println(result);
	}

}
