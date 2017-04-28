<%@page import="com.kabra.mayur.entity.BusStop"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="../resources/static/css/bootstrap.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<title>RUBI</title>
</head>
<body>
<%
Map<String, String> busStopMap = (Map<String, String>)request.getAttribute("busStopMap");
%>
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
				<li><a href="../first/parameterTest">Point-to-Point Directions</a></li>
				<li><a href="../analytics/currentGraphForm">Current Status</a></li>
				<li><a href="../analytics/hourlyChartForm">Hourly Analytics: Routewise</a></li>
				<li class="active"><a href="#">Weekly Analytics: Stopwise</a></li>
				<li><a href="../analytics/hashtag"><b>#</b>RUBuses</a></li>
			</ul>
	    </div>
	  </div>
	</nav>
	
	<div class="container">
		<div class="row">
			<form class="form-horizontal">
			  <fieldset>
			    <legend>Stopwise Weekly Analytics</legend>
			    <div class="form-group">
					<label for="from" class="col-lg-2 control-label">From</label>
					<div class="col-lg-10">
						<input type="text" id="from" name="from" class="form-control" >
					</div>
				</div>
				<div class="form-group">
					<label for="to" class="col-lg-2 control-label">To</label>
					<div class="col-lg-10">
						<input type="text" id="to" name="to" class="form-control" >
					</div>
			    </div>
			    <div class="form-group">
			      <label for="route" class="col-lg-2 control-label">Route</label>
			      <div class="col-lg-10">
			        <select class="form-control" id="stop">
						<%
						for(String entry : busStopMap.keySet()){
							%>
							<option value="<%=busStopMap.get(entry)%>"><%=entry %></option>
							<%
						}
						%>
			        </select>
			    	</div>
			    </div>
			    <!-- <div class="form-group">
			      <div class="col-lg-10 col-lg-offset-2">
			        <button type="reset" class="btn btn-default">Cancel</button>
			        <button type="submit" class="btn btn-primary">Submit</button>
			      </div>
			    </div> -->
			  </fieldset>
			</form>
		</div>
		<div class="row" style="height: 2vh;">
			&nbsp;
		</div>
		<div class="row" style="height: 15vh;">
			<a href="javascript:fetchChart();" class="btn btn-default btn-lg btn-block">Show Trends</a>
		</div>
		<div class="row" id="results">
			&nbsp;
		</div>
	</div>
	<script type="text/javascript" src="../resources/static/js/jquery-3.1.1.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script type="text/javascript" src="../resources/static/js/bootstrap.js"></script>
    <script src="http://d3js.org/d3.v3.js"></script>
	<script type="text/javascript" src="http://colorbrewer2.org/export/colorbrewer.js"></script>
	<script>
  $( function() {
    var dateFormat = "mm/dd/yy",
      from = $( "#from" )
        .datepicker({
          defaultDate: "+1w",
          changeMonth: true,
          numberOfMonths: 3
        })
        .on( "change", function() {
          to.datepicker( "option", "minDate", getDate( this ) );
        }),
      to = $( "#to" ).datepicker({
        defaultDate: "+1w",
        changeMonth: true,
        numberOfMonths: 3
      })
      .on( "change", function() {
        from.datepicker( "option", "maxDate", getDate( this ) );
      });
    
    $("#from").datepicker('setDate', new Date());
    $("#to").datepicker('setDate', new Date());
 
    function getDate( element ) {
      var date;
      try {
        date = $.datepicker.parseDate( dateFormat, element.value );
      } catch( error ) {
        date = null;
      }
 
      return date;
    }
  } );
  
  function fetchChart(){
	  var dto = { from: $('#from').val(), to: $('#to').val(), stop: $('#stop').val() };
		$.ajax({
			method: "POST",
			url: "../analytics/stopwiseChart",
			data: dto,
			success: function( msg ) {
				//alert( msg );
				$('#results').html(msg);
				$('html, body').animate({
					scrollTop: $("#results").offset().top
				}, 1000);
			},
			error:function( msg ) {
				alert( dto );
			}
		});
  }
  </script>
</body>
</html>