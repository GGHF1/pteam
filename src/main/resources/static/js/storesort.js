$(document).ready(function() {
    // Handler for the "Sort by Price (High to Low)" button click
    $("#sortHighToLowBtn").click(function() {
        sortGamesHighToLow();
    });

    // Handler for the "Sort by Price (Low to High)" button click
    $("#sortLowToHighBtn").click(function() {
        sortGamesLowToHigh();
    });

    // Handler for the "Sort by Title (A-Z)" button click
    $("#sortAToZBtn").click(function() {
        sortGamesAToZ();
    });

    // Handler for the "Sort by Title (Z-A)" button click
    $("#sortZToABtn").click(function() {
        sortGamesZToA();
    });

    // Handler for the "Reset" button click
    $("#resetBtn").click(function() {
        // Remove sorting state from session
        sessionStorage.removeItem('isSortedByHtoL');
        sessionStorage.removeItem('isSortedByLtoH');
        sessionStorage.removeItem('isSortedByAToZ');
        sessionStorage.removeItem('isSortedByZToA');
        // Refresh the store page
        window.location.href = '/store';
    });

    // Function to sort games by price (High to Low)
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

    // Function to sort games by price (Low to High)
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

    // Function to sort games alphabetically by title A to Z
    function sortGamesAToZ() {
        // Store sorting state in session
        sessionStorage.setItem('isSortedByAToZ', true);

        // Get the game list container
        var gameList = $(".game-container");

        // Get the list of game items
        var games = gameList.children(".game-card");

        // Sort the games by title alphabetically A to Z
        games.sort(function(a, b) {
            var titleA = $(a).find(".game-title").text().toUpperCase();
            var titleB = $(b).find(".game-title").text().toUpperCase();
            if (titleA < titleB) {
                return -1;
            }
            if (titleA > titleB) {
                return 1;
            }
            return 0;
        });

        // Re-append sorted game items to the container
        gameList.empty().append(games);
    }

    // Function to sort games alphabetically by title Z to A
    function sortGamesZToA() {
        // Store sorting state in session
        sessionStorage.setItem('isSortedByZToA', true);

        // Get the game list container
        var gameList = $(".game-container");

        // Get the list of game items
        var games = gameList.children(".game-card");

        // Sort the games by title alphabetically Z to A
        games.sort(function(a, b) {
            var titleA = $(a).find(".game-title").text().toUpperCase();
            var titleB = $(b).find(".game-title").text().toUpperCase();
            if (titleA > titleB) {
                return -1;
            }
            if (titleA < titleB) {
                return 1;
            }
            return 0;
        });

        // Re-append sorted game items to the container
        gameList.empty().append(games);
    }

    // Check sorting state when the page loads
    if (sessionStorage.getItem('isSortedByHtoL') === 'true') {
        sortGamesHighToLow();
    }

    if (sessionStorage.getItem('isSortedByLtoH') === 'true') {
        sortGamesLowToHigh();
    }

    if (sessionStorage.getItem('isSortedByAToZ') === 'true') {
        sortGamesAToZ();
    }

    if (sessionStorage.getItem('isSortedByZToA') === 'true') {
        sortGamesZToA();
    }
});
