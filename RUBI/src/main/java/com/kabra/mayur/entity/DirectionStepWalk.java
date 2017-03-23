package com.kabra.mayur.entity;

import java.io.Serializable;
import java.util.Date;

public class DirectionStepWalk implements DirectionStep, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double fromLat;
	private double toLat;
	private double fromLng;
	private double toLng;
	
	public double getFromLat() {
		return fromLat;
	}

	public void setFromLat(double fromLat) {
		this.fromLat = fromLat;
	}
	
	public double getToLat() {
		return toLat;
	}

	public void setToLat(double toLat) {
		this.toLat = toLat;
	}

	public double getFromLng() {
		return fromLng;
	}

	public void setFromLng(double fromLng) {
		this.fromLng = fromLng;
	}

	public double getToLng() {
		return toLng;
	}

	public void setToLng(double toLng) {
		this.toLng = toLng;
	}

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
	
	private String startStr;
	private String endStr;

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
