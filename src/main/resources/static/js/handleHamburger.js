function handleHamburger() {
  bar1 = document.getElementById("bar1");
  bar2 = document.getElementById("bar2");
  bar3 = document.getElementById("bar3");
  menu = document.getElementById("menuList");
  outerMenu = document.getElementById("outerMneu");

  if (bar2.classList.contains("hidden")) {
    bar2.classList.remove("hidden");
    bar1.classList.remove("absolute");
    bar1.classList.remove("rotate-45");
    bar1.classList.remove("duration-200");
    bar3.classList.remove("-rotate-45");
    bar3.classList.remove("duration-200");

    bar1.classList.add("rotate-0");
    bar1.classList.add("duration-200");
    bar3.classList.add("rotate-0");
    bar3.classList.add("duration-200");

    menu.classList.remove("duration-500");
    menu.classList.add("translate-x-[100vw]");
    menu.classList.add("duration-500");
    setTimeout(() => {
      outerMenu.classList.remove("h-[93vh]");
    }, 400);
  } else {
    bar1.classList.remove("rotate-0");
    bar1.classList.remove("duration-200");
    bar3.classList.remove("rotate-0");
    bar3.classList.remove("duration-500");

    bar2.classList.add("hidden");
    bar1.classList.add("absolute");
    bar1.classList.add("rotate-45");
    bar1.classList.add("duration-200");
    bar3.classList.add("-rotate-45");
    bar3.classList.add("duration-200");

    outerMenu.classList.add("h-[93vh]");
    menu.classList.remove("duration-500");
    menu.classList.remove("translate-x-[100vw]");
    menu.classList.add("duration-500");
  }
}
