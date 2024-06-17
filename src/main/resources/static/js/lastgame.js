document.addEventListener('DOMContentLoaded', function() {
    var gamePhoto = document.querySelector('.game-photo');
    if (gameData && gameData.length > 0) {
        // Get the last game
        var lastGame = gameData[gameData.length - 1]; 
        gamePhoto.src = '/images/game' + lastGame.id + '.png';
    }
});