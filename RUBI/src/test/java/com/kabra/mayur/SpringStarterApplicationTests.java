package com.kabra.mayur;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.SingleGraph;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kabra.mayur.cached.DataStore;
import com.kabra.mayur.entity.BusStop;
import com.kabra.mayur.entity.Route;
import com.kabra.mayur.utility.HibernateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringStarterApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void httpTest(){
		//HTTPListener.getDocument("http://webservices.nextbus.com/service/publicXMLFeed?a=rutgers&command=routeConfig");
		DataStore.updateCache();
		for (Route route : DataStore.routesMap.values()) {
			System.out.println(route);
		}
		for (Route route : DataStore.routesMap.values()) {
			System.out.println(route.getQueryForPredictions());
		}
		for(BusStop busStop : DataStore.busStopMap.values()){
			System.out.println(busStop);
		}
	}
	
	@Test
	public void predListenerTest(){
		//DataStore.updateConnections();
		DataStore.aStar.compute("NODE_BUS_STOP-=-pubsafn","NODE_BUS_STOP-=-werblinm");
		Path shortestPath = DataStore.aStar.getShortestPath();
		shortestPath.getEdgeSet().forEach(edge->{
			System.out.println(edge);
		});
	}
	
	@Test
	public void classTest(){
		System.setProperty("java.awt.headless", "false"); 
		Graph graph = new SingleGraph("classTest");
		graph.display();
		for(int i = 0 ; i < 11 ; i++){
			graph.addNode(""+i);
		}
		int[][] test = {
				{},
				{1},
				{1,1},
				{1,0,1},
				{1,0,1,1},
				{1,0,1,0,1},
				{1,0,1,0,1,1},
				{1,1,1,0,0,0,1},
				{1,1,1,0,1,1,1,1},
				{1,1,1,1,1,1,1,0,1},
				{1,1,1,1,1,1,1,1,1,1}};
		for(int i = 0 ; i < test.length ; i++){
			for(int j = 0 ; j < test[i].length ; j++){
				if(test[i][j]==1){
					graph.addEdge(""+i+""+j, ""+i, ""+j);
				}
			}
			
		}
		int v = graph.getNodeCount();
		System.out.println("Number of vertices: " + v);
		int e = graph.getEdgeCount();
		System.out.println("Number of edges: " + e);
		System.out.println("Density of graph: " + (2.0*e/(v*(v-1))));
		System.out.println("Degree of graph: " + (2.0*e/v));
		
	}
	
	/*@Test
	public void dataSourceStringTest(){
		SQLServerDataSource dataSource = new SQLServerDataSource();
		dataSource.setServerName("MAYUR-KABRA-PC");
		dataSource.setIntegratedSecurity(true);
		dataSource.setDatabaseName("RUBI");
		dataSource.setInstanceName("SQL2014");
		System.out.println(dataSource.getURL());
	}*/
	
	@Test
	public void dbTest(){
		Session session = HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();

	    // Check database version
	    String sql = "select @@version";

	    String result = (String) session.createNativeQuery(sql).getSingleResult();
	    System.out.println(result);

	    session.getTransaction().commit();
	    session.close();

	    
	    HibernateUtil.shutdown();
	}

}
