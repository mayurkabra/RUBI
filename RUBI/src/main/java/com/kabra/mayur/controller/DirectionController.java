package com.kabra.mayur.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.kabra.mayur.cached.DataStore;
import com.kabra.mayur.entity.BusDirection;
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
		ModelAndView modelAndView = new ModelAndView("fromTo");
		List<BusDirection> fastestDirections = busService.getFastestDirections(new Location(Double.parseDouble(fromLat), Double.parseDouble(fromLon)), new Location(Double.parseDouble(toLat), Double.parseDouble(toLon)));
		model.addAttribute(fastestDirections);
		return modelAndView;
	}

}
