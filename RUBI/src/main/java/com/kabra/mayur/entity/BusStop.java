package com.kabra.mayur.entity;

import java.util.HashSet;
import java.util.Set;

public class BusStop {
	
	String name;
	Location location;
	Set<Route> routesServed;
	
	public BusStop(String name, Location location) {
		super();
		this.name = name;
		this.location = location;
		this.routesServed = new HashSet<>();
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Route> getRoutesServed() {
		return routesServed;
	}
	public void setRoutesServed(Set<Route> routesServed) {
		this.routesServed = routesServed;
	}
	public void addRoute(Route route){
		this.routesServed.add(route);
	}
	
	

}
