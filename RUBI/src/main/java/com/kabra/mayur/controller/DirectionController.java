package com.kabra.mayur.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.kabra.mayur.cached.DataStore;
import com.kabra.mayur.entity.DirectionSet;
import com.kabra.mayur.entity.Location;
import com.kabra.mayur.service.BusService;

@RestController
@RequestMapping(value="direction/")
public class DirectionController {
	
	@Autowired
	BusService busService;
	
	@RequestMapping(value="fromTo")
	public ModelAndView parameterTest(String fromLat, String fromLon, String toLat, String toLon, Model model){
		DataStore.updateCache();
		ModelAndView modelAndView = new ModelAndView("parameterTestView");
		/*List<BusDirection> fastestDirections = busService.getFastestDirections(new Location(Double.parseDouble(fromLat), Double.parseDouble(fromLon)), new Location(Double.parseDouble(toLat), Double.parseDouble(toLon)));
		model.addAttribute(fastestDirections);*/
		return modelAndView;
	}
	
	@RequestMapping(value="findWayAjax")
	public ModelAndView findWayAjax(String fromLat, String fromLng, String toLat, String toLng, boolean notransfer, Model model){
		ModelAndView modelAndView = new ModelAndView("optionsInner");
		//DataStore.updateConnections();
		List<DirectionSet> fastestDirections = null;
		try {
			fastestDirections = busService.getFastestDirections(new Location(Double.parseDouble(fromLat), Double.parseDouble(fromLng)), new Location(Double.parseDouble(toLat), Double.parseDouble(toLng)), notransfer);
			modelAndView.addObject("options", fastestDirections);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	@RequestMapping(value="hourlyChart")
	public ModelAndView hourlyChart(String route, String stop, Date from, Date to, Model model){
		ModelAndView modelAndView = new ModelAndView("barChart");
		model.addAttribute("route", route);
		model.addAttribute("stop", stop);
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		return modelAndView;
	}
	
	@RequestMapping(value="hourlyChartTSV")
	public String hourlyChartTSV(String route, String stop, Date from, Date to){
		Calendar calendar = Calendar.getInstance();
		String tsv = "Hour_of_Day\tNumber_of_Vehicles\n";
		if(from == null){
			calendar = Calendar.getInstance();
		} else{
			calendar.setTime(from);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		from = new Date(calendar.getTimeInMillis());
		
		if(to == null){
			calendar = Calendar.getInstance();
		} else{
			calendar.setTime(to);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 99);
		to = new Date(calendar.getTimeInMillis());
		List<Object[]> list = busService.getOptionsForFilter(route, stop, from, to);
		for(Object[] object : list){
			tsv+=object [0]+"\t";
			tsv+=object[1]+"\n";
		}
		return tsv;
	}

}
