package com.kabra.mayur.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabra.mayur.dao.BusDao;
import com.kabra.mayur.entity.BusDirection;
import com.kabra.mayur.entity.BusStop;
import com.kabra.mayur.entity.BusStopRequest;
import com.kabra.mayur.entity.Location;

@Service
public class BusService {
	
	private int NUMBER_OF_CLOSEST_BUS_STOPS = 3;
	
	@Autowired
	private BusDao busDao;
	
	public List<BusDirection> getFastestDirections(Location source, Location destination){
		List<BusStop> sourceStops = busDao.getPhysicallyClosestBusStops(source, NUMBER_OF_CLOSEST_BUS_STOPS);
		List<BusStop> destinationStops = busDao.getPhysicallyClosestBusStops(destination, NUMBER_OF_CLOSEST_BUS_STOPS);
		List<BusDirection> busDirections = busDao.getFastestBusDirectionsForGivenBusStopScenarios(sourceStops, destinationStops);
		
		return busDirections;
	}

}
