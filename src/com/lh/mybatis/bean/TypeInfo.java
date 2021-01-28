package com.lh.mybatis.bean;

import java.io.Serializable;

/**
 * 父分类信息
 * @author admin
 *
 */
public class TypeInfo implements Serializable {
	private static final long serialVersionUID = 5217438297571157416L;
	private Integer tid;
	private String tname;
	
	@Override
	public String toString() {
		return "TypeInfo [tid=" + tid + ", tname=" + tname + "]";
	}
	
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tid == null) ? 0 : tid.hashCode());
		result = prime * result + ((tname == null) ? 0 : tname.hashCode());
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
		TypeInfo other = (TypeInfo) obj;
		if (tid == null) {
			if (other.tid != null)
				return false;
		} else if (!tid.equals(other.tid))
			return false;
		if (tname == null) {
			if (other.tname != null)
				return false;
		} else if (!tname.equals(other.tname))
			return false;
		return true;
	}

	/**
	 * @param tname
	 */
	public TypeInfo(String tname) {
		super();
		this.tname = tname;
	}
	
	
}
