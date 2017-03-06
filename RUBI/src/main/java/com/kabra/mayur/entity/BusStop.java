package com.kabra.mayur.entity;

import java.util.HashSet;
import java.util.Set;

public class BusStop {
	
	String tag;
	String title;
	Location location;
	Set<Route> routesServed;
	
	public BusStop() {
		routesServed = new HashSet<>();
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Set<Route> getRoutesServed() {
		return routesServed;
	}
	public void setRoutesServed(Set<Route> routesServed) {
		this.routesServed = routesServed;
	}
	public void addRoute(Route route){
		this.routesServed.add(route);
		route.addBusStop(this);
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
	@Override
	public String toString() {
		return "BusStop [tag=" + tag + ", title=" + title + ", location=" + location + ", routesServed=" + routesServed + "]";
	}
	
	

}
