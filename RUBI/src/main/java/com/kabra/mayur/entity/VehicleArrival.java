package com.kabra.mayur.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*@Table(name="VEHICLE_ARRIVAL")*/
@Entity
public class VehicleArrival implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*@Column(name="vehicle_arrival_id")*/
	private long vehicleArrivalId;
	
	public VehicleArrival() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*@Column(name="epoch_time")*/
	private long epochTime;
	
	/*@Column(name="bus_number")*/
	private String busNumber;
	
	/*@Column(name="route")*/
	private String route;
	
	/*@Column(name="stop")*/
	private String stop;
	
	/*@Column(name="date")*/
	private Date date;
	
	/*@Column(name="month")*/
	private int month;
	
	/*@Column(name="day_of_week")*/
	private int dayOfWeek;
	
	/*@Column(name="hour_of_day")*/
	private int hourOfDay;

	@Id
	@GeneratedValue
	public Long getVehicleArrivalId() {
		return vehicleArrivalId;
	}

	public void setVehicleArrivalId(long vehicleArrivalId) {
		this.vehicleArrivalId = vehicleArrivalId;
	}

	public long getEpochTime() {
		return epochTime;
	}

	public void setEpochTime(long epochTime) {
		this.epochTime = epochTime;
	}

	public String getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getHourOfDay() {
		return hourOfDay;
	}

	public void setHourOfDay(int hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	public VehicleArrival(long epochTime, String busNumber, String route, String stop,
			Date date, int month, int dayOfWeek, int hourOfDay) {
		super();
		this.epochTime = epochTime;
		this.busNumber = busNumber;
		this.route = route;
		this.stop = stop;
		this.date = date;
		this.month = month;
		this.dayOfWeek = dayOfWeek;
		this.hourOfDay = hourOfDay;
	}
	
	

}
