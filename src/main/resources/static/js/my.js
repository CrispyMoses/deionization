var allFilters = document.getElementsByClassName("filter");
var allInfos = document.getElementsByTagName("p");
for (let i = 0; i < allFilters.length; i++) {
    allFilters[i].onclick = function() {
        var display = allInfos[i].style.display;
        allInfos[i].style.display = display === "block" ? "none" : "block";
        for (let pidr = 0; pidr < 10; pidr++) {
            if (pidr === i) {
                continue;
            }
            allInfos[pidr].style.display = "none";
        }
    }
}