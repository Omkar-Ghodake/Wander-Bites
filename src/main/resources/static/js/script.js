// loc = document.querySelector(".loc-access");
acc = document.querySelector(".dropdown-content");
upd = document.querySelector(".dropdown-content-update");
function drop() {
  acc.classList.toggle("show");
}
function dropupdate() {
  upd.classList.toggle("show");
}
// loc.addEventListener("click", function (event) {
//   if (confirm("Allow access?") == true) {
//     event.preventDefault();
//     alert("Access granted");
//   } else {
//     alert("Access denied");
//   }
//   //alert("click")
// });

// button = document.querySelector(".reg-button");
// form=document.querySelector('.reg')
// in1 = document.querySelector(".input1");
// in2 = document.querySelector(".input2");
// in3 = document.querySelector(".input3");
// in4 = document.querySelector(".input4");
// in5 = document.querySelector(".input5");

// console.log(button, in1, in2, in3, in4, in5);

//   button.addEventListener("click", function (event) {
//     event.preventDefault();
//     if (
//         in1.value.trim() == "" ||
//         in2.value.trim() == "" ||
//         in3.value.trim() == "" ||
//         in4.value.trim() == "" ||
//         in5.value.trim() == ""
//     ) {
//         alert("Enter all details");
//         // event.preventDefault();
//     } else if (!/^\d+$/.test(in2.value.trim())) {
//         alert("Enter valid contact number");
//         // event.preventDefault();
//     } else {
//         alert("Registered successfully!");
//     }
// });

// document.addEventListener("DOMContentLoaded", function () {
//   let button = document.querySelector(".reg-button");
//   let in1 = document.querySelector(".input1");
//   let in2 = document.querySelector(".input2");
//   let in3 = document.querySelector(".input3");
//   let in4 = document.querySelector(".input4");
//   let in5 = document.querySelector(".input5");

//   console.log(button, in1, in2, in3, in4, in5);

//   button.addEventListener("click", function (event) {
//     event.preventDefault();
//     if (
//       in1.value.trim() == "" ||
//       in2.value.trim() == "" ||
//       in3.value.trim() == "" ||
//       in4.value.trim() == "" ||
//       in5.value.trim() == ""
//     ) {
//       alert("Enter all details");
//     } else if (!/^\d+$/.test(in2.value.trim())) {
//       alert("Enter valid contact number");
//     } else {
//       alert("Registered successfully!");
//     }
//   });
// });
