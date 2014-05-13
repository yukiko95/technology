var windowHeight = window.innerHeight - 100;
var windowWidth = window.innerWidth - 100;
var clickFirstRectangle = false;
var countRectangles = 20;
var countDeleted = 0;
var deleted = {};
var all = [];
var maxRectangleWidth = 250;
var maxRectangleHeight = 250;

function createRectangle(i) {
    var rectangleWidth = Math.round(maxRectangleWidth * Math.random() + 10);
    var rectangleHeight = Math.round(maxRectangleHeight * Math.random() + 10);
    var x1 = Math.round((windowWidth - rectangleWidth) * Math.random());
    var y1 = Math.round((windowHeight - rectangleHeight) * Math.random());
    var rectangle = document.createElement("div");
    rectangle.id = i;
    rectangle.style.background = '#' + Math.round(0xDDDDDD * Math.random() + 0x111111).toString(16);
    rectangle.style.width = rectangleWidth + "px";
    rectangle.style.height = rectangleHeight + "px";
    rectangle.style.top = y1 + "px";
    rectangle.style.left = x1 + "px";
    rectangle.style.position = "absolute";
    all.push({'x1': x1, 'y1': y1, 'x2': x1 + rectangleWidth, 'y2': y1 + rectangleHeight});
    deleted[i] = false;
    return rectangle;
}

function startGame() {
    var gameWindow = document.getElementById("game_window");
    gameWindow.style.border = "1px solid black";
    gameWindow.style.height = windowHeight + "px";
    gameWindow.style.width = windowWidth + "px";
    for (var i = 0; i < countRectangles; i++) {
        gameWindow.appendChild(createRectangle(i));
        $("#" + i).on("click", function() {
            if (!clickFirstRectangle) {
                clickFirstRectangle = true;
                startTime = Date.now();
            }
            $('#' + $(this).attr('id')).remove();
            deleted[$(this).attr('id')] = true;
            countDeleted += 1;
            if (!OK()) {
                alert("Time: " + ((Date.now() - startTime) / 1000.0) + " secs.\nYou are removed " + countDeleted + " rectangles\n");
                location.reload();
            }
        });
    }
}

function OK() {
    for (var i = 0; i < countRectangles; i++)
        for (var j = 0; j < countRectangles; j++)
            if (!deleted[i] && !deleted[j] && i != j) {
                var xx = Math.abs(all[i]['x1'] - all[j]['x1']);
                var yy = Math.abs(all[i]['y1'] - all[j]['y1']);
                var w = 0;
                if (all[i]['x1'] < all[j]['x2'])
                    w = all[i]['x2'] - all[i]['x1'];
                else
                    w = all[j]['x2'] - all[j]['x1'];
                var h = 0;
                if (all[i]['y1'] < all[j]['y2'])
                    h = all[i]['y2'] - all[i]['y1'];
                else
                    h = all[j]['y2'] - all[j]['y1'];
                if (xx < w && yy < h) {
                    return true;
                }
            }
    return false;
}