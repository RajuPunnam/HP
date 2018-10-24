package com.io.pojos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="MASTERSKUAVBOM")
public class MasterSkuAvBomBo {

	@Id
	private String id;
	@Field("date")
	private String date;
	@Field("sku")
	private String sku;
	@Field("av")
	private String av;
	@Field("family")
	private String family;

	public String getId() {
		if(id!=null)return id.trim();
		else
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((av == null) ? 0 : av.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MasterSkuAvBomBo other = (MasterSkuAvBomBo) obj;
		if (av == null) {
			if (other.av != null)
				return false;
		} else if (!av.equals(other.av))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		return true;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		if(date!=null)return date.trim();
		else
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSku() {
		if(sku!=null)return sku.trim();
		else
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getAv() {
		if(av!=null)return av.trim();
		else
		return av;
	}

	public void setAv(String av) {
		this.av = av;
	}

	public String getFamily() {
		if(family!=null)return family.trim();
		else
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	@Override
	public String toString() {
		return "MasterSkuAvBomBo [id=" + id + ", date=" + date + ", sku=" + sku + ", av=" + av + ", family=" + family
				+ "]";
	}

}
