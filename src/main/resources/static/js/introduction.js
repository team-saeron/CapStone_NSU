const monthly = document.querySelector("#monthlyRent_value");
const deposit=document.querySelector("#deposit_value");
const depositValue=document.querySelector(".deposit_value");
const monthlyRentValue=document.querySelector(".monthlyRent_value");


function monthlyValue(){
monthly.innerText = `${monthlyRentValue.value}`;
}

function depositValue(){
deposit.innerText = `${depositValue.value}`;
}

function init(){
monthlyValue();
depositValue();
monthly.addEventListener("change",monthlyValue);
deposit.addEventListener("change",depositValue);

}

init();