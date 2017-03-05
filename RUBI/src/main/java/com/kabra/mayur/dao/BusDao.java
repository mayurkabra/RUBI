package com.kabra.mayur.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kabra.mayur.entity.BusDirection;
import com.kabra.mayur.entity.BusStopRequest;
import com.kabra.mayur.entity.Location;

@Repository
public class BusDao {
	
	public List<BusStopRequest> getClosestBusStops(Location location, int k){
		List<BusStopRequest> orderedList = new ArrayList<>();
		
		return orderedList;
	}

	public List<BusDirection> getFastestBusDirectionsForGivenBusStopScenarios(List<BusStopRequest> sourceStops, List<BusStopRequest> destinationStops) {
		List<BusDirection> busDirections = new ArrayList<>();
		
		for(BusStopRequest sourceBusStopRequest : sourceStops){
			for(BusStopRequest destBusStopRequest : destinationStops ){
				BusDirection busDirection = this.findFastestBusOptionBetweenStopsAfterCertainTime(sourceBusStopRequest, destBusStopRequest);
				busDirections.add(busDirection);
			}
		}
		
		return busDirections;
	}

	private BusDirection findFastestBusOptionBetweenStopsAfterCertainTime(BusStopRequest sourceBusStopRequest, BusStopRequest destBusStopRequest) {
		BusDirection busDirection = new BusDirection();
		return busDirection;
	}

}
