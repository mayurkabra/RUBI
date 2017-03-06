package com.kabra.mayur.cached;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.kabra.mayur.entity.BusStop;
import com.kabra.mayur.entity.Location;
import com.kabra.mayur.entity.Route;
import com.kabra.mayur.utility.HTTPListener;

public class DataStore {
	public static String NEXT_BUS_URL = "http://webservices.nextbus.com/service/publicXMLFeed?a=rutgers&command=routeConfig";
	public static Document document = null;
	//public static List<BusStop> busStops = null;
	public static List<Route> routes = null;
	public static Map<String, BusStop> busStopMap = null;
	//private static Set<String> existingRouteTags = new HashSet<>();
	
	public static void updateCache (){
		if(document==null){
			document=HTTPListener.getDocument(NEXT_BUS_URL);
			routes = new ArrayList<>();
			busStopMap = new HashMap<>();
			NodeList routeNodes = document.getElementsByTagName("route");
			for(int i = 0 ; i < routeNodes.getLength() ; i++){
				Node routeNode = routeNodes.item(i);
				Element routeElement = (Element) routeNode;
				Route route = new Route();
				route.setTag(routeElement.getAttribute("tag"));
				route.setTitle(routeElement.getAttribute("title"));
				routes.add(route);
				
				NodeList stopNodes = routeElement.getElementsByTagName("stop");
				for(int j = 0 ; j < stopNodes.getLength() ; j++){
					Node stopNode = stopNodes.item(j);
					Element stopElement = (Element) stopNode;
					String busStopTag = stopElement.getAttribute("tag");
					if (!busStopMap.containsKey(busStopTag)) {
						BusStop busStop = new BusStop();
						busStop.setTag(busStopTag);
						busStop.setTitle(stopElement.getAttribute("title"));
						double latitude = Double.parseDouble(stopElement.getAttribute("lat"));
						double longitude = Double.parseDouble(stopElement.getAttribute("lon"));
						Location location = new Location(latitude, longitude);
						busStop.setLocation(location);
						busStop.addRoute(route);
						busStopMap.put(busStopTag, busStop);
					} else{
						busStopMap.get(busStopTag).addRoute(route);
					}
				}
			}
		}
	}

}
