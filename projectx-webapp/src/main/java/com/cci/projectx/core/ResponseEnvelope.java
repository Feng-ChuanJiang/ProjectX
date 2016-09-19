package com.cci.projectx.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wlw.pylon.web.rest.PageInfo;
import com.wlw.pylon.web.rest.exception.RestApiError;

import java.util.ArrayList;

/**
 * The response envelope to envelope the response data and make it as the
 * standard data
 *
 * @author zhidong.she
 *
 * @param <T>
 */
@JsonInclude
public class ResponseEnvelope<T> {

	private T data;

	private boolean status;

	public ResponseEnvelope() {
		this(null, null, false);
	}

	public ResponseEnvelope(T data) {
		this(data, null, false);
	}

	public ResponseEnvelope(T data, PageInfo pageInfo, boolean status) {
		if(data==null){
			this.data=(T)new ArrayList<>();
		}else{
			this.data = data;
		}
		this.status = status;
	}


	public ResponseEnvelope(T data, boolean status) {
		this(data, null, status);
	}

	public ResponseEnvelope(RestApiError error, boolean status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public boolean isStatus() {
		return status;
	}
}
