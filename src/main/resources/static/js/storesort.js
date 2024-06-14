$(document).ready(function() {
    // Handler for the "Sort by Price" button click
    $("#sortHighToLowBtn").click(function() {
        // Perform sorting by price
        sortGamesHighToLow();
    });

    // Handler for the "Sort by Price (Low to High)" button click
    $("#sortLowToHighBtn").click(function() {
        // Perform sorting by price (low to high)
        sortGamesLowToHigh();
    });

    // Handler for the "Reset" button click
    $("#resetBtn").click(function() {
        // Remove sorting state from session
        sessionStorage.removeItem('isSortedByHtoL');
        sessionStorage.removeItem('isSortedByLtoH');
        // Refresh the store page
        window.location.href = '/store';
    });

    $(".game-container").on("click", ".info-btn", function() {
        // Get the game details from the corresponding game card
        var gameCard = $(this).closest(".game-card");
        var gameTitle = gameCard.find(".game-title").text();
        var gameDescription = gameCard.find(".game-description").text();
        var gameDeveloper = gameCard.find(".game-developer").text();
        var gamePublisher = gameCard.find(".game-publisher").text();
        var gameReleaseDate = gameCard.find(".game-release-date").text();

        // Populate the game details modal with the retrieved information
        $("#gameModal .modal-title").text(gameTitle);
        $("#gameModal .game-description").text(gameDescription);
        $("#gameModal .game-developer").text(gameDeveloper);
        $("#gameModal .game-publisher").text(gamePublisher);
        $("#gameModal .game-release-date").text(gameReleaseDate);

        // Show the game details modal
        $("#gameModal").modal("show");
    });



    function sortGamesHighToLow() {
        // Store sorting state in session
        sessionStorage.setItem('isSortedByHtoL', true);

        // Get the game list container
        var gameList = $(".game-container");

        // Get the list of game items
        var games = gameList.children(".game-card");

        // Sort the games by price
        games.sort(function(a, b) {
            var priceA = parseFloat($(a).find(".price").text().replace("Price: $", ""));
            var priceB = parseFloat($(b).find(".price").text().replace("Price: $", ""));
            return priceB - priceA; // Sort in descending order (high to low)
        });

        // Re-append sorted game items to the container
        gameList.empty().append(games);
    }

    // Check sorting state when the page loads
    if (sessionStorage.getItem('isSortedByHtoL') === 'true') {
        sortGamesHighToLow(); // Reapply sorting if necessary
    }

    // Function to sort games from low to high
    function sortGamesLowToHigh() {
        // Store sorting state in session
        sessionStorage.setItem('isSortedByLtoH', true);

        // Get the game list container
        var gameList = $(".game-container");

        // Get the list of game items
        var games = gameList.children(".game-card");

        // Sort the games by price (low to high)
        games.sort(function(a, b) {
            var priceA = parseFloat($(a).find(".price").text().replace("Price: $", ""));
            var priceB = parseFloat($(b).find(".price").text().replace("Price: $", ""));
            return priceA - priceB; // Sort in ascending order (low to high)
        });

        // Re-append sorted game items to the container
        gameList.empty().append(games);
    }

    // Check sorting state when the page loads
    if (sessionStorage.getItem('isSortedByLtoH') === 'true') {
        sortGamesLowToHigh(); // Reapply sorting if necessary
    }

});
