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
		      <div class="col-lg-10 col-lg-offset-2">
		        <button type="reset" class="btn btn-default">Cancel</button>
		        <button type="submit" class="btn btn-primary">Submit</button>
		      </div>
		    </div>
		  </fieldset>
		</form>
		<div class="panel-group">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" href="#collapse1">Option 1: <!-- You will reach at XX:XX PM --><br/>Total Time (21 minutes) = Bus Time (10 minutes) + Walk Time (9 minutes) + Waiting Time (2 minutes)</a>
					</h4>
				</div>
				<div id="collapse1" class="panel-collapse collapse">
					<ul class="list-group">
						<li class="list-group-item">
							<div class="panel-group" style="margin-bottom: 0px;">
							  <div class="panel panel-default">
							    <div class="panel-heading">
							      <h4 class="panel-title">
							        <a data-toggle="collapse" href="#innercollapse1">Walk to the Quads (4 minutes)</a>
							      </h4>
							    </div>
							    <div id="innercollapse1" class="panel-collapse collapse">
							      <div class="panel-body"><iframe src="https://www.google.com/maps/embed?pb=!1m26!1m12!1m3!1d3032.924145218318!2d-74.43653353504543!3d40.52116767935308!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!4m11!3e2!4m3!3m2!1d40.522164499999995!2d-74.4356605!4m5!1s0x89c3c7be243f3807%3A0xc08ecfb005f17419!2sThe+Quads%2C+89+Rd+3%2C+Piscataway+Township%2C+NJ+08854!3m2!1d40.520413999999995!2d-74.4330325!5e0!3m2!1sen!2sus!4v1485766157517" width="400" height="300" frameborder="0" style="border:0" allowfullscreen></iframe></div>
							    </div>
							  </div>
							</div>
						</li>
						<li class="list-group-item">Catch LX and get off at Scott Hall (12 minutes)</li>
						<li class="list-group-item">
						<div class="panel-group" style="margin-bottom: 0px;">
							  <div class="panel panel-default">
							    <div class="panel-heading">
							      <h4 class="panel-title">
							        <a data-toggle="collapse" href="#innercollapse2">Walk to 85 Easton Avenue (5 minutes)</a>
							      </h4>
							    </div>
							    <div id="innercollapse2" class="panel-collapse collapse">
							      <div class="panel-body"><iframe src="https://www.google.com/maps/embed?pb=!1m26!1m12!1m3!1d1516.9643677656043!2d-74.4503647919006!3d40.498958894838935!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!4m11!3e2!4m3!3m2!1d40.499621499999996!2d-74.4482488!4m5!1s0x89c3c655de5ef3a1%3A0xa0b5199f978a1c0c!2s85+Easton+Avenue%2C+New+Brunswick%2C+NJ+08901%2C+USA!3m2!1d40.4986207!2d-74.4508773!5e0!3m2!1sen!2sus!4v1485766806599" width="400" height="300" frameborder="0" style="border:0" allowfullscreen></iframe></div>
							    </div>
							  </div>
							</div>
						</li>
					</ul>
					<!-- <div class="panel-footer">Footer</div> -->
				</div>
			</div>
		</div>

		<div class="panel-group">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" href="#collapse2">Option 2: <!-- You will reach at XX:XX PM --><br/>Total Time (24 minutes) = Bus Time (12 minutes) + Walk Time (8 minutes) + Waiting Time (4 minutes)</a>
					</h4>
				</div>
				<div id="collapse2" class="panel-collapse collapse">
					<ul class="list-group">
						<li class="list-group-item">
							<div class="panel-group" style="margin-bottom: 0px;">
							  <div class="panel panel-default">
							    <div class="panel-heading">
							      <h4 class="panel-title">
							        <a data-toggle="collapse" href="#innercollapse3">Walk to the Quads (4 minutes)</a>
							      </h4>
							    </div>
							    <div id="innercollapse3" class="panel-collapse collapse">
							      <div class="panel-body"><iframe src="https://www.google.com/maps/embed?pb=!1m26!1m12!1m3!1d3032.924145218318!2d-74.43653353504543!3d40.52116767935308!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!4m11!3e2!4m3!3m2!1d40.522164499999995!2d-74.4356605!4m5!1s0x89c3c7be243f3807%3A0xc08ecfb005f17419!2sThe+Quads%2C+89+Rd+3%2C+Piscataway+Township%2C+NJ+08854!3m2!1d40.520413999999995!2d-74.4330325!5e0!3m2!1sen!2sus!4v1485766157517" width="400" height="300" frameborder="0" style="border:0" allowfullscreen></iframe></div>
							    </div>
							  </div>
							</div>
						</li>
						<li class="list-group-item">Catch LX and get off at Scott Hall (12 minutes)</li>
						<li class="list-group-item">
						<div class="panel-group" style="margin-bottom: 0px;">
							  <div class="panel panel-default">
							    <div class="panel-heading">
							      <h4 class="panel-title">
							        <a data-toggle="collapse" href="#innercollapse4">Walk to 85 Easton Avenue (5 minutes)</a>
							      </h4>
							    </div>
							    <div id="innercollapse4" class="panel-collapse collapse">
							      <div class="panel-body"><iframe src="https://www.google.com/maps/embed?pb=!1m26!1m12!1m3!1d1516.9643677656043!2d-74.4503647919006!3d40.498958894838935!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!4m11!3e2!4m3!3m2!1d40.499621499999996!2d-74.4482488!4m5!1s0x89c3c655de5ef3a1%3A0xa0b5199f978a1c0c!2s85+Easton+Avenue%2C+New+Brunswick%2C+NJ+08901%2C+USA!3m2!1d40.4986207!2d-74.4508773!5e0!3m2!1sen!2sus!4v1485766806599" width="400" height="300" frameborder="0" style="border:0" allowfullscreen></iframe></div>
							    </div>
							  </div>
							</div>
						</li>
					</ul>
					<!-- <div class="panel-footer">Footer</div> -->
				</div>
			</div>
		</div>
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