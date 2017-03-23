package com.kabra.mayur.controller;

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
	public ModelAndView findWayAjax(String fromLat, String fromLng, String toLat, String toLng, Model model){
		ModelAndView modelAndView = new ModelAndView("optionsInner");
		//DataStore.updateConnections();
		List<DirectionSet> fastestDirections = null;
		try {
			fastestDirections = busService.getFastestDirections(new Location(Double.parseDouble(fromLat), Double.parseDouble(fromLng)), new Location(Double.parseDouble(toLat), Double.parseDouble(toLng)));
			modelAndView.addObject("options", fastestDirections);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelAndView;
	}

}
