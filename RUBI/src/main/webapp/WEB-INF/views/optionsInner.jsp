<%@page import="com.kabra.mayur.entity.DirectionStepBus"%>
<%@page import="com.kabra.mayur.entity.DirectionStepWait"%>
<%@page import="com.kabra.mayur.entity.DirectionSet"%>
<%@page import="com.kabra.mayur.entity.DirectionStepWalk"%>
<%@page import="com.kabra.mayur.entity.DirectionStep"%>
<%@page import="java.util.List"%>
<%
	List<DirectionSet> directionSets = (List<DirectionSet>) request.getAttribute("options");
	//for (DirectionSet directionSet : directionSets) {
	for (int outerCount = 0 ; outerCount < directionSets.size() ; outerCount++) {
		DirectionSet directionSet = directionSets.get(outerCount);
%>
<div class="panel-group">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" href="#collapse<%=outerCount%>">Option <%=outerCount+1%> : 
				You will reach at <%=directionSet.getEndTime()%> PM<br />
					Total Time (<%=directionSet.getTotalTime() / (1000 * 60)%> minutes) = 
					Bus Time (<%=directionSet.getBusRouteTime() / (1000 * 60)%> minutes) + 
					Walk Time (<%=directionSet.getWalkingTime() / (1000 * 60)%> minutes) + 
					Waiting Time (<%=directionSet.getWaitingTime() / (1000 * 60)%> minutes)
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
									<a data-toggle="collapse" href="#innercollapse<%=outerCount%><%=i%>">Walk
										from <%=directionStepWalk.getStartStr()%> towards <%=directionStepWalk.getEndStr()%>
										(<%=directionStepWalk.getTotalTime() / (1000 * 60)%> minutes)
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
				<li class="list-group-item">Wait for <%=directionStepWait.getTotalTime() / (1000 * 60)%>
					minutes till the bus number <%=directionStepWait.getVehicleNumber()%>
					for route <%=directionStepWait.getRouteName()%> arrives
				</li>
				<%
					} else if (directionStep instanceof DirectionStepBus) {
						DirectionStepBus directionStepBus = (DirectionStepBus) directionStep;
				%>
				<li class="list-group-item">Get off at <%=directionStepBus.getEndStr() %> after <%=directionStepBus.getTotalTime()/(1000*60) %> minutes
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
