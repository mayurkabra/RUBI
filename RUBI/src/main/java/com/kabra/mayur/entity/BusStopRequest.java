package com.kabra.mayur.entity;

public class BusStopRequest implements DirectionStep {
	
	private BusStop busStop;
	private int walkingTime;
	private int walkingDistance;
	public BusStop getBusStop() {
		return busStop;
	}
	public void setBusStop(BusStop busStop) {
		this.busStop = busStop;
	}
	public int getWalkingTime() {
		return walkingTime;
	}
	public void setWalkingTime(int walkingTime) {
		this.walkingTime = walkingTime;
	}
	public int getWalkingDistance() {
		return walkingDistance;
	}
	public void setWalkingDistance(int walkingDistance) {
		this.walkingDistance = walkingDistance;
	}
	
	

}
