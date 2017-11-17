var allFilters = document.getElementsByClassName("filter");
var allInfos = document.getElementsByTagName("p");
var wearPercentage = document.getElementsByClassName("wear-percentage");
for (let i = 0; i < allFilters.length; i++) {
    if (wearPercentage[i].innerHTML >= 100) {
        allFilters[i].className += ' red';
    }
    else if (wearPercentage[i].innerHTML >= 80) {
        allFilters[i].className += ' yellow';
    }
    allFilters[i].onclick = function() {
        var display = allInfos[i].style.display;
        allInfos[i].style.display = display === "block" ? "none" : "block";
        allFilters[i].classList.toggle("shadow");
        for (let pidr = 0; pidr < 10; pidr++) {
            if (pidr === i) {
                continue;
            }
            allFilters[pidr].classList.remove("shadow");
            allInfos[pidr].style.display = "none";
        }
    }
}