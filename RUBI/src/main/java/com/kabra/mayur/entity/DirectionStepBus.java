package com.kabra.mayur.entity;

import java.io.Serializable;
import java.util.Date;

public class DirectionStepBus implements DirectionStep, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startStr;
	private String endStr;

	private Date startTime;
	private Date endTime;
	private long totalTime;

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public long getTotalTime() {
		return totalTime;
	}
	
	public void setStartAndEndTimes(long startEpoch, long endEpoch){
		this.startTime = new Date(startEpoch);
		this.endTime = new Date(endEpoch);
		this.totalTime = endEpoch - startEpoch;
	}

	public String getStartStr() {
		return startStr;
	}

	public void setStartStr(String startStr) {
		this.startStr = startStr;
	}

	public String getEndStr() {
		return endStr;
	}

	public void setEndStr(String endStr) {
		this.endStr = endStr;
	}

}
