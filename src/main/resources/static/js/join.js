$(".key").on("keyup", function(event) {
  if (event.keyCode === 13) {
    event.preventDefault();
    $(".checkKey").triggerHandler("click");
  } else {
    if (this.value) {
      $(".keyShow").css("display", "inline-block");
    } else {
      $(".keyShow").hide();
    }
  }
}).focus();

