package com.kabra.mayur;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kabra.mayur.cached.DataStore;
import com.kabra.mayur.entity.BusStop;
import com.kabra.mayur.entity.Route;

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
		for (Route route : DataStore.routes) {
			System.out.println(route);
		}
		for (Route route : DataStore.routes) {
			System.out.println(route.getQueryForPredictions());
		}
		for(BusStop busStop : DataStore.busStopMap.values()){
			System.out.println(busStop);
		}
	}

}
