package com.kabra.mayur.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@RestController
@RequestMapping(value="first/")
public class FirstController {
	
	@RequestMapping(value="first")
	public String first(){
		return "Test Succesfull";
	}
	
	@RequestMapping(value="parameterTest")
	public ModelAndView parameterTest(String testName, Model model){
		model.addAttribute("passedParameter", testName);
		ModelAndView modelAndView = new ModelAndView("parameterTestView");
		return modelAndView;
	}

}
