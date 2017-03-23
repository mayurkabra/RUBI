package com.kabra.mayur.entity;

public class Vehicle {
	
	private String vehicleNumber;
	private Route route;
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	@Override
	public String toString() {
		return "Vehicle [vehicleNumber=" + vehicleNumber + ", route=" + route.getTag() + "]";
	}
	/*@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Vehicle)){
			return false;
		}
		Vehicle vehicleToBeCompared = (Vehicle) obj;
		if(vehicleToBeCompared.route.getTag().equals(this.route.getTag()) && vehicleToBeCompared.vehicleNumber.equals(this.vehicleNumber)){
			return true;
		}
		return false;
	}*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((route == null) ? 0 : route.hashCode());
		result = prime * result + ((vehicleNumber == null) ? 0 : vehicleNumber.hashCode());
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
		Vehicle other = (Vehicle) obj;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		if (vehicleNumber == null) {
			if (other.vehicleNumber != null)
				return false;
		} else if (!vehicleNumber.equals(other.vehicleNumber))
			return false;
		return true;
	}
	

}
