package com.realestate.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.realestate.beans.GeoBox;
import com.realestate.beans.PropertyModel;
import com.realestate.database.DatabaseController;

@RestController
@RequestMapping("/")
@EnableWebMvc
public class MVCController {

	@Inject
	DatabaseController dc;

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> propertyServiceImpl(
			@RequestBody PropertyModel property, HttpServletResponse response,
			HttpServletRequest request) {
		dc.savePropertyClass(property);

		return Collections.singletonMap("name", property.getName());
	}

	@RequestMapping(value = "/ind", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> loadFormPage(
			@RequestBody PropertyModel property, HttpServletResponse response,
			HttpServletRequest request) {
		ArrayList<PropertyModel> propList = dc.loadPropertyClass();
		Map<String, Object> map = new ConcurrentSkipListMap<String, Object>();
		map.put("propertyList", propList);
		return map;
	}

	@RequestMapping(value = "/box", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> loadPropertiesInBox(
			@RequestBody GeoBox geoBox, HttpServletResponse response,
			HttpServletRequest request) {
		ArrayList<PropertyModel> propList = dc.loadPropertiesinBox(geoBox);
		Map<String, ArrayList<PropertyModel>> map = new ConcurrentSkipListMap<String, ArrayList<PropertyModel>>();
		map.put("propertyList", propList);
		return map;
	}
}
