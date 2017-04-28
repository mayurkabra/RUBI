<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="../resources/static/css/bootstrap.css" />
<title>Insert title here</title>
<style>
.bar {
	fill: steelblue;
}

.bar:hover {
	fill: brown;
}

.axis--x path {
	display: none;
}
</style>
</head>
<body>
	<!-- <nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Rutgers University Buses Insight</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1"></div>
	</div>
	</nav> -->
	<div class="container">
		<svg width="960" height="500"></svg>
	</div>
	<!-- <footer class="footer">
	<div class="container">
		<p class="text-muted">Rutgers University Buses Insight</p>
	</div>
	</footer> -->
	<script type="text/javascript"
		src="../resources/static/js/jquery-3.1.1.js"></script>
	<script type="text/javascript"
		src="../resources/static/js/bootstrap.js"></script>
	<script src="https://d3js.org/d3.v4.min.js"></script>
	<script>

var svg = d3.select("svg"),
    margin = {top: 20, right: 20, bottom: 30, left: 40},
    width = +svg.attr("width") - margin.left - margin.right,
    height = +svg.attr("height") - margin.top - margin.bottom;

var x = d3.scaleBand().rangeRound([0, width]).padding(0.1),
    y = d3.scaleLinear().rangeRound([height, 0]);

var g = svg.append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

d3.tsv("../analytics/hourlyChartTSV?route=<%=request.getAttribute("route")%>&stop=<%=request.getAttribute("stop")%>&from=<%=request.getAttribute("from")%>&to=<%=request.getAttribute("to")%>", function(d) {
  d.Number_of_Vehicles = +d.Number_of_Vehicles;
  return d;
}, function(error, data) {
  if (error) throw error;

  x.domain(data.map(function(d) { return d.Hour_of_Day; }));
  y.domain([0, d3.max(data, function(d) { return d.Number_of_Vehicles; })]);

  g.append("g")
      .attr("class", "axis axis--x")
      .attr("transform", "translate(0," + height + ")")
      .call(d3.axisBottom(x));

  g.append("g")
	.attr("class", "axis axis--y")
	.call(d3.axisLeft(y))
	.append("text")
	.attr("transform", "rotate(-90)")
	.attr("y", 6)
	.attr("dy", "0.71em")
	.attr("text-anchor", "end")
	.text("Number of Vehicles");

  g.selectAll(".bar")
    .data(data)
    .enter().append("rect")
    .attr("class", "bar")
    .attr("x", function(d) { return x(d.Hour_of_Day); })
    .attr("y", function(d) { return y(d.Number_of_Vehicles); })
    .attr("width", x.bandwidth())
    .attr("height", function(d) { return height - y(d.Number_of_Vehicles); });
  
  g.append("text")      // text label for the x axis
  .attr("x", "50%" )
  .attr("y",  "98%" )
  .style("text-anchor", "middle")
  .text("Hour")
});

</script>
</body>
</html>