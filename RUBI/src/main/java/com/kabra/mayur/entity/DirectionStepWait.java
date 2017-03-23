package com.kabra.mayur.entity;

import java.io.Serializable;
import java.util.Date;

public class DirectionStepWait implements DirectionStep, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String atBusStop;
	private String vehicleNumber;
	private String routeName;
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

	public String getAtBusStop() {
		return atBusStop;
	}

	public void setAtBusStop(String atBusStop) {
		this.atBusStop = atBusStop;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

}
