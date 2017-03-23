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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}
	
	

}
