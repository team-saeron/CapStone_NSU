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
$(".keyShow").on("click", function() {
  if ($(".key").attr("type") == "password") {
    $(".key").attr("type", "text");
    $($(this)).text("비밀번호 숨기기");
  } else {
    $(".key").attr("type", "password");
    $($(this)).text("비밀번호 표시");
  }
});
function extractHashtags() {
        const text = document.getElementById("text").value;
        const regex = /#\w+/g;
        const hashtags = text.match(regex);
        const hashtagsDiv = document.getElementById("hashtags");
        hashtagsDiv.innerHTML = "";
        if (hashtags) {
          hashtags.forEach(hashtag => {
            const circle = document.createElement("span");
            circle.className = "circle";
            circle.innerText = hashtag.substring(1);
            hashtagsDiv.appendChild(circle);
          });
        }
      }