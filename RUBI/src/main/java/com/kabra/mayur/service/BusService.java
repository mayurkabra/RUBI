package com.kabra.mayur.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Path;
import org.graphstream.ui.view.Viewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabra.mayur.cached.DataStore;
import com.kabra.mayur.dao.BusDAO;
import com.kabra.mayur.entity.DirectionSet;
import com.kabra.mayur.entity.DirectionStepBus;
import com.kabra.mayur.entity.DirectionStepWait;
import com.kabra.mayur.entity.DirectionStepWalk;
import com.kabra.mayur.entity.Location;
import com.kabra.mayur.entity.Mode;

@Service
public class BusService {
	
	private int NUMBER_OF_CLOSEST_BUS_STOPS = 3;
	
	@Autowired
	private BusDAO busDao;
	
	public List<DirectionSet> getFastestDirections(Location source, Location destination, boolean notransfer) throws Exception{
		String returnString = "";
		/*Viewer viewer = DataStore.graph.display();*/
		long startTime = new Date().getTime();
		List<DirectionSet> allOptions = new ArrayList<>();
		try {
			DataStore.lock.lock();
			DataStore.addUserNodes(source, destination, startTime);
			for (int i = 0; i < 5; i++) {
				DataStore.aStar.compute(DataStore.getUserSourceNodeName(), DataStore.getUserDestinationNodeName());//DataStore.getUserDestinationNodeName(destination));
				Path shortestPath = DataStore.aStar.getShortestPath();
				/*shortestPath.getEdgeSet().forEach(edge->{
					System.out.println(edge);
				});*/
				Iterator<Edge> edgeIterator = shortestPath.getEdgeIterator();
				DirectionSet fastestOption = new DirectionSet();
				long endTime = startTime;
				int waitingEdgeCount = 0;
				while (edgeIterator.hasNext()) {
					Edge edge = edgeIterator.next();
					Object mode = edge.getAttribute("mode");
					String from = edge.getAttribute("from");
					String to = edge.getAttribute("to");
					long fromTime = edge.getAttribute("fromTime");
					long toTime = edge.getAttribute("toTime");
					/*if(endTime<toTime){
						endTime = toTime;
					}*/
					returnString += "\n" + mode + " from " + from + " to " + to;

					if (mode.equals(Mode.WALKING)) {
						double startlat = edge.getAttribute("startlat");
						double startlon = edge.getAttribute("startlon");
						double toLat = edge.getAttribute("toLat");
						double toLon = edge.getAttribute("toLon");
						DirectionStepWalk walk = new DirectionStepWalk();
						walk.setStartStr(from);
						walk.setEndStr(to);
						walk.setStartAndEndTimes(fromTime, toTime);
						walk.setFromLat(startlat);
						walk.setFromLng(startlon);
						walk.setToLat(toLat);
						walk.setToLng(toLon);
						fastestOption.getStepByStepDirections().add(walk);
						fastestOption.setWalkingTime(toTime - fromTime + fastestOption.getWalkingTime());
					} else if (mode.equals(Mode.IN_BUS)) {
						DirectionStepBus bus = new DirectionStepBus();
						bus.setStartStr(from);
						bus.setEndStr(to);
						bus.setStartAndEndTimes(fromTime, toTime);
						fastestOption.getStepByStepDirections().add(bus);
						fastestOption.setBusRouteTime(toTime - fromTime + fastestOption.getBusRouteTime());
						fastestOption.setBusChangeCount(fastestOption.getBusChangeCount()+1);
					} else if (mode.equals(Mode.WAITING)) {
						waitingEdgeCount++;
						String routeName = edge.getAttribute("routeName");
						String vehicleNumber = edge.getAttribute("vehicleNumber");
						DirectionStepWait wait = new DirectionStepWait();
						wait.setAtBusStop(from);
						wait.setRouteName(routeName);
						wait.setVehicleNumber(vehicleNumber);
						wait.setStartAndEndTimes(fromTime, toTime);
						fastestOption.getStepByStepDirections().add(wait);
						fastestOption.setWaitingTime(toTime - fromTime + fastestOption.getWaitingTime());
						if(waitingEdgeCount == 1){
							DataStore.graph.removeEdge(edge);
						}
					}
					endTime += toTime - fromTime;
				}
				fastestOption.setBusChangeCount(waitingEdgeCount);
				fastestOption.setStartAndEndTimes(startTime, endTime);
				if(notransfer && waitingEdgeCount>1){
					i--;
					continue;
				}
				allOptions.add(fastestOption);
				if(shortestPath.getEdgeCount()==1){
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DataStore.removeUserNodes(source, destination);
			DataStore.lock.unlock();
		}
		return allOptions;
	}
	
	public List getOptionsForFilter(String route, String stop, Date from, Date to){
		return busDao.getOptionsForFilter(route, stop, from, to);
	}

	public List stopwiseChartValues(String stop, Date from, Date to) {
		return busDao.stopwiseChartValues(stop, from, to);
	}

}
