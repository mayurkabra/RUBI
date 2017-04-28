<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="../resources/static/css/bootstrap.css" />
<title>RUBI</title>
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
	      <a class="navbar-brand" href="../first/parameterTest">Rutgers University Buses Insight</a>
	    </div>
	
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Point-to-Point Directions</a></li>
				<li><a href="../analytics/currentGraphForm">Current Status</a></li>
				<li><a href="../analytics/hourlyChartForm">Hourly Analytics: Routewise</a></li>
				<li><a href="../analytics/stopwiseChartForm">Weekly Analytics: Stopwise</a></li>
				<li><a href="../analytics/hashtag"><b>#</b>RUBuses</a></li>
			</ul>
	    </div>
	  </div>
	</nav>
	
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-lg-6">
				<div class="row col-xs-12">
					<h4>From</h4>
				</div>
				<div class="row col-xs-12">
					<input type="text" id="fromAddress" class="col-xs-12"/>
					<input type="hidden" id="fromLat" />
					<input type="hidden" id="fromLng" />
				</div>
				<div class="row col-xs-12">
					<div id="fromMap" class="col-xs-12" style="height: 60vh;"></div>
				</div>
			</div>
			<div class="col-xs-12 col-lg-6">
				<div class="row col-xs-12">
				<h4>To</h4>
				</div>
				<div class="row col-xs-12">
					<input type="text" id="toAddress" class="col-xs-12"/>
					<input type="hidden" id="toLat" />
					<input type="hidden" id="toLng" />
				</div>
				<div class="row col-xs-12">
					<div id="toMap" class="col-xs-12" style="height: 60vh;"></div>
				</div>
			</div>
		</div>
		<div class="row" style="height: 2vh;">
			&nbsp;
		</div>
		<div class="row" style="text-align: center">
				<input type="checkbox" id="notransfer" name="notransfer" >
				<label for="notransfer">I do not want to switch buses</label>
		</div>
		<div class="row" style="height: 15vh">
			<a href="javascript:findWay();" class="btn btn-default btn-lg btn-block">Find me a way</a>
		</div>
		<div class="row" id="results">
			&nbsp;
		</div>
	</div>
	<script type="text/javascript" src="../resources/static/js/jquery-3.1.1.js"></script>
	<script type="text/javascript" src="../resources/static/js/bootstrap.js"></script>
    <script type="text/javascript" src='http://maps.google.com/maps/api/js?key=AIzaSyBfl7YOLYDHwoQf-gOl3PZX8JHArgycGq4&sensor=false&libraries=places'></script>
    <!-- <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBfl7YOLYDHwoQf-gOl3PZX8JHArgycGq4&libraries=places&callback=initMap" async defer></script> -->
	<script type="text/javascript" src="../resources/static/js/locationpicker.jquery.js"></script>
	<script type="text/javascript" src="../resources/static/js/fromTo.js"></script>
	<script type="text/javascript">
		/* $('#locationTest').locationpicker(); */
		
		var defLat=40.5008186;
		var defLng=-74.44739909999998;
		
		if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
	       	defLat = position.coords.latitude,
	       	defLng = position.coords.longitude
			$('#fromMap').locationpicker({
			    location: {
			        latitude: defLat,
			        longitude: defLng
			    },
			    radius: 0,
			    zoom: 17,
			    inputBinding: {
			        latitudeInput: $('#fromLat'),
			        longitudeInput: $('#fromLng'),
			        locationNameInput: $('#fromAddress')
			    },
			    enableAutocomplete: true,
			    autocompleteOptions: {
			        componentRestrictions: {country: 'us'}
			    }
			});
        }, function() {
          //handleLocationError(true, infoWindow, map.getCenter());
          alert("Could not fetch current location. Please enter manually");
		$('#fromMap').locationpicker({
		    location: {
		        latitude: defLat,
		        longitude: defLng
		    },
		    radius: 0,
		    zoom: 17,
		    inputBinding: {
		        latitudeInput: $('#fromLat'),
		        longitudeInput: $('#fromLng'),
		        locationNameInput: $('#fromAddress')
		    },
		    enableAutocomplete: true,
		    autocompleteOptions: {
		        componentRestrictions: {country: 'us'}
		    }
		});
        });
      } else {
        // Browser doesn't support Geolocation
        //handleLocationError(false, infoWindow, map.getCenter());
          alert("Could not fetch current location. Please enter manually");
		$('#fromMap').locationpicker({
		    location: {
		        latitude: defLat,
		        longitude: defLng
		    },
		    radius: 0,
		    zoom: 17,
		    inputBinding: {
		        latitudeInput: $('#fromLat'),
		        longitudeInput: $('#fromLng'),
		        locationNameInput: $('#fromAddress')
		    },
		    enableAutocomplete: true,
		    autocompleteOptions: {
		        componentRestrictions: {country: 'us'}
		    }
		});
      }
		
		$('#toMap').locationpicker({
		    location: {
		        latitude: 40.5008186,
		        longitude: -74.44739909999998
		    },
		    radius: 0,
		    zoom: 17,
		    inputBinding: {
		        latitudeInput: $('#toLat'),
		        longitudeInput: $('#toLng'),
		        locationNameInput: $('#toAddress')
		    },
		    enableAutocomplete: true,
		    autocompleteOptions: {
		        componentRestrictions: {country: 'us'}
		    }
		});
	</script>
</body>
</html>