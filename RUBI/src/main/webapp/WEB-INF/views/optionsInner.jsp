<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.kabra.mayur.entity.DirectionStepBus"%>
<%@page import="com.kabra.mayur.entity.DirectionStepWait"%>
<%@page import="com.kabra.mayur.entity.DirectionSet"%>
<%@page import="com.kabra.mayur.entity.DirectionStepWalk"%>
<%@page import="com.kabra.mayur.entity.DirectionStep"%>
<%@page import="java.util.List"%>
<%
	List<DirectionSet> directionSets = (List<DirectionSet>) request.getAttribute("options");
	SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
	//for (DirectionSet directionSet : directionSets) {
	for (int outerCount = 0 ; outerCount < directionSets.size() ; outerCount++) {
		DirectionSet directionSet = directionSets.get(outerCount);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(directionSet.getEndTime());
%>
<div class="panel-group">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" href="#collapse<%=outerCount%>">Option <%=outerCount+1%> : 
				You will reach at <%=dateFormat.format(calendar.getTime())%><br />
					<img title="Total Time" src="../resources/static/img/time.png" style="height: 2em"> <b style="font-size: large;"> <%=directionSet.getTotalTime() / (1000 * 60)%> minutes =</b> 
					<img title="In-Transit Time" src="../resources/static/img/bus.png" style="height: 2em"> <%=directionSet.getBusRouteTime() / (1000 * 60)%> minutes 
					<%if(directionSet.getBusChangeCount()-1>0){
						%>
							<b>(</b><img title="Number of transfers" src="../resources/static/img/transfer.png" style="height: 2em"> <%=directionSet.getBusChangeCount()-1%><b>)</b>
						<%
					}%>
					<b>+</b> 
					<img title="Walking Time" src="../resources/static/img/walk.png" style="height: 2em"> <%=directionSet.getWalkingTime() / (1000 * 60)%> minutes <b>+</b>
					<img title="Waiting Time" src="../resources/static/img/wait.png" style="height: 2em"> <%=directionSet.getWaitingTime() / (1000 * 60)%> minutes
				</a>
			</h4>
		</div>
		<div id="collapse<%=outerCount%>" class="panel-collapse collapse">
			<ul class="list-group">
				<%
					List<DirectionStep> fastestDirections = directionSet.getStepByStepDirections();

						for (int i = 0; i < fastestDirections.size(); i++) {
							DirectionStep directionStep = fastestDirections.get(i);
							if (directionStep instanceof DirectionStepWalk) {
								DirectionStepWalk directionStepWalk = (DirectionStepWalk) directionStep;
				%>
				<li class="list-group-item">
					<div class="panel-group" style="margin-bottom: 0px;">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" href="#innercollapse<%=outerCount%><%=i%>">
									<img src="../resources/static/img/walk.png" style="height: 2em"> <%=directionStepWalk.getTotalTime() / (1000 * 60)%> minutes: 
									Walk from <%=directionStepWalk.getStartStr()%> towards <b><%=directionStepWalk.getEndStr()%></b>
									</a>
								</h4>
							</div>
							<div id="innercollapse<%=outerCount%><%=i%>" class="panel-collapse collapse">
								<div class="panel-body">
									<iframe
										src="https://www.google.com/maps/embed/v1/directions?key=AIzaSyBfl7YOLYDHwoQf-gOl3PZX8JHArgycGq4&origin=<%=directionStepWalk.getFromLat()%>,<%=directionStepWalk.getFromLng()%>&destination=<%=directionStepWalk.getToLat()%>,<%=directionStepWalk.getToLng()%>&mode=walking"
										width="400" height="300" frameborder="0" style="border: 0"
										allowfullscreen></iframe>
								</div>
							</div>
						</div>
					</div>
				</li>
				<%
					} else if (directionStep instanceof DirectionStepWait) {
								DirectionStepWait directionStepWait = (DirectionStepWait) directionStep;
				%>
				
				<li class="list-group-item">
					<div class="panel-group" style="margin-bottom: 0px;">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<img src="../resources/static/img/wait.png" style="height: 2em"> <%=directionStepWait.getTotalTime() / (1000 * 60)%> minutes: 
									Wait for the bus number <b><%=directionStepWait.getVehicleNumber()%></b>
									for route <b><%=directionStepWait.getRouteName()%></b>
								</h4>
							</div>
						</div>
					</div>
				</li>
				<%
					} else if (directionStep instanceof DirectionStepBus) {
						DirectionStepBus directionStepBus = (DirectionStepBus) directionStep;
				%>
				
				<li class="list-group-item">
					<div class="panel-group" style="margin-bottom: 0px;">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<img src="../resources/static/img/bus.png" style="height: 2em"> <%=directionStepBus.getTotalTime()/(1000*60) %> minutes: 
									Get off at <b><%=directionStepBus.getEndStr() %></b> 
								</h4>
							</div>
						</div>
					</div>
				</li>
				<%
					}
						}
				%>
			</ul>
		</div>
	</div>
</div>
<%
}
%>
