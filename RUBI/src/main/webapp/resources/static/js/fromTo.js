function findWay(){
	var dto = { fromLat: $('#fromLat').val(), fromLng: $('#fromLng').val(), toLat: $('#toLat').val(), toLng: $('#toLng').val(), notransfer: $('#notransfer').is(':checked') };
	$.ajax({
		method: "POST",
		url: "../direction/findWayAjax",
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

function showPosition(position) {
	$("#fromLat").val(position.lat);
	$("#fromLon").val(position.lng);
}

function locationTypeSwitch(){
	var a = $('.location-type-not-selected').first();
	var b = $('.location-type-selected').first();
	a.addClass('location-type-selected');
	a.removeClass('location-type-not-selected');
	b.addClass('location-type-not-selected');
	b.removeClass('location-type-selected');
}

function fillLocationForm(lat, lng){
	var locationType = $('.location-type-selected').first();
	if(locationType.val()=="From"){
		$('#fromLat').val(lat);
		$('#fromLon').val(lng);
		locationTypeSwitch();
	} else{
		$('#toLat').val(lat);
		$('#toLon').val(lng);
		locationTypeSwitch();
	}
	locationType.addClass('info-complete');
	locationType.removeClass('info-incomplete');
}

function initMap() {
	
	var autoTest = new google.maps.places.Autocomplete(document.getElementById('testAutocomplete'));
	
    var map = new google.maps.Map(document.getElementById('map'), {
      center: {lat: 40.4988, lng: -74.450},
      zoom: 13
    });
    var card = document.getElementById('pac-card');
    var input = document.getElementById('pac-input');
    var types = document.getElementById('type-selector');
    var strictBounds = document.getElementById('strict-bounds-selector');

    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(card);

    var autocomplete = new google.maps.places.Autocomplete(input);

    // Bind the map's bounds (viewport) property to the autocomplete object,
    // so that the autocomplete requests use the current map bounds for the
    // bounds option in the request.
    autocomplete.bindTo('bounds', map);

    var infowindow = new google.maps.InfoWindow({map: map});
    var infowindowContent = document.getElementById('infowindow-content');
    infowindow.setContent(infowindowContent);
    var marker = new google.maps.Marker({
      map: map,
      anchorPoint: new google.maps.Point(0, -29)
    });
    
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
          var pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude
          };

          infowindow.setPosition(pos);
          infowindow.setContent('');
          map.setCenter(pos);
          showPosition(pos);
        }, function() {
          handleLocationError(true, infoWindow, map.getCenter());
        });
      } else {
        // Browser doesn't support Geolocation
        handleLocationError(false, infoWindow, map.getCenter());
      }

    autocomplete.addListener('place_changed', function() {
      infowindow.close();
      marker.setVisible(false);
      var place = autocomplete.getPlace();
      if (!place.geometry) {
        // User entered the name of a Place that was not suggested and
        // pressed the Enter key, or the Place Details request failed.
        window.alert("No details available for input: '" + place.name + "'");
        return;
      }
      //alert(place.geometry.location);
      /*$('#toLat').val(place.geometry.location.lat());
      $('#toLon').val(place.geometry.location.lng());*/
      fillLocationForm(place.geometry.location.lat(), place.geometry.location.lng());
      // If the place has a geometry, then present it on a map.
      if (place.geometry.viewport) {
        map.fitBounds(place.geometry.viewport);
      } else {
        map.setCenter(place.geometry.location);
        map.setZoom(17);  // Why 17? Because it looks good.
      }
      marker.setPosition(place.geometry.location);
      marker.setVisible(true);

      var address = '';
      if (place.address_components) {
        address = [
          (place.address_components[0] && place.address_components[0].short_name || ''),
          (place.address_components[1] && place.address_components[1].short_name || ''),
          (place.address_components[2] && place.address_components[2].short_name || '')
        ].join(' ');
      }

      infowindowContent.children['place-icon'].src = place.icon;
      infowindowContent.children['place-name'].textContent = place.name;
      infowindowContent.children['place-address'].textContent = address;
      infowindow.open(map, marker);
    });

    // Sets a listener on a radio button to change the filter type on Places
    // Autocomplete.
    function setupClickListener(id, types) {
      var radioButton = document.getElementById(id);
      radioButton.addEventListener('click', function() {
        autocomplete.setTypes(types);
      });
    }

    setupClickListener('changetype-all', []);
    setupClickListener('changetype-address', ['address']);
    setupClickListener('changetype-establishment', ['establishment']);
    setupClickListener('changetype-geocode', ['geocode']);

    document.getElementById('use-strict-bounds')
        .addEventListener('click', function() {
          console.log('Checkbox clicked! New state=' + this.checked);
          autocomplete.setOptions({strictBounds: this.checked});
        });
  }