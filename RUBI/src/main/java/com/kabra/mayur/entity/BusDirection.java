package com.kabra.mayur.entity;

public class BusDirection implements DirectionStep {
	
	BusStopRequest source;
	BusStopRequest destination;
	Route route;
	String busId;
	long originatingTime;
	long reachingTime;
	long enRouteTime;
	
	public BusDirection() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public BusStopRequest getSource() {
		return source;
	}


	public void setSource(BusStopRequest source) {
		this.source = source;
	}


	public BusStopRequest getDestination() {
		return destination;
	}


	public void setDestination(BusStopRequest destination) {
		this.destination = destination;
	}


	public long getOriginatingTime() {
		return originatingTime;
	}
	public void setOriginatingTime(long originatingTime) {
		this.originatingTime = originatingTime;
	}
	public long getReachingTime() {
		return reachingTime;
	}
	public void setReachingTime(long reachingTime) {
		this.reachingTime = reachingTime;
	}
	public long getEnRouteTime() {
		return enRouteTime;
	}
	public void setEnRouteTime(long enRouteTime) {
		this.enRouteTime = enRouteTime;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}
	
	

}
