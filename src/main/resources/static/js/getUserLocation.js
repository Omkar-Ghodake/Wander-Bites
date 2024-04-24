function getLocation(e) {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition);
  } else {
    msg.innerHTML = "Geolocation is not supported by this browser.";
  }
}

function showPosition(position) {
  lat = document.getElementById("lat");
  long = document.getElementById("long");
  lat.value = position.coords.latitude;
  long.value = position.coords.longitude;
  setTimeout(sendLocation, 500);
}

function sendLocation() {
  document.getElementById("sendLocation").click();
}

if (document.getElementById("lat").value == "" && navigator.geolocation) {
  getLocation();
}

console.log(document.getElementById("lat").value);
