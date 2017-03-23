package com.kabra.mayur.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kabra.mayur.cached.DataStore;
import com.kabra.mayur.entity.DirectionStepWait;
import com.kabra.mayur.entity.BusStop;
import com.kabra.mayur.entity.BusStopRequest;
import com.kabra.mayur.entity.Location;
import com.kabra.mayur.entity.Route;

@Repository
public class BusDAO {
	
	public List<BusStop> getPhysicallyClosestBusStops(Location location, int k){
		//List<BusStopRequest> orderedList = new ArrayList<>();
		List<BusStop> busStops = new ArrayList<>(DataStore.busStopMap.values());
		BusStop dummyBusStop = new BusStop();
		dummyBusStop.setLocation(location);
		Collections.sort(busStops, sortBusStopComparator(dummyBusStop));
		return busStops;
	}

	private Comparator<BusStop> sortBusStopComparator(BusStop dummyBusStop) {
		return new Comparator<BusStop>() {
			@Override
			public int compare(BusStop o1, BusStop o2) {
				double ds0 = o1.getLocation().getPoint2d().distanceSq(dummyBusStop.getLocation().getPoint2d());
                double ds1 = o2.getLocation().getPoint2d().distanceSq(dummyBusStop.getLocation().getPoint2d());
                return Double.compare(ds0, ds1);
			}
		};
	}

	public List<DirectionStepWait> getFastestBusDirectionsForGivenBusStopScenarios(List<BusStop> sourceStops, List<BusStop> destinationStops) {
		List<DirectionStepWait> busDirections = new ArrayList<>();
		List<BusStopRequest> sourceStopsTime = new ArrayList<>();
		List<BusStopRequest> destinationStopsTime = new ArrayList<>();
		populateTimeWiseClosestBusStops(sourceStops, destinationStops, sourceStopsTime, destinationStopsTime);
		for(BusStopRequest sourceBusStopRequest : sourceStopsTime){
			for(BusStopRequest destBusStopRequest : destinationStopsTime ){
				DirectionStepWait busDirection = this.findFastestBusOptionBetweenStopsAfterCertainTime(sourceBusStopRequest, destBusStopRequest);
				busDirections.add(busDirection);
			}
		}
		
		return busDirections;
	}

	private void populateTimeWiseClosestBusStops(List<BusStop> sourceStops, List<BusStop> destinationStops,
			List<BusStopRequest> sourceStopsTime, List<BusStopRequest> destinationStopsTime) {
		Map<Route, List<BusStop>> routeToStop = new HashMap<>();
		int i = 0;
		/*while(i<sourceStops.size()){
			BusStop s = sourceStops.get(i);
			BusStop d = destinationStops.get(i);
		}*/
	}

	private DirectionStepWait findFastestBusOptionBetweenStopsAfterCertainTime(BusStopRequest sourceBusStopRequest, BusStopRequest destBusStopRequest) {
		DirectionStepWait busDirection = new DirectionStepWait();
		return busDirection;
	}

}
