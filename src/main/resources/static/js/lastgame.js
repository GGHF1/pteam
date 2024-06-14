document.addEventListener('DOMContentLoaded', function() {
    var gamePhoto = document.querySelector('.game-photo');
    if (gameData && gameData.length > 0) {
        var lastGame = gameData[gameData.length - 1]; // Get the last game
        gamePhoto.src = '/images/game' + lastGame.id + '.png';
    }
});