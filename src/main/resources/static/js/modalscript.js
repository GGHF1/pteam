$(document).ready(function() {
    $('.info-btn').click(function() {
        var gameCard = $(this).closest('.game-card');
        var gameTitle = gameCard.find('.game-title').text();
        var gameDescription = gameCard.find('.game-description').text();
        var gameDeveloper = gameCard.find('.game-developer').text();
        var gamePublisher = gameCard.find('.game-publisher').text();
        var gameReleaseDate = gameCard.find('.game-release-date').text();

        // Clear previous content before setting new content
        $('#gameModal .modal-title').empty().text(gameTitle);
        $('#gameModal .game-description').empty().text(gameDescription.trim());
        $('#gameModal .game-developer').empty().text(gameDeveloper.trim());
        $('#gameModal .game-publisher').empty().text(gamePublisher.trim());
        $('#gameModal .game-release-date').empty().text(gameReleaseDate.trim());

        $('#gameModal').modal('show'); // Show the modal
    });
});
