package com.kabra.mayur.dao;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.kabra.mayur.cached.DataStore;
import com.kabra.mayur.entity.DirectionStepWait;
import com.kabra.mayur.entity.BusStop;
import com.kabra.mayur.entity.BusStopRequest;
import com.kabra.mayur.entity.Location;
import com.kabra.mayur.entity.Route;
import com.kabra.mayur.entity.VehicleArrival;
import com.kabra.mayur.utility.HibernateUtil;

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
	
	@Transactional
	public void saveVehicleArrivals(VehicleArrival vehicleArrival){
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			/*session.saveOrUpdate("VehicleArrival", vehicleArrival);*/
			Query query = session.createNativeQuery("INSERT INTO [dbo].[VehicleArrival] ([busNumber] ,[date] ,[dayOfWeek] ,[epochTime] ,[hourOfDay] ,[month] ,[route] ,[stop]) "
					+ "VALUES (:busno ,:date ,:day ,:time ,:hour ,:month ,:route ,:stop)");
			query.setString("busno", vehicleArrival.getBusNumber());
			query.setString("route", vehicleArrival.getRoute());
			query.setInteger("month", vehicleArrival.getMonth());
			query.setInteger("hour", vehicleArrival.getHourOfDay());
			query.setLong("time", vehicleArrival.getEpochTime());
			query.setInteger("day", vehicleArrival.getDayOfWeek());
			query.setTimestamp("date", vehicleArrival.getDate());
			query.setString("stop", vehicleArrival.getStop());
			
			query.executeUpdate();
			
			
			
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(session != null && session.isOpen()){
				session.close();
			}
		}
	}
	
	public List getOptionsForFilter(String route, String stop, Date from, Date to){
		List list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			/*Criteria criteria = session.createCriteria(VehicleArrival.class);
			criteria.add(Restrictions.eq("route", route));
			criteria.add(Restrictions.eq("stop", stop));
			criteria.add(Restrictions.between("date", from, to));
			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("hourOfDay"))
					.add(Projections.rowCount()));*/
			
			Criteria criteria = session.createCriteria(VehicleArrival.class);
			criteria.add(Restrictions.eq("route", route));
			criteria.add(Restrictions.between("date", from, to));
			criteria.addOrder(Order.asc("hourOfDay"));
			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("hourOfDay"))
					.add(Projections.sqlProjection("(COUNT(vehicleArrivalId)/COUNT(DISTINCT stop))/COUNT(DISTINCT DATEPART(DY, date)) AS vehicleCount", new String[]{"vehicleCount"}, new Type[]{StandardBasicTypes.INTEGER})));
					//.add(Projections.rowCount()));
			
			list = criteria.list();
			
			/*Query query = session.createNativeQuery("SELECT [hourOfDay], COUNT(*) FROM [dbo].[VehicleArrival] WHERE route = :route AND stop = :stop AND DATE between :from and :to GROUP BY [hourOfDay] ORDER BY [hourOfDay]");
			query.setString("route", route);
			query.setString("stop", stop);
			query.setDate("from", from);
			query.setDate("to", to);
			
			list = query.getResultList();*/
			
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return list;
	}

	public List stopwiseChartValues(String stop, Date from, Date to) {
		List list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			/*Criteria criteria = session.createCriteria(VehicleArrival.class);
			criteria.add(Restrictions.eq("route", route));
			criteria.add(Restrictions.eq("stop", stop));
			criteria.add(Restrictions.between("date", from, to));
			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("hourOfDay"))
					.add(Projections.rowCount()));*/
			
			Criteria criteria = session.createCriteria(VehicleArrival.class);
			criteria.add(Restrictions.in("stop", stop.split(",")));
			criteria.add(Restrictions.between("date", from, to));
			criteria.addOrder(Order.asc("dayOfWeek"));
			criteria.addOrder(Order.asc("hourOfDay"));
			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("dayOfWeek"))
					.add(Projections.groupProperty("hourOfDay"))
					.add(Projections.sqlProjection("count(vehicleArrivalId)/COUNT(DISTINCT DATEPART(DY, date)) AS vehicleCount", new String[]{"vehicleCount"}, new Type[]{StandardBasicTypes.INTEGER})));
					//.add(Projections.rowCount()));
			
			list = criteria.list();
			
			/*Query query = session.createNativeQuery("SELECT [hourOfDay], COUNT(*) FROM [dbo].[VehicleArrival] WHERE route = :route AND stop = :stop AND DATE between :from and :to GROUP BY [hourOfDay] ORDER BY [hourOfDay]");
			query.setString("route", route);
			query.setString("stop", stop);
			query.setDate("from", from);
			query.setDate("to", to);
			
			list = query.getResultList();*/
			
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return list;
	}

}
