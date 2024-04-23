function handlePass() {
  let pass = document.getElementById("pass");
  let show = document.getElementById("show");
  let hide = document.getElementById("hide");
  if (pass.type == "password") {
    pass.type = "text";
    show.classList.add("hidden");
    hide.classList.remove("hidden");
  } else {
    pass.type = "password";
    show.classList.remove("hidden");
    hide.classList.add("hidden");
  }
}

function handleCPass() {
  let pass = document.getElementById("cpass");
  let show = document.getElementById("cshow");
  let hide = document.getElementById("chide");
  if (pass.type == "password") {
    pass.type = "text";
    show.classList.add("hidden");
    hide.classList.remove("hidden");
  } else {
    pass.type = "password";
    show.classList.remove("hidden");
    hide.classList.add("hidden");
  }
}
