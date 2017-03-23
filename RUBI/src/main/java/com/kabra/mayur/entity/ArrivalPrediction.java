package com.kabra.mayur.entity;

public class ArrivalPrediction implements Comparable<ArrivalPrediction>, Cloneable {
	
	private Long arrivalEpochTime;
	private BusStop busStop;
	private Vehicle vehicle;
	private Route route;
	public ArrivalPrediction(Long arrivalEpochTime, BusStop busStop, Vehicle vehicle, Route route) {
		super();
		this.arrivalEpochTime = arrivalEpochTime;
		this.busStop = busStop;
		this.vehicle = vehicle;
		this.route = route;
	}
	public long getArrivalEpochTime() {
		return arrivalEpochTime;
	}
	public void setArrivalEpochTime(long arrivalEpochTime) {
		this.arrivalEpochTime = arrivalEpochTime;
	}
	public BusStop getBusStop() {
		return busStop;
	}
	public void setBusStop(BusStop busStop) {
		this.busStop = busStop;
	}
	@Override
	public int compareTo(ArrivalPrediction o) {
		return arrivalEpochTime.compareTo(o.arrivalEpochTime);
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public void setArrivalEpochTime(Long arrivalEpochTime) {
		this.arrivalEpochTime = arrivalEpochTime;
	}
	
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	@Override
	public String toString() {
		return "ArrivalPrediction [arrivalEpochTime=" + arrivalEpochTime + ", busStop=" + busStop.tag + ", vehicle=" + vehicle.getVehicleNumber() + ", route=" + route.getTag() + "]";
	}
	
	
	

}
