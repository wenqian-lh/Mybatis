/**
 * 
 */
package com.lh.mybatis.core;

import java.util.List;

/**
 * @author lh
 * @data 2021年1月9日
 * Email 2944862497@qq.com
 */
public class MapperInfo {
	
	private String parameterType;
	private String resultType;
	private String sql;
	private List<String> paramName;
	private boolean update = true;
	@Override
	public String toString() {
		return "MapperInfo [parameterType=" + parameterType + ", resultType=" + resultType + ", sql=" + sql
				+ ", paramName=" + paramName + ", update=" + update + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paramName == null) ? 0 : paramName.hashCode());
		result = prime * result + ((parameterType == null) ? 0 : parameterType.hashCode());
		result = prime * result + ((resultType == null) ? 0 : resultType.hashCode());
		result = prime * result + ((sql == null) ? 0 : sql.hashCode());
		result = prime * result + (update ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapperInfo other = (MapperInfo) obj;
		if (paramName == null) {
			if (other.paramName != null)
				return false;
		} else if (!paramName.equals(other.paramName))
			return false;
		if (parameterType == null) {
			if (other.parameterType != null)
				return false;
		} else if (!parameterType.equals(other.parameterType))
			return false;
		if (resultType == null) {
			if (other.resultType != null)
				return false;
		} else if (!resultType.equals(other.resultType))
			return false;
		if (sql == null) {
			if (other.sql != null)
				return false;
		} else if (!sql.equals(other.sql))
			return false;
		if (update != other.update)
			return false;
		return true;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<String> getParamName() {
		return paramName;
	}
	public void setParamName(List<String> paramName) {
		this.paramName = paramName;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
	
	

}
