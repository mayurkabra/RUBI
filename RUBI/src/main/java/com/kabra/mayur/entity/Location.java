package com.kabra.mayur.entity;

import java.awt.geom.Point2D;

public class Location {
	
	/*private double latitude;
	private double longitude;*/
	private Point2D point2d;
	
	public Location(double latitude, double longitude) {
		super();
		/*this.latitude = latitude;
		this.longitude = longitude;*/
		point2d = new Point2D.Double(latitude, longitude);
	}
	/*public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}*/
	
	@Override
	public String toString() {
		return "Location [latitude=" + point2d.getX() + ", longitude=" + point2d.getY() + "]";
	}

	public Point2D getPoint2d() {
		return point2d;
	}

	public void setPoint2d(Point2D point2d) {
		this.point2d = point2d;
	}
	
	

}
