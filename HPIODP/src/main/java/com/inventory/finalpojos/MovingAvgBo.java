package com.inventory.finalpojos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class MovingAvgBo {
	
	@Id
	private String _id;
	@Field("PN")
	private String pn;
	@Field("PODATE")
	private String poDate;
	@Field("FCSTDATE")
	private String fcstDate;
	@Field("FCSTGENDATE")
	private String fcstGenDate;
	@Field("MVGAVG")
	private String mvgAvg;
	@Field("WEEK")
	private int week;
	
	public String get_id() {
		return _id;
	}	
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	public String getFcstDate() {
		return fcstDate;
	}
	public void setFcstDate(String fcstDate) {
		this.fcstDate = fcstDate;
	}
	public String getFcstGenDate() {
		return fcstGenDate;
	}
	public void setFcstGenDate(String fcstGenDate) {
		this.fcstGenDate = fcstGenDate;
	}
	public String getMvgAvg() {
		return mvgAvg;
	}
	public void setMvgAvg(String mvgAvg) {
		this.mvgAvg = mvgAvg;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	@Override
	public String toString() {
		return "MovingAvgBo [_id=" + _id + ", pn=" + pn + ", poDate=" + poDate + ", fcstDate=" + fcstDate
				+ ", fcstGenDate=" + fcstGenDate + ", mvgAvg=" + mvgAvg + "]";
	}
	
}
