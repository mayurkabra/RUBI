<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="../resources/static/css/bootstrap.css" />
<title>Spring Test</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 50vh;
        border-top: 2px solid #000;
	    border-left: 2px solid #000;
	    border-right: 2px solid #000;
      }
      #description {
        font-family: Roboto;
        font-size: 15px;
        font-weight: 300;
      }

      #infowindow-content .title {
        font-weight: bold;
      }

      #infowindow-content {
        display: none;
      }

      #map #infowindow-content {
        display: inline;
      }

      .pac-card {
        margin: 10px 10px 0 0;
        border-radius: 2px 0 0 2px;
        box-sizing: border-box;
        -moz-box-sizing: border-box;
        outline: none;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
        background-color: #fff;
        font-family: Roboto;
      }

      #pac-container {
        padding-bottom: 12px;
        margin-right: 12px;
      }

      .pac-controls {
        display: inline-block;
        padding: 5px 11px;
      }

      .pac-controls label {
        font-family: Roboto;
        font-size: 13px;
        font-weight: 300;
      }

      #pac-input {
        background-color: #fff;
        font-family: Roboto;
        font-size: 15px;
        font-weight: 300;
        margin-left: 12px;
        padding: 0 11px 0 13px;
        text-overflow: ellipsis;
        width: 400px;
      }

      #pac-input:focus {
        border-color: #4d90fe;
      }

      #title {
        color: #fff;
        background-color: #4d90fe;
        font-size: 25px;
        font-weight: 500;
        padding: 6px 12px;
      }
      
      .location-type-selected{
      	border-left-color: black;
	    border-right-color: black;
	    border-bottom-color: black;
      }
      
      .location-type-selected:hover{
      	border-left-color: black;
	    border-right-color: black;
	    border-bottom-color: black;
      }
      
      .location-type-not-selected{
      	border-top-color: black;
      }
      
      .location-type-not-selected:hover{
      	border-top-color: black;
      }
      
      .location-type{
      	border-radius: 0px;
      }
    </style>
</head>
<body>
	<nav class="navbar navbar-default">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="#">Rutgers University Buses Insight</a>
	    </div>
	
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      
	    </div>
	  </div>
	</nav>
	<div class="container">
	<input type="text" id="testAutocomplete" />
		<div class="pac-card" id="pac-card">
	      <div>
	        <div id="title">
	          Where to?
	        </div>
	        <div id="type-selector" class="pac-controls" style="display: none">
	          <input type="radio" name="type" id="changetype-all" checked="checked">
	          <label for="changetype-all">All</label>
	
	          <input type="radio" name="type" id="changetype-establishment">
	          <label for="changetype-establishment">Establishments</label>
	
	          <input type="radio" name="type" id="changetype-address">
	          <label for="changetype-address">Addresses</label>
	
	          <input type="radio" name="type" id="changetype-geocode">
	          <label for="changetype-geocode">Geocodes</label>
	        </div>
	        <div id="strict-bounds-selector" class="pac-controls" style="display: none">
	          <input type="checkbox" id="use-strict-bounds" value="">
	          <label for="use-strict-bounds">Strict Bounds</label>
	        </div>
	      </div>
	      <div id="pac-container">
	        <input id="pac-input" type="text"
	            placeholder="Enter a location">
	      </div>
	    </div>
	    <div id="map"></div>
	    <div id="infowindow-content">
	      <img src="" width="16" height="16" id="place-icon">
	      <span id="place-name"  class="title"></span><br>
	      <span id="place-address"></span>
	    </div>
		<form class="form-horizontal" action="../direction/fromTo">
		  <fieldset style="display: none">
		    <legend>From</legend>
		    <div class="form-group">
		      <label for="fromLat" class="col-lg-2 control-label">Current Latitude</label>
		      <div class="col-lg-10">
		        <input type="text" class="form-control" id="fromLat" name="fromLat" placeholder="Current Latitude">
		      </div>
		    </div>
		    <div class="form-group">
		      <label for="fromLon" class="col-lg-2 control-label">Current Longitude</label>
		      <div class="col-lg-10">
		        <input type="text" class="form-control" id="fromLon" name="fromLon" placeholder="Current Longitude">
		      </div>
		    </div>
		  </fieldset>
		  <fieldset style="display: none">
		    <legend>To</legend>
		    <div class="form-group">
		      <label for="toLat" class="col-lg-2 control-label">Destination Latitude</label>
		      <div class="col-lg-10">
		        <input type="text" class="form-control" id="toLat" name="toLat" placeholder="Destination Latitude">
		      </div>
		    </div>
		    <div class="form-group">
		      <label for="toLon" class="col-lg-2 control-label">Destination Longitude</label>
		      <div class="col-lg-10">
		        <input type="text" class="form-control" id="toLon" name="toLon" placeholder="Destination Longitude">
		      </div>
		    </div>
		  </fieldset>
		  <fieldset>
		    <div class="form-group">
			    <div class="col-xs-12">
				   <div class="btn btn-warning col-xs-6 location-type location-type-selected info-incomplete" onclick="javascript:locationTypeSwitch();">From</div>
				   <div class="btn btn-warning col-xs-6 location-type location-type-not-selected info-incomplete" onclick="javascript:locationTypeSwitch();">To</div>
		           <button type="submit" class="btn btn-primary col-xs-12">Get Directions</button>		
				</div>
		    </div>
		  </fieldset>
		  <!-- <fieldset>
		    <div class="form-group">
		      <div class="col-xs-12">
		        <button type="submit" class="btn btn-primary col-xs-12">Submit</button>
		      </div>
		    </div>
		  </fieldset> -->
		</form>
	</div>
	<footer class="footer">
      <div class="container">
        <p class="text-muted">Rutgers University Buses Insight</p>
      </div>
    </footer>
	
	<script type="text/javascript" src="../resources/static/js/jquery-3.1.1.js"></script>
	<script type="text/javascript" src="../resources/static/js/bootstrap.js"></script>
	<script type="text/javascript" src="../resources/static/js/fromTo.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBfl7YOLYDHwoQf-gOl3PZX8JHArgycGq4&libraries=places&callback=initMap" async defer></script>
</body>
</html>