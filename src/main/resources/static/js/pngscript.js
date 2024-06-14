document.addEventListener('DOMContentLoaded', function() {
    var games = gameData;
    for (var i = 0; i < games.length; i++) {
        var game = games[i];
        var photoUrl = '/images/game' + (i + 1) + '.png'; 
        var img = document.getElementsByClassName('game-photo')[i];
        img.id = 'game-photo-' + i;
        img.src = photoUrl;
    }
});
