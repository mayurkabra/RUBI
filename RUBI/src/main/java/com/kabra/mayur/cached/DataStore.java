package com.kabra.mayur.cached;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.graphstream.algorithm.AStar;
import org.graphstream.graph.Edge;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.kabra.mayur.entity.ArrivalPrediction;
import com.kabra.mayur.entity.BusStop;
import com.kabra.mayur.entity.Location;
import com.kabra.mayur.entity.Mode;
import com.kabra.mayur.entity.Route;
import com.kabra.mayur.entity.Vehicle;
import com.kabra.mayur.utility.HTTPListener;

public class DataStore {
	private static final String GRAPH_EDGE_PRE_NAME_WAIT = "EDGE_WAIT";
	private static final String GRAPH_NODE_PRE_NAME_WAIT = "NODE_WAIT";
	private static final String GRAPH_EDGE_PRE_NAME_PREDICTION = "EDGE_PRED";
	public static final String GRAPH_NODE_NAME_SEPARATOR = "-=-";
	public static final String GRAPH_NODE_PRE_NAME_BUS_STOP = "NODE_BUS_STOP";
	private static final String GRAPH_NODE_PRE_NAME_USER_SOURCE = "USER_SOURCE";
	public static final String BUS_STOPS_GRAPH_ID = "BusStopsGraph";
	public static final String NEXT_BUS_URL = "http://webservices.nextbus.com/service/publicXMLFeed?a=rutgers&command=routeConfig";
	public static final String PREDICTIONS_FOR_MULTIPLE_BUS_STOPS = "http://webservices.nextbus.com/service/publicXMLFeed?a=rutgers&command=predictionsForMultiStops";
	public static final String COMPLETED_PREDICTIONS_FOR_MULTIPLE_BUS_STOPS = "http://webservices.nextbus.com/service/publicXMLFeed?a=rutgers&command=predictionsForMultiStops&stops=kearney||njit&stops=kearney||kearduke&stops=kearney||kearberg&stops=kearney||harrpass&stops=kearney||boydhall&stops=kearney||shoplite&stops=kearney||kearquin&stops=kearney||kmart&stops=kearney||frodclev&stops=kearney||kearmidl&stops=penn||mark180&stops=penn||njit&stops=penn||bergbldg&stops=penn||hosp&stops=penn||pennstat&stops=penn||blumhall&stops=penn||pennstat_nt&stops=penn||dentschl&stops=penn||icph&stops=penn||medischo&stops=pennexpr||hosp&stops=pennexpr||pennstat&stops=pennexpr||medischo&stops=mdntpenn||boyd&stops=mdntpenn||pennback&stops=connect||boydhall_c&stops=connect||washpark&stops=connect||icph_e&stops=connect||physplan&stops=connect||njit&stops=connect||univnort&stops=connect||ecc&stops=connect||broad&stops=connect||bergbuil&stops=connect||medischo&stops=connect||clj&stops=a||rutgerss_a&stops=a||hillw&stops=a||stuactcntr&stops=a||werblinm&stops=a||libofsciw&stops=a||science&stops=a||busch_a&stops=a||werblinback&stops=a||scott&stops=a||stadium_a&stops=a||buells&stops=a||lot48a&stops=a||buschse&stops=b||libofsci&stops=b||hillw&stops=b||beck&stops=b||quads&stops=b||science&stops=b||busch_a&stops=b||buschse&stops=b||werblinback&stops=b||livingston_a&stops=c||hillw&stops=c||stadium_a&stops=c||allison&stops=c||hilln&stops=c||werblinback&stops=ee||rutgerss_a&stops=ee||biel&stops=ee||foodsci&stops=ee||stuactcntr&stops=ee||gibbons&stops=ee||rockoff&stops=ee||traine&stops=ee||lipman&stops=ee||pubsafs&stops=ee||college_a&stops=ee||patersonn&stops=ee||patersons&stops=ee||katzenbach&stops=ee||scott&stops=ee||pubsafn&stops=ee||henders&stops=ee||zimmerli&stops=ee||redoak_a&stops=ee||liberty&stops=ee||trainn_a&stops=f||rutgerss_a&stops=f||katzenbach&stops=f||biel&stops=f||foodsci&stops=f||stuactcntr&stops=f||redoak&stops=f||gibbons&stops=f||lipman&stops=f||scott&stops=f||pubsafs&stops=f||henders&stops=f||college_a&stops=h||rutgerss_a&stops=h||libofsci&stops=h||stuactcntr&stops=h||werblinm&stops=h||traine&stops=h||davidson&stops=h||scott&stops=h||stadium_a&stops=h||buel&stops=h||busch_a&stops=h||allison&stops=h||hilln&stops=lx||rutgerss_a&stops=lx||stuactcntr&stops=lx||beck&stops=lx||quads&stops=lx||traine&stops=lx||scott&stops=lx||livingston_a&stops=rexb||hillw&stops=rexb||allison_a&stops=rexb||lipman&stops=rexb||pubsafs&stops=rexb||rockhall&stops=rexb||redoak_a&stops=rexb||college_a&stops=rexb||hilln&stops=rexb||newstree&stops=rexl||beck&stops=rexl||lipman&stops=rexl||pubsafs&stops=rexl||rockhall&stops=rexl||redoak_a&stops=rexl||college_a&stops=rexl||newstree&stops=rexl||livingston_a&stops=s||biel&stops=s||stuactcntrs&stops=s||libofsciw&stops=s||lipman&stops=s||pubsafs&stops=s||college_a&stops=s||werblinback&stops=s||patersonn&stops=s||quads&stops=s||pubsafn&stops=s||zimmerli_2&stops=s||buschse&stops=s||trainn_a&stops=s||traine_a&stops=s||rutgerss_a&stops=s||hillw&stops=s||stuactcntr&stops=s||foodsci&stops=s||gibbons&stops=s||science&stops=s||busch_a&stops=s||livingston_a&stops=s||katzenbach&stops=s||beck&stops=s||scott&stops=s||henders&stops=s||redoak_a&stops=s||lot48a&stops=s||liberty&stops=s||stuactcntrn_2&stops=w1||rutgerss_a&stops=w1||traine_a&stops=w1||colony&stops=w1||scott&stops=w1||nursscho&stops=w2||rutgerss_a&stops=w2||traine_a&stops=w2||stuactcntr&stops=w2||colony&stops=w2||trainn&stops=w2||nursscho&stops=w2||zimmerli&stops=wknd1||livingston&stops=wknd1||rutgerss&stops=wknd1||biel&stops=wknd1||stuactcntrs&stops=wknd1||traine&stops=wknd1||libofsciw&stops=wknd1||lipman&stops=wknd1||pubsafs&stops=wknd1||busch&stops=wknd1||werblinback&stops=wknd1||patersonn&stops=wknd1||quads&stops=wknd1||pubsafn&stops=wknd1||zimmerli_2&stops=wknd1||buschse&stops=wknd1||college&stops=wknd1||hillw&stops=wknd1||stuactcntr&stops=wknd1||foodsci&stops=wknd1||gibbons&stops=wknd1||science&stops=wknd1||trainn&stops=wknd1||katzenbach&stops=wknd1||beck&stops=wknd1||redoak&stops=wknd1||scott&stops=wknd1||henders&stops=wknd1||lot48a&stops=wknd1||liberty&stops=wknd1||stuactcntrn_2&stops=wknd2||livingston&stops=wknd2||rutgerss&stops=wknd2||libofsci&stops=wknd2||biel&stops=wknd2||foodsci&stops=wknd2||gibbons&stops=wknd2||rockoff&stops=wknd2||traine&stops=wknd2||lipman&stops=wknd2||pubsafs&stops=wknd2||stuactcntrn&stops=wknd2||busch&stops=wknd2||hilln&stops=wknd2||patersons&stops=wknd2||katzenbach&stops=wknd2||redoak&stops=wknd2||beck&stops=wknd2||quads&stops=wknd2||davidson&stops=wknd2||scott&stops=wknd2||henders&stops=wknd2||college&stops=wknd2||allison&stops=rbhs||clinacad&stops=rbhs||pischoes&stops=housing||walmart&stops=housing||shoprite&stops=housing||robecent&stops=housing||kmart";
	//public static final String COMPLETED_PREDICTIONS_FOR_MULTIPLE_BUS_STOPS = "http://webservices.nextbus.com/service/publicXMLFeed?a=rutgers&command=predictionsForMultiStops&stops=a||hillw&stops=a||scott";
	//public static final String COMPLETED_PREDICTIONS_FOR_MULTIPLE_BUS_STOPS = "http://webservices.nextbus.com/service/publicXMLFeed?a=rutgers&command=predictionsForMultiStops&stops=ee||rutgerss_a&stops=ee||biel&stops=ee||foodsci&stops=ee||stuactcntr&stops=ee||gibbons&stops=ee||rockoff&stops=ee||traine&stops=ee||lipman&stops=ee||pubsafs&stops=ee||college_a&stops=ee||patersonn&stops=ee||patersons&stops=ee||katzenbach&stops=ee||scott&stops=ee||pubsafn&stops=ee||henders&stops=ee||zimmerli&stops=ee||redoak_a&stops=ee||liberty&stops=ee||trainn_a&stops=h||rutgerss_a&stops=h||libofsci&stops=h||stuactcntr&stops=h||werblinm&stops=h||traine&stops=h||davidson&stops=h||scott&stops=h||stadium_a&stops=h||buel&stops=h||busch_a&stops=h||allison&stops=h||hilln";
	//public static final String COMPLETED_PREDICTIONS_FOR_MULTIPLE_BUS_STOPS = "http://webservices.nextbus.com/service/publicXMLFeed?a=rutgers&command=predictionsForMultiStops&stops=a||rutgerss_a&stops=a||hillw&stops=a||stuactcntr&stops=a||werblinm&stops=a||libofsciw&stops=a||science&stops=a||busch_a&stops=a||werblinback&stops=a||scott&stops=a||stadium_a&stops=a||buells&stops=a||lot48a&stops=a||buschse";
	public static final String GOOGLE_DISTANCE_MATRIX = "https://maps.googleapis.com/maps/api/distancematrix/json?key=AIzaSyBfl7YOLYDHwoQf-gOl3PZX8JHArgycGq4&mode=walking&";
	private static final String GRAPH_EDGE_PRE_NAME_WALKING = "NODE_WALKING";
	private static final String GRAPH_NODE_PRE_NAME_USER_DESTINATION = "USER_DESTINATION";
	
