var game = {};

game.tick = function () {
    // sphere.position.x += (0.05 * DIR.x);
    // sphere.position.y += (0.05 * DIR.y);
    // sphere.position.z += (0.05 * DIR.z);
    // camera.position.x = sphere.position.x;
    // camera.position.z = sphere.position.z;

}

game.appStart = function () {
    web.toggleElement("VIEW_CANVAS", false)
    web.toggleElement("VIEW_GAMES", false)

}
window.onload = game.appStart;

game.changeView = function (view) {
    toggleElement("VIEW_SERVERS", view == "VIEW_SERVERS")
    toggleElement("VIEW_GAMES", view == "VIEW_GAMES")
    toggleElement("VIEW_CANVAS", view == "VIEW_CANVAS")
    if (view == "VIEW_CANVAS") {
        baby.setup3D();
        // var t = setInterval(tick, 1000);
    }
}

game.toggleElement = function (id, toggle) {
    if (toggle) {
        document.getElementById(id).style.display = "block"
        document.getElementById(id).style.visibility = "visible";
    } else {
        document.getElementById(id).style.visibility = "hidden";
        document.getElementById(id).style.display = "none"
    }
}

window.addEventListener("keydown", function (evt) {
    // Press space key to fire
    if (evt.keyCode === 32) {
        // gunshot.play();
        game.playPew();
    }
});

game.playPew = function () {
    GSound.playSound("sounds/pew.mp3")

}
