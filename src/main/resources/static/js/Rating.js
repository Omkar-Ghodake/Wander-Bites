function handleRating(n) {
  for (let i = 1; i <= 5; i++) {
    if (n >= i) {
      document.getElementById("star" + i).classList.add("c");
    } else {
      document.getElementById("star" + i).classList.remove("c");
    }
  }
  document.getElementById("rating").value = n;
}

function handleFoodtruckMenu(div) {
  if (div == "menu") {
    document.getElementById("menu").classList.remove("hidden");
    document.getElementById("menuBtn").classList.contains("bg-gray-400")
      ? ""
      : document.getElementById("menuBtn").classList.add("bg-gray-400");

    document.getElementById("gallery").classList.contains("hidden")
      ? ""
      : document.getElementById("gallery").classList.add("hidden");

    document.getElementById("galleryBtn").classList.contains("bg-gray-400")
      ? document.getElementById("galleryBtn").classList.remove("bg-gray-400")
      : "";
    document.getElementById("feedback").classList.contains("hidden")
      ? ""
      : document.getElementById("feedback").classList.add("hidden");
    document.getElementById("feedbackBtn").classList.contains("bg-gray-400")
      ? document.getElementById("feedbackBtn").classList.remove("bg-gray-400")
      : "";
  }
  if (div == "gallery") {
    document.getElementById("gallery").classList.remove("hidden");
    document.getElementById("galleryBtn").classList.contains("bg-gray-400")
      ? ""
      : document.getElementById("galleryBtn").classList.add("bg-gray-400");

    document.getElementById("menu").classList.contains("hidden")
      ? ""
      : document.getElementById("menu").classList.add("hidden");

    document.getElementById("menuBtn").classList.contains("bg-gray-400")
      ? document.getElementById("menuBtn").classList.remove("bg-gray-400")
      : "";
    document.getElementById("feedback").classList.contains("hidden")
      ? ""
      : document.getElementById("feedback").classList.add("hidden");
    document.getElementById("feedbackBtn").classList.contains("bg-gray-400")
      ? document.getElementById("feedbackBtn").classList.remove("bg-gray-400")
      : "";
  }
  if (div == "feedback") {
    document.getElementById("feedback").classList.remove("hidden");
    document.getElementById("feedbackBtn").classList.contains("bg-gray-400")
      ? ""
      : document.getElementById("feedbackBtn").classList.add("bg-gray-400");

    document.getElementById("menu").classList.contains("hidden")
      ? ""
      : document.getElementById("menu").classList.add("hidden");

    document.getElementById("menuBtn").classList.contains("bg-gray-400")
      ? document.getElementById("menuBtn").classList.remove("bg-gray-400")
      : "";
    document.getElementById("gallery").classList.contains("hidden")
      ? ""
      : document.getElementById("gallery").classList.add("hidden");
    document.getElementById("galleryBtn").classList.contains("bg-gray-400")
      ? document.getElementById("galleryBtn").classList.remove("bg-gray-400")
      : "";
  }
}

function handleFilterBox() {
  element = document.getElementById("filterBox");
  element.classList.contains("hidden")
    ? element.classList.remove("hidden")
    : element.classList.add("hidden");
}

function showCoverPhotoBtn() {
  document.getElementById("coverPhoto").classList.remove("hidden");
}
function closeCoverPhotoBtn() {
  document.getElementById("coverPhoto").classList.add("hidden");
}
function coverPhotoBtn() {
  element = document.getElementById("coverPhotoChange");
  element.classList.contains("hidden")
    ? element.classList.remove("hidden")
    : element.classList.add("hidden");
}

function handleAddMenuItem() {
  element = document.getElementById("addMenuItemBox");
  element.classList.contains("hidden")
    ? element.classList.remove("hidden")
    : element.classList.add("hidden");
}
