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
				<li><a href="#">Point-to-Point Directions</a></li>
				<li><a href="../analytics/currentGraphForm">Current Status</a></li>
				<li><a href="../analytics/hourlyChartForm">Hourly Analytics: Routewise</a></li>
				<li><a href="../analytics/stopwiseChartForm">Weekly Analytics: Stopwise</a></li>
				<li class="active"><a href="#"><b>#</b>RUBuses</a></li>
			</ul>
	    </div>
	  </div>
	</nav>
	
	<div class="container">
		<script src="//assets.juicer.io/embed.js" type="text/javascript"></script>
		<link href="//assets.juicer.io/embed.css" media="all" rel="stylesheet" type="text/css" />
		<ul class="juicer-feed" data-feed-id="rutgers"></ul>
	</div>
	<script type="text/javascript" src="../resources/static/js/jquery-3.1.1.js"></script>
	<script type="text/javascript" src="../resources/static/js/bootstrap.js"></script>
</body>
</html>