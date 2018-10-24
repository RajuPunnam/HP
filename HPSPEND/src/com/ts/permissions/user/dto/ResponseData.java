package com.ts.permissions.user.dto;

import java.io.Serializable;

public class ResponseData implements Serializable {

	private String msg;
	private boolean status;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
