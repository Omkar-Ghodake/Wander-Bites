function handleUpdateMenuBox() {
  element = document.getElementById("updateMenuItemBox");
  element.classList.contains("hidden")
    ? element.classList.remove("hidden")
    : element.classList.add("hidden");
}
function updateMenuItem(n) {
  handleUpdateMenuBox();
  document.getElementById("formDisheId").value = document.getElementById(
    "disheId" + n
  ).textContent;

  document.getElementById("formDisheName").value = document.getElementById(
    "disheName" + n
  ).textContent;

  document.getElementById("formDisheDescription").value =
    document.getElementById("disheDescription" + n).textContent;

  document.getElementById("formDishePrice").value = document.getElementById(
    "dishePrice" + n
  ).textContent;

  document.getElementById("formDisheDiscount").value = document.getElementById(
    "disheDiscount" + n
  ).textContent;

  document.getElementById("formDisheCategory").value = document.getElementById(
    "disheCategory" + n
  ).textContent;

  if (document.getElementById("dishePhoto" + n).textContent.length > 0) {
    document.getElementById("formDishePhoto").src =
      "data:image/jpeg;base64," +
      document.getElementById("dishePhoto" + n).textContent;
  }
}