	public static Document document = null;
	public static Document predictionsDocument = null;
	//public static List<Route> routes = null;
	public static Map<String, Route> routesMap = null;
	public static Map<String, BusStop> busStopMap = null;
	public static Graph graph = new MultiGraph(BUS_STOPS_GRAPH_ID);
	public static AStar aStar = new AStar(graph);
	public static Map<Vehicle, TreeSet<ArrivalPrediction>> predictionMap = new HashMap<>();
	public static Map<String, HashSet<ArrivalPrediction>> stopPredictionsMap = new HashMap<>();
	public static Set<String> temporaryNodes = new HashSet<>();
	
	public static void updateCache (){
		if(document==null){
			document=HTTPListener.getDocument(NEXT_BUS_URL);
			//routes = new ArrayList<>();
			routesMap = new HashMap<>();
			busStopMap = new HashMap<>();
			NodeList routeNodes = document.getElementsByTagName("route");
			for(int i = 0 ; i < routeNodes.getLength() ; i++){
				Element routeElement = getNodeAsElement(routeNodes, i);
				Route route = new Route();
				route.setTag(routeElement.getAttribute("tag"));
				route.setTitle(routeElement.getAttribute("title"));
				//routes.add(route);
				routesMap.put(route.getTag(), route);
				
				NodeList stopNodes = routeElement.getElementsByTagName("stop");
				for(int j = 0 ; j < stopNodes.getLength() ; j++){
					Element stopElement = getNodeAsElement(stopNodes, j);
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
						org.graphstream.graph.Node busStopNode = graph.addNode(getGraphNodeNameForBusStopTag(busStopTag));
						busStopNode.setAttribute("x", busStop.getLocation().getPoint2d().getX());
						busStopNode.setAttribute("y", busStop.getLocation().getPoint2d().getY());
					} else{
						busStopMap.get(busStopTag).addRoute(route);
					}
				}
			}
		}
	}
	
	public static void updateConnections(Map<String, Long> interestedBusStopsMap){
		try {
			updateCache();
			predictionsDocument = HTTPListener.getDocument(COMPLETED_PREDICTIONS_FOR_MULTIPLE_BUS_STOPS);
			NodeList predictionsNodes = predictionsDocument.getElementsByTagName("predictions");
			stopPredictionsMap = new HashMap<>();
			for(int i = 0 ; i < predictionsNodes.getLength() ; i++){
				Element predictionsElement = getNodeAsElement(predictionsNodes, i);
				NodeList directions = predictionsElement.getElementsByTagName("direction");
				String busStopTag = predictionsElement.getAttribute("stopTag");
				String routeTag = predictionsElement.getAttribute("routeTag");
				Route route = routesMap.get(routeTag);
				for(int j = 0 ; j < directions.getLength() ; j++){
					Element directionElement = getNodeAsElement(directions, j);
					NodeList predictionList = directionElement.getElementsByTagName("prediction");
					for(int k = 0 ; k < predictionList.getLength() ; k++){
						Element prediction = getNodeAsElement(predictionList, k);
						String vehicleNumber = prediction.getAttribute("vehicle");
						Vehicle vehicle = new Vehicle();
						vehicle.setRoute(route);
						vehicle.setVehicleNumber(vehicleNumber);
						String arrivalEpochTime = prediction.getAttribute("epochTime");
						String arrivalSeconds = prediction.getAttribute("seconds");
						if(!predictionMap.containsKey(vehicle)){
							predictionMap.put(vehicle, new TreeSet<>());
						}
						ArrivalPrediction arrivalPrediction = new ArrivalPrediction(Long.parseLong(arrivalEpochTime), busStopMap.get(busStopTag), vehicle, route);
						predictionMap.get(vehicle).add(arrivalPrediction);
						if(!stopPredictionsMap.containsKey(busStopTag)){
							stopPredictionsMap.put(busStopTag , new HashSet<>());
						}
						stopPredictionsMap.get(busStopTag).add(arrivalPrediction);
					}
				}
			}
			System.out.println(predictionMap);
			//eraseTemporaryNodes();
			for (Entry<String, Long> entry : interestedBusStopsMap.entrySet()) {
				createNewNodesAndEdgesForGivenStop(entry.getValue(), entry.getKey());
			}
			System.out.println(predictionMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void eraseTemporaryNodes() {
		for(String waitNodeName : temporaryNodes){
			graph.removeNode(waitNodeName);
			if(waitNodeName.startsWith("NODE_BUS_STOP")){
				busStopMap.remove(waitNodeName.split(GRAPH_NODE_NAME_SEPARATOR)[1]);
			}
		}
		temporaryNodes = new HashSet<>();
		predictionMap = new HashMap<>();
		stopPredictionsMap = new HashMap<>();
	}

	private static void createNewNodesAndEdgesForGivenStop(long startTime, String stop) {
		HashSet<ArrivalPrediction> predictionsForStop = stopPredictionsMap.get(stop);
		Set<String> avoidOverlaps = new HashSet<>();
		if(predictionsForStop != null){
			for (ArrivalPrediction arrivalPrediction : predictionsForStop) {
				if(arrivalPrediction.getArrivalEpochTime() > startTime){
					avoidOverlaps = new HashSet<>();
					String waitNode = getWaitNodeNameForArrivalPrediction(arrivalPrediction);
					graph.addNode(waitNode);
					temporaryNodes.add(waitNode);
					Edge edge = graph.addEdge(getWaitEdgeNameForGivenArrivalPrediction(arrivalPrediction), getGraphNodeNameForBusStopTag(stop), waitNode, true);
					edge.addAttribute("mode", Mode.WAITING);
					edge.addAttribute("from", "Bus Stop - " + busStopMap.get(stop).getTitle());
					edge.addAttribute("to", "Bus - " + arrivalPrediction.getVehicle().getRoute().getTitle() + " numbered " + arrivalPrediction.getVehicle().getVehicleNumber());
					edge.addAttribute("routeName", arrivalPrediction.getVehicle().getRoute().getTag());
					edge.addAttribute("vehicleNumber", arrivalPrediction.getVehicle().getVehicleNumber());
					edge.addAttribute("fromTime", startTime);
					edge.addAttribute("toTime", arrivalPrediction.getArrivalEpochTime());
					edge.addAttribute("weight", arrivalPrediction.getArrivalEpochTime()-startTime);
					TreeSet<ArrivalPrediction> predictionsForVehicle = predictionMap.get(arrivalPrediction.getVehicle());
					for(ArrivalPrediction vehiclePrediction : predictionsForVehicle){
						BusStop currBusStop = vehiclePrediction.getBusStop();
						if (vehiclePrediction.getArrivalEpochTime() - arrivalPrediction.getArrivalEpochTime() > 0 && !avoidOverlaps.contains(waitNode + "-" + currBusStop.getTag())) {
							avoidOverlaps.add(waitNode + "-" + currBusStop.getTag());

							String newBusStopTag = currBusStop.getTag()+"_POST_"+vehiclePrediction.getArrivalEpochTime();
							if(!busStopMap.containsKey(newBusStopTag)){
								BusStop newBusStop = new BusStop();
								newBusStop.setLocation(currBusStop.getLocation());
								newBusStop.setRoutesServed(currBusStop.getRoutesServed());
								newBusStop.setTag(newBusStopTag);
								newBusStop.setTitle(currBusStop.getTitle());
								for(ArrivalPrediction ap : stopPredictionsMap.get(currBusStop.getTag())){
									if(ap.getArrivalEpochTime() > vehiclePrediction.getArrivalEpochTime()){
										if(!stopPredictionsMap.containsKey(newBusStopTag)){
											stopPredictionsMap.put(newBusStopTag , new HashSet<>());
											org.graphstream.graph.Node newBusStopNode = graph.addNode(getGraphNodeNameForBusStopTag(newBusStopTag));
											busStopMap.put(newBusStopTag, newBusStop);
											temporaryNodes.add(getGraphNodeNameForBusStopTag(newBusStopTag));
											if(graph.getNode(getGraphNodeNameForBusStopTag(currBusStop.getTag())).hasEdgeToward(getUserDestinationNodeName())){
												Edge edgeToward = graph.getNode(getGraphNodeNameForBusStopTag(currBusStop.getTag())).getEdgeToward(getUserDestinationNodeName());
												Edge duplicateEdge = graph.addEdge(getWalkingEdgeName(edgeToward.getTargetNode().getId(), newBusStopNode.getId()), newBusStopNode, edgeToward.getTargetNode());
												Collection<String> attributeKeySet = edgeToward.getAttributeKeySet();
												for(String attrKey : attributeKeySet){
													Object attribute = edgeToward.getAttribute(attrKey);
													duplicateEdge.addAttribute(attrKey, attribute);
												}
											}
											/*if(graph.getNode(getGraphNodeNameForBusStopTag(currBusStop.getTag())).hasEdgeFrom(getUserSourceNodeName())){
												Edge edgeFrom = graph.getNode(getGraphNodeNameForBusStopTag(currBusStop.getTag())).getEdgeFrom(getUserSourceNodeName());
												Edge duplicateEdge = graph.addEdge(getWalkingEdgeName(edgeFrom.getSourceNode().getId(), newBusStopNode.getId()), edgeFrom.getSourceNode(), newBusStopNode);
												Collection<String> attributeKeySet = edgeFrom.getAttributeKeySet();
												for(String attrKey : attributeKeySet){
													Object attribute = edgeFrom.getAttribute(attrKey);
													duplicateEdge.addAttribute(attrKey, attribute);
												}
											}*/
										}
										ArrivalPrediction newAP = new ArrivalPrediction(ap.getArrivalEpochTime(), newBusStop, ap.getVehicle(), ap.getRoute());
										stopPredictionsMap.get(newBusStopTag).add(newAP);
									}
								}
								if(stopPredictionsMap.containsKey(newBusStopTag)){
									
									ArrivalPrediction vehiclePredictionCopy = new ArrivalPrediction(vehiclePrediction.getArrivalEpochTime(), newBusStop, vehiclePrediction.getVehicle(), vehiclePrediction.getRoute());

									Edge predictionEdge = graph.addEdge(getPredictionEdgeName(arrivalPrediction, vehiclePredictionCopy), waitNode, getGraphNodeNameForBusStopTag(newBusStopTag), true);
									predictionEdge.addAttribute("mode", Mode.IN_BUS);
									predictionEdge.addAttribute("from", "Bus Stop - " + busStopMap.get(stop).getTitle());
									predictionEdge.addAttribute("to", "Bus Stop - " + busStopMap.get(newBusStopTag).getTitle());
									predictionEdge.addAttribute("weight", vehiclePredictionCopy.getArrivalEpochTime() - arrivalPrediction.getArrivalEpochTime());
									predictionEdge.addAttribute("fromTime", arrivalPrediction.getArrivalEpochTime());
									predictionEdge.addAttribute("toTime", vehiclePredictionCopy.getArrivalEpochTime());
									
									createNewNodesAndEdgesForGivenStop(vehiclePredictionCopy.getArrivalEpochTime(), newBusStopTag);
								}
							}
							
							Edge predictionEdge = graph.addEdge(getPredictionEdgeName(arrivalPrediction, vehiclePrediction), waitNode, getGraphNodeNameForBusStopTag(currBusStop.getTag()), true);
							predictionEdge.addAttribute("mode", Mode.IN_BUS);
							predictionEdge.addAttribute("from", "Bus Stop - " + busStopMap.get(stop).getTitle());
							predictionEdge.addAttribute("to", "Bus Stop - " + busStopMap.get(currBusStop.getTag()).getTitle());
							predictionEdge.addAttribute("weight", vehiclePrediction.getArrivalEpochTime() - arrivalPrediction.getArrivalEpochTime());
							predictionEdge.addAttribute("fromTime", arrivalPrediction.getArrivalEpochTime());
							predictionEdge.addAttribute("toTime", vehiclePrediction.getArrivalEpochTime());
						}
					}
				}
			}
		}
	}
	
	/*public static void updateConnections(){
		try {
			long currentTime = new Date().getTime();
			updateCache();
			predictionsDocument = HTTPListener.getDocument(COMPLETED_PREDICTIONS_FOR_MULTIPLE_BUS_STOPS);
			NodeList predictionsNodes = predictionsDocument.getElementsByTagName("predictions");
			stopPredictionsMap = new HashMap<>();
			for(int i = 0 ; i < predictionsNodes.getLength() ; i++){
				Element predictionsElement = getNodeAsElement(predictionsNodes, i);
				NodeList directions = predictionsElement.getElementsByTagName("direction");
				String busStopTag = predictionsElement.getAttribute("stopTag");
				String routeTag = predictionsElement.getAttribute("routeTag");
				Route route = routesMap.get(routeTag);
				for(int j = 0 ; j < directions.getLength() ; j++){
					Element directionElement = getNodeAsElement(directions, j);
					NodeList predictionList = directionElement.getElementsByTagName("prediction");
					for(int k = 0 ; k < predictionList.getLength() ; k++){
						Element prediction = getNodeAsElement(predictionList, k);
						String vehicleNumber = prediction.getAttribute("vehicle");
						Vehicle vehicle = new Vehicle();
						vehicle.setRoute(route);
						vehicle.setVehicleNumber(vehicleNumber);
						String arrivalEpochTime = prediction.getAttribute("epochTime");
						String arrivalSeconds = prediction.getAttribute("seconds");
						if(!predictionMap.containsKey(vehicle)){
							predictionMap.put(vehicle, new TreeSet<>());
						}
						ArrivalPrediction arrivalPrediction = new ArrivalPrediction(Long.parseLong(arrivalEpochTime), busStopMap.get(busStopTag), vehicle, route);
						predictionMap.get(vehicle).add(arrivalPrediction);
						if(!stopPredictionsMap.containsKey(busStopTag)){
							stopPredictionsMap.put(busStopTag , new HashSet<>());
						}
						stopPredictionsMap.get(busStopTag).add(arrivalPrediction);
					}
				}
			}
			System.out.println(predictionMap);
			for(String waitNodeName : waitNodesName){
				graph.removeNode(waitNodeName);
			}
			waitNodesName = new HashSet<>();
			for (String stop : stopPredictionsMap.keySet()) {
				HashSet<ArrivalPrediction> predictionsForStop = stopPredictionsMap.get(stop);
				Set<String> avoidOverlaps = new HashSet<>();
				for (ArrivalPrediction arrivalPrediction : predictionsForStop) {
					avoidOverlaps = new HashSet<>();
					String waitNode = getWaitNodeNameForArrivalPrediction(arrivalPrediction);
					graph.addNode(waitNode);
					waitNodesName.add(waitNode);
					Edge edge = graph.addEdge(getWaitEdgeNameForGivenArrivalPrediction(arrivalPrediction), getGraphNodeNameForBusStopTag(stop), waitNode, true);
					edge.addAttribute("mode", Mode.WAITING);
					edge.addAttribute("from", "Bus Stop - " + busStopMap.get(stop).getTitle());
					edge.addAttribute("to", "Bus - " + arrivalPrediction.getVehicle().getRoute().getTag() + " numbered " + arrivalPrediction.getVehicle().getVehicleNumber());
					edge.addAttribute("weight", arrivalPrediction.getArrivalEpochTime()-currentTime);
					TreeSet<ArrivalPrediction> predictionsForVehicle = predictionMap.get(arrivalPrediction.getVehicle());
					for(ArrivalPrediction prediction : predictionsForVehicle){
						if (prediction.getArrivalEpochTime() - arrivalPrediction.getArrivalEpochTime() > 0 && !avoidOverlaps.contains(waitNode + "-" + prediction.getBusStop().getTag())) {
							avoidOverlaps.add(waitNode + "-" + prediction.getBusStop().getTag());
							Edge predictionEdge = graph.addEdge(getPredictionEdgeName(arrivalPrediction, prediction), waitNode, getGraphNodeNameForBusStopTag(prediction.getBusStop().getTag()), true);
							predictionEdge.addAttribute("mode", Mode.IN_BUS);
							predictionEdge.addAttribute("from", "Bus Stop - " + busStopMap.get(stop).getTitle());
							predictionEdge.addAttribute("to", "Bus Stop - " + busStopMap.get(prediction.getBusStop().getTag()).getTitle());
							predictionEdge.addAttribute("weight", prediction.getArrivalEpochTime() - arrivalPrediction.getArrivalEpochTime());
							predictionEdge.addAttribute("fromTime", arrivalPrediction.getArrivalEpochTime());
							predictionEdge.addAttribute("toTime", prediction.getArrivalEpochTime());
						}
					}
				}
			}
			System.out.println(predictionMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	public static String getPredictionEdgeName(ArrivalPrediction sourceArrivalPrediction, ArrivalPrediction destinationArrivalPrediciton) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(GRAPH_EDGE_PRE_NAME_PREDICTION);
		stringBuffer.append(GRAPH_NODE_NAME_SEPARATOR);
		stringBuffer.append(getWaitNodeNameForArrivalPrediction(sourceArrivalPrediction));
		stringBuffer.append(GRAPH_NODE_NAME_SEPARATOR);
		return GRAPH_EDGE_PRE_NAME_PREDICTION + GRAPH_NODE_NAME_SEPARATOR + sourceArrivalPrediction.getRoute().getTag()+GRAPH_NODE_NAME_SEPARATOR + destinationArrivalPrediciton.getVehicle().getVehicleNumber() + GRAPH_NODE_NAME_SEPARATOR + sourceArrivalPrediction.getBusStop().getTag()+GRAPH_NODE_NAME_SEPARATOR+sourceArrivalPrediction.getRoute().getTag()+GRAPH_NODE_NAME_SEPARATOR+sourceArrivalPrediction.getArrivalEpochTime() + GRAPH_NODE_NAME_SEPARATOR + destinationArrivalPrediciton.getBusStop().getTag() + GRAPH_NODE_NAME_SEPARATOR + destinationArrivalPrediciton.getArrivalEpochTime();
	}

	public static String getWaitEdgeNameForGivenArrivalPrediction(ArrivalPrediction arrivalPrediction) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(GRAPH_EDGE_PRE_NAME_WAIT);
		stringBuffer.append(GRAPH_NODE_NAME_SEPARATOR);
		stringBuffer.append(arrivalPrediction.getRoute().getTag());
		stringBuffer.append(GRAPH_NODE_NAME_SEPARATOR);
		stringBuffer.append(arrivalPrediction.getVehicle().getVehicleNumber());
		stringBuffer.append(GRAPH_NODE_NAME_SEPARATOR);
		stringBuffer.append(arrivalPrediction.getArrivalEpochTime());
		stringBuffer.append(GRAPH_NODE_NAME_SEPARATOR);
		stringBuffer.append(arrivalPrediction.getBusStop().getTag());
		return stringBuffer.toString();
	}

	public static String getWaitNodeNameForArrivalPrediction(ArrivalPrediction arrivalPrediction) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(GRAPH_NODE_PRE_NAME_WAIT);
		stringBuffer.append(GRAPH_NODE_NAME_SEPARATOR);
		stringBuffer.append(arrivalPrediction.getBusStop().getTag());
		stringBuffer.append(GRAPH_NODE_NAME_SEPARATOR);
		stringBuffer.append(arrivalPrediction.getRoute().getTag());
		stringBuffer.append(GRAPH_NODE_NAME_SEPARATOR);
		stringBuffer.append(arrivalPrediction.getVehicle().getVehicleNumber());
		stringBuffer.append(GRAPH_NODE_NAME_SEPARATOR);
		stringBuffer.append(arrivalPrediction.getArrivalEpochTime());
		
		return stringBuffer.toString();
	}

	public static String getGraphNodeNameForBusStopTag(String busStopTag) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(GRAPH_NODE_PRE_NAME_BUS_STOP);
		stringBuffer.append(GRAPH_NODE_NAME_SEPARATOR);
		stringBuffer.append(busStopTag);
		return stringBuffer.toString();
	}

	public static Element getNodeAsElement(NodeList routeNodes, int i) {
		Node routeNode = routeNodes.item(i);
		Element routeElement = (Element) routeNode;
		return routeElement;
	}

	public static void addUserNodes(Location source, Location destination, long startTime) throws Exception {
		eraseTemporaryNodes();
		updateCache();
		Map<String, Long> interestedBusStopsMap = addUserNode(source, true, startTime);
		addUserNode(destination, false, startTime);
		addDefaultEdge(source, destination, startTime);
		updateConnections(interestedBusStopsMap);
	}

	private static void addDefaultEdge(Location source, Location destination, long startTime) throws Exception {
		// TODO Auto-generated method stub
		String distanceMatrixURL = GOOGLE_DISTANCE_MATRIX + "origins=" + source.getPoint2d().getX() + ","+ source.getPoint2d().getY() + "&destinations=" + destination.getPoint2d().getX() + ","+ destination.getPoint2d().getY();
		JSONObject json = HTTPListener.getJSON(distanceMatrixURL);
		JSONArray elementsArray;
		JSONArray outerArray = json.getJSONArray("rows");
		JSONObject elementsObject = outerArray.getJSONObject(0);
		elementsArray = elementsObject.getJSONArray("elements");
		JSONObject innerJSONObject = elementsArray.getJSONObject(0);
		JSONObject durationJSONObject = innerJSONObject.getJSONObject("duration");
		int seconds = durationJSONObject.getInt("value");
		Edge edge = graph.addEdge(
				getWalkingEdgeName(getUserSourceNodeName(), getUserDestinationNodeName()),//getUserDestinationNodeName(destination)
				getUserSourceNodeName(), getUserDestinationNodeName(), true);//getUserDestinationNodeName(destination)
		edge.addAttribute("weight", seconds * 1000);
		edge.addAttribute("mode", Mode.WALKING);
		edge.addAttribute("from", "Starting Location");
		edge.addAttribute("to", "Destination");
		edge.addAttribute("fromTime", startTime);
		edge.addAttribute("toTime", startTime + (seconds * 1000));
		edge.addAttribute("startlat", source.getPoint2d().getX());
		edge.addAttribute("startlon", source.getPoint2d().getY());
		edge.addAttribute("toLat", destination.getPoint2d().getX());
		edge.addAttribute("toLon", destination.getPoint2d().getY());
	}

	private static Map<String, Long> addUserNode(Location userPoint, boolean isSource, long startTime) throws Exception, JSONException {
		//Set<String> interestedBusStopTags = new HashSet<>();
		Map<String, Long> interestedBusStopTags = new HashMap<>();
		String userNodeName;
		if (isSource) {
			userNodeName = getUserSourceNodeName();
		} else{
			userNodeName = getUserDestinationNodeName();//getUserDestinationNodeName(userPoint);
		}
		graph.addNode(userNodeName);
		String distanceMatrixURL;
		if (isSource) {
			distanceMatrixURL = GOOGLE_DISTANCE_MATRIX + "origins=" + userPoint.getPoint2d().getX() + ","+ userPoint.getPoint2d().getY() + "&destinations=";
		} else {
			distanceMatrixURL = GOOGLE_DISTANCE_MATRIX + "destinations=" + userPoint.getPoint2d().getX() + ","+ userPoint.getPoint2d().getY() + "&origins=";
		}
		List<BusStop> busStops = new ArrayList<>(busStopMap.values());
		BusStop dummyBusStop = new BusStop();
		dummyBusStop.setLocation(userPoint);
		Collections.sort(busStops, sortBusStopComparator(dummyBusStop));
		//int topK = busStops.size();
		int topK = 5;
		for(int i = 0 ; i < topK ; i++){
			distanceMatrixURL+=busStops.get(i).getLocation().getPoint2d().getX()+","+busStops.get(i).getLocation().getPoint2d().getY();
			//interestedBusStopTags.add(busStops.get(i).getTag());
			if(i<topK-1){
				distanceMatrixURL+="|";
			}
		}
		JSONObject json = HTTPListener.getJSON(distanceMatrixURL);
		JSONArray elementsArray;
		if (isSource) {
			JSONArray outerArray = json.getJSONArray("rows");
			JSONObject elementsObject = outerArray.getJSONObject(0);
			elementsArray = elementsObject.getJSONArray("elements");
		} else{
			elementsArray = new JSONArray();
			JSONArray rowsArray = json.getJSONArray("rows");
			for(int j = 0 ; j < rowsArray.length() ; j++){
				JSONObject elementObject = rowsArray.getJSONObject(j).getJSONArray("elements").getJSONObject(0);
				elementsArray.put(elementObject);
			}
		}
		for(int j = 0 ; j < elementsArray.length() ; j++){
			try {
				JSONObject innerJSONObject = elementsArray.getJSONObject(j);
				JSONObject durationJSONObject = innerJSONObject.getJSONObject("duration");
				int seconds = durationJSONObject.getInt("value");
				Edge edge;
				if (isSource) {
					edge = graph.addEdge(
							getWalkingEdgeName(userNodeName, getGraphNodeNameForBusStopTag(busStops.get(j).getTag())),
							userNodeName, getGraphNodeNameForBusStopTag(busStops.get(j).getTag()), true);
					edge.addAttribute("from", "Starting Location");
					edge.addAttribute("to", "Bus Stop - " + busStops.get(j).getTitle());
					edge.addAttribute("startlat", userPoint.getPoint2d().getX());
					edge.addAttribute("startlon", userPoint.getPoint2d().getY());
					edge.addAttribute("toLat", busStops.get(j).getLocation().getPoint2d().getX());
					edge.addAttribute("toLon", busStops.get(j).getLocation().getPoint2d().getY());
				} else{
					edge = graph.addEdge(
							getWalkingEdgeName(userNodeName, getGraphNodeNameForBusStopTag(busStops.get(j).getTag())),
							getGraphNodeNameForBusStopTag(busStops.get(j).getTag()), userNodeName, true);
					edge.addAttribute("from", "Bus Stop - " + busStops.get(j).getTitle());
					edge.addAttribute("to", "Destination");
					edge.addAttribute("toLat", userPoint.getPoint2d().getX());
					edge.addAttribute("toLon", userPoint.getPoint2d().getY());
					edge.addAttribute("startlat", busStops.get(j).getLocation().getPoint2d().getX());
					edge.addAttribute("startlon", busStops.get(j).getLocation().getPoint2d().getY());
				}
				edge.addAttribute("weight", seconds * 1000);
				edge.addAttribute("fromTime", startTime);
				edge.addAttribute("toTime", startTime + (seconds * 1000));
				edge.addAttribute("mode", Mode.WALKING);
				interestedBusStopTags.put(busStops.get(j).getTag(), startTime + (seconds * 1000));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return interestedBusStopTags;
	}

	/*public static String getUserDestinationNodeName(Location source) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(GRAPH_NODE_PRE_NAME_USER_DESTINATION);
		buffer.append(GRAPH_NODE_NAME_SEPARATOR);
		double latitude = source.getPoint2d().getX();
		buffer.append(latitude);
		buffer.append(GRAPH_NODE_NAME_SEPARATOR);
		double longitude = source.getPoint2d().getY();
		buffer.append(longitude);
		return buffer.toString();
	}*/

	public static String getUserDestinationNodeName() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(GRAPH_NODE_PRE_NAME_USER_DESTINATION);
		return buffer.toString();
	}

	private static String getWalkingEdgeName(String userSourceNodeName, String graphNodeNameForBusStopTag) {
		return GRAPH_EDGE_PRE_NAME_WALKING + GRAPH_NODE_NAME_SEPARATOR + userSourceNodeName + GRAPH_NODE_NAME_SEPARATOR + graphNodeNameForBusStopTag;
	}

	public static String getUserSourceNodeName() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(GRAPH_NODE_PRE_NAME_USER_SOURCE);
		return buffer.toString();
	}

	private static Comparator<BusStop> sortBusStopComparator(BusStop dummyBusStop) {
		return new Comparator<BusStop>() {
			@Override
			public int compare(BusStop o1, BusStop o2) {
				double ds0 = o1.getLocation().getPoint2d().distanceSq(dummyBusStop.getLocation().getPoint2d());
                double ds1 = o2.getLocation().getPoint2d().distanceSq(dummyBusStop.getLocation().getPoint2d());
                return Double.compare(ds0, ds1);
			}
		};
	}

	public static void removeUserNodes(Location source, Location destination) {
		try {
			graph.removeNode(getUserSourceNodeName());
		} catch (ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			graph.removeNode(getUserDestinationNodeName());//graph.removeNode(getUserDestinationNodeName(destination));
		} catch (ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
