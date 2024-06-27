document.addEventListener("DOMContentLoaded", function() {
    // Check filtering state when the page loads
    if (sessionStorage.getItem('selectedGenre')) {
        document.getElementById('genreDropdown').value = sessionStorage.getItem('selectedGenre');
    }
    if (sessionStorage.getItem('minPrice')) {
        document.getElementById('minPrice').value = sessionStorage.getItem('minPrice');
    }
    if (sessionStorage.getItem('maxPrice')) {
        document.getElementById('maxPrice').value = sessionStorage.getItem('maxPrice');
    }
    if (sessionStorage.getItem('showAddToCartOnly') === 'true') {
        document.getElementById('showAddToCartOnly').classList.add('active');
    }

    // Apply filters based on stored session data
    applyFilters();

    // Event listener for filter by genre button click
    document.getElementById('filterByGenreBtn').addEventListener('click', function() {
        var genreOptions = document.getElementById('genreOptions');
        genreOptions.style.display = genreOptions.style.display === 'block' ? 'none' : 'block';
    });

    // Event listener for genre dropdown change
    document.getElementById('genreDropdown').addEventListener('change', function() {
        sessionStorage.setItem('selectedGenre', this.value);
        applyFilters();
        document.getElementById('genreOptions').style.display = 'none';
    });

    // Event listener for filter by price button click
    document.getElementById('filterByPriceBtn').addEventListener('click', function() {
        var priceOptions = document.getElementById('priceOptions');
        priceOptions.style.display = priceOptions.style.display === 'block' ? 'none' : 'block';
    });

    // Event listeners for price range input change
    document.getElementById('minPrice').addEventListener('input', function() {
        document.getElementById('minPriceValue').textContent = this.value;
        applyFilters();
        sessionStorage.setItem('minPrice', this.value);
    });

    document.getElementById('maxPrice').addEventListener('input', function() {
        document.getElementById('maxPriceValue').textContent = this.value;
        applyFilters();
        sessionStorage.setItem('maxPrice', this.value);
    });

    // Event listener for "Show Add to Cart Only" toggle click
    document.getElementById('showAddToCartOnly').addEventListener('click', function() {
        this.classList.toggle('active');
        applyFilters();
        sessionStorage.setItem('showAddToCartOnly', this.classList.contains('active'));
    });

    // Reset button functionality
    document.getElementById('resetBtn').addEventListener('click', function() {
        // Remove filtering state from session
        sessionStorage.removeItem('selectedGenre');
        sessionStorage.removeItem('minPrice');
        sessionStorage.removeItem('maxPrice');
        sessionStorage.removeItem('showAddToCartOnly');

        // Reload the page
        window.location.href = '/store';
    });

    // Function to apply filters based on selected criteria
    function applyFilters() {
        var selectedGenre = sessionStorage.getItem('selectedGenre');
        var minPrice = parseFloat(document.getElementById('minPrice').value);
        var maxPrice = parseFloat(document.getElementById('maxPrice').value);
        var showAddToCartOnly = document.getElementById('showAddToCartOnly').classList.contains('active');
        var gameCards = document.querySelectorAll('.game-card');

        gameCards.forEach(function(card) {
            var genre = card.querySelector('.game-info').textContent.trim();
            var price = parseFloat(card.querySelector('.price').textContent.replace('Price: $', ''));
            var purchased = card.querySelector('.buy-btn').disabled;

            var genreMatches = !selectedGenre || genre === selectedGenre; 
            var priceMatches = price >= minPrice && price <= maxPrice;
            var addToCartMatches = !showAddToCartOnly || !purchased;

            if (genreMatches && priceMatches && addToCartMatches) {
                card.style.display = 'block'; // Show game card if all criteria match
            } else {
                card.style.display = 'none';
            }
        });
    }
    
    // Function to get unique list of game genres
    function getUniqueGenres() {
        var uniqueGenres = [];
        document.querySelectorAll('.game-info').forEach(function(element) {
            var genre = element.textContent.trim();
            if (!uniqueGenres.includes(genre)) {
                uniqueGenres.push(genre);
            }
        });
        return uniqueGenres;
    }

    // Function to create filter by genre dropdown
    function createFilterByGenreDropdown(genres) {
        var dropdown = document.getElementById('genreDropdown');
        dropdown.innerHTML = '';
    
        var selectedGenre = sessionStorage.getItem('selectedGenre');
    
        dropdown.appendChild(new Option('Select Genre', ''));
    
        genres.forEach(function(genre) {
            var option = new Option(genre, genre);
            if (genre === selectedGenre) {
                option.selected = true; // Set selected attribute if genre matches sessionStorage value
            }
            dropdown.appendChild(option);
        });
    }
    
    // Get unique list of game genres and create dropdown
    var uniqueGenres = getUniqueGenres();
    createFilterByGenreDropdown(uniqueGenres);

});
