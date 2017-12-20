/**
 * Created by jason on 17-2-23.
 */
$(function() {

    var top = 7.470;
    var bottom = -13.072;
    var left = 94.436;
    var right = 141.55;
    var height = 678;
    var width = 1600;

    var colorCore = "#FF6347";
    //var colorCore = "#E74C3C";
    var colorCity1 = "#4876FF";
    //var colorCity1 = "#3498DB";
    //var colorCorner = "#FA5B33";
    var colorCorner = "#FF6138";
    var colorCity2 = "#3BE246";
    //var colorCity2 = "#00C23D";
    var colorFont2 = "#28992F";
    //var colorFont2 = "#008000";

    $(function (){

        var i = document.getElementById("Indonesia");
        var id = i.getContext("2d");

        var img = new Image();
        img.onload = function(){
            id.drawImage(img,0,0)
        };
        img.src = "/images/Indonesia.png";

        $("[data-toggle='popover']").popover();

    });

    function draw(city1, city2){
        var x1 = (city1.longi - left) / (right - left) * width;
        var y1 = (top - city1.lati) / (top - bottom) * height;
        var x2 = (city2.longi - left) / (right - left) * width;
        var y2 = (top - city2.lati) / (top - bottom) * height;

        var i = document.getElementById("Indonesia");
        var id = i.getContext("2d");
        id.fillStyle = colorCity1;
        id.beginPath();
        id.moveTo(x1, y1);
        id.lineTo(x2, y2);
        id.stroke();
    };

    function drawCore(core){
        var x = (core.longi - left) / (right - left) * width;
        var y = (top - core.lati) / (top - bottom) * height;

        var i = document.getElementById("Indonesia");
        var id = i.getContext("2d");
        //id.fillStyle = "#FF6347";
        id.fillStyle = colorCore;
        id.beginPath();
        id.arc(x, y, 10, 0, 2*Math.PI);
        id.fill();
    };

    function drawall(graph){
        for(var n = 0; n < graph.number; n++){
            draw(graph.cities[graph.core], graph.cities[n])
        }
    };

    function coreInfo(data){
        var title = "Core Information.";
        var info = "[CORE AREA] " + data.cities[data.core].name + "\n\n";
        for(var n = 0; n < data.number; n++){
            if(n == data.core) continue;
            info += "[TO] " + data.cities[n].name +" : " + data.distances[data.core][n] + "km\n"
        };

        $("#info").attr("title",title);
        $("#info").attr("data-content",info);
        $("[data-toggle='popover']").popover();
    };

    function drawDist(graph){
        for (var n = 1; n < graph.number; n++){
            draw(graph.cities[graph.prim[1][n]], graph.cities[n]);
        };
    };

    function drawCity(data){
        var x = (data.longi - left) / (right - left) * width;
        var y = (top - data.lati) / (top - bottom) * height;

        var i = document.getElementById("Indonesia");
        var id = i.getContext("2d");

        id.fillStyle = colorCity1;
        id.beginPath();
        id.arc(x, y, 6, 0, 2*Math.PI);
        id.fill();
    };

    function drawCity2(data){
        var x = (data.longi - left) / (right - left) * width;
        var y = (top - data.lati) / (top - bottom) * height;

        var i = document.getElementById("Indonesia");
        var id = i.getContext("2d");

        id.fillStyle = colorCity2;
        id.beginPath();
        id.arc(x, y, 6, 0, 2*Math.PI);
        id.fill();
    };

    function drawAllCities(graph){
        for (var n = 0; n < graph.number; n++){
            drawCity(graph.cities[n]);
        }
    };

    function drawAllCities2(graph){
        for (var n = 0; n < graph.number; n++){
            drawCity2(graph.cities[n]);
        }
    };

    function drawCorner(city){
        var x = (city.longi - left) / (right - left) * width;
        var y = (top - city.lati) / (top - bottom) * height;

        var i = document.getElementById("Indonesia");
        var id = i.getContext("2d");
        id.fillStyle = colorCorner;
        id.beginPath();
        id.arc(x, y, 10, 0, 2*Math.PI);
        id.fill();
    };

    function drawAllCorners(graph){

        var info = "[TRANSIT   AREAS] \n";

        for(var j = 1; j < graph.number; j++) {
            var count = 0;
            for(var k = 0; k < graph.number; k++) {
                if (graph.prim[1][k] == j) {
                    count++;
                }
                if (count > 1) {
                    drawCorner(graph.cities[j]);
                    info += graph.cities[j].name + "\n";
                    break;
                }
            }
        }
        var jj = 0;
        var countt = 0;
        for(var kk = 0; kk < graph.number; kk++) {
            if (graph.prim[1][kk] == jj) {
                countt++;
            }
            if (count > 2) {
                drawCorner(graph.cities[jj]);
                info += graph.cities[j].name + "\n";
                break;
            }
        }

        $("#info").attr("data-content",info);
        $("[data-toggle='popover']").popover();

    };

    $("#city").typeahead({
        source: function (query, process) {
            $.post("/cities", query,
                function (data, status) {
                    process(data);
                });
        }
    });

    $("#add").click(function(){
        var city = $("#city").val();
        if(city == "") return;
        $.post("/coor",
            {
                city: city
            },
            function(data, status){
                var x = (data.longi - left) / (right - left) * width;
                var y = (top - data.lati) / (top - bottom) * height;

                var i = document.getElementById("Indonesia");
                var id = i.getContext("2d");

                id.fillStyle = colorCity1;
                id.beginPath();
                id.arc(x, y, 6, 0, 2*Math.PI);
                id.fill();
            })
        $("#city").val("");
    });

    $("#navi").click(function(){
        $.get("/navi",
            function(data, status){
                var i = document.getElementById("Indonesia");
                var id = i.getContext("2d");

                var img = new Image();
                img.onload = function(){
                    id.drawImage(img,0,0);
                    drawall(data);
                    drawAllCities(data);
                    drawCore(data.cities[data.core]);
                    coreInfo(data);
                };
                img.src = "/images/Indonesia.png";
            }
        )
    });

    $("#dist").click(function(){
        $.get("/dist",
            function(data, status){
                var i = document.getElementById("Indonesia");
                var id = i.getContext("2d");

                var img = new Image();
                img.onload = function(){
                    id.drawImage(img,0,0);
                    drawDist(data);
                    drawAllCities2(data);
                    drawAllCorners(data);
                };
                img.src = "/images/Indonesia.png";
            }
        )
    });

    $("#name").click(function(){
        $.get("/name",
            function(data, status){
                var i = document.getElementById("Indonesia");
                var id = i.getContext("2d");
                id.textAlign = "center";
                id.fillStyle = colorFont2;
                //id.font = "48px serif";
                for(var n = 0; n < data.number; n++){
                    var x = (data.cities[n].longi - left) / (right - left) * width;
                    var y = (top - data.cities[n].lati) / (top - bottom) * height - 8;
                    id.fillText(data.cities[n].name, x, y);
                }
            }
        )
    });

    $("#reset").click(function(){
        window.location.href="/reset";
    });

});