package com.kabra.mayur.entity;

import java.util.HashSet;
import java.util.Set;

public class Route {
	
	private String tag;
	private String title;
	private Set<BusStop> busStops;
	
	public Route() {
		super();
		busStops = new HashSet<>(); 
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<BusStop> getBusStops() {
		return busStops;
	}

	public void setBusStops(Set<BusStop> busStops) {
		this.busStops = busStops;
	}
	
	public void addBusStop(BusStop busStop){
		this.busStops.add(busStop);
	}

	@Override
	public String toString() {
		StringBuffer busStopsStr = new StringBuffer();
		for (BusStop busStop : this.busStops) {
			busStopsStr.append(busStop.tag+",");
		}
		return "Route [tag=" + tag + ", title=" + title + "busStops=" + busStopsStr.toString() + "]";
	}
	
	public String getQueryForPredictions(){
		StringBuffer busStopsStr = new StringBuffer();
		for (BusStop busStop : this.busStops) {
			busStopsStr.append("&stops="+this.tag+"||"+busStop.tag);
		}
		return busStopsStr.toString();
	}

}
