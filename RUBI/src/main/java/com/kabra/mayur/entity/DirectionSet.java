package com.kabra.mayur.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DirectionSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long walkingTime;
	public int getBusChangeCount() {
		return busChangeCount;
	}

	public void setBusChangeCount(int busChangeCount) {
		this.busChangeCount = busChangeCount;
	}

	private long waitingTime;
	private long busRouteTime;
	private int busChangeCount;
	
	List<DirectionStep> stepByStepDirections;

	public DirectionSet() {
		stepByStepDirections = new ArrayList<>();
	}

	public long getWalkingTime() {
		return walkingTime;
	}

	public void setWalkingTime(long walkingTime) {
		this.walkingTime = walkingTime;
	}

	public long getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(long waitingTime) {
		this.waitingTime = waitingTime;
	}

	public long getBusRouteTime() {
		return busRouteTime;
	}

	public void setBusRouteTime(long busRouteTime) {
		this.busRouteTime = busRouteTime;
	}

	public List<DirectionStep> getStepByStepDirections() {
		return stepByStepDirections;
	}

	public void setStepByStepDirections(List<DirectionStep> stepByStepDirections) {
		this.stepByStepDirections = stepByStepDirections;
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
	
	

}
