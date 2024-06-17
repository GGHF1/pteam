function applyFilters() {
    var selectedGenre = document.getElementById('genreDropdown').value;
    var minPrice = parseFloat(document.getElementById('minPrice').value);
    var maxPrice = parseFloat(document.getElementById('maxPrice').value);
    var showAddToCartOnly = document.getElementById('showAddToCartOnly').classList.contains('active');
    var gameCards = document.querySelectorAll('.game-card');

    gameCards.forEach(function(card) {
        var genre = card.querySelector('.game-info').textContent.trim();
        var price = parseFloat(card.querySelector('.price').textContent.replace('Price: $', ''));
        var purchased = card.querySelector('.buy-btn').disabled;

        var genreMatches = selectedGenre === '' || genre === selectedGenre;
        var priceMatches = price >= minPrice && price <= maxPrice;
        var addToCartMatches = !showAddToCartOnly || !purchased;

        if (genreMatches && priceMatches && addToCartMatches) {
            card.style.display = 'block'; // Show game card if both genre and price match
        } else {
            card.style.display = 'none'; 
        }
    });
}

// Event handler for filter by genre button click
document.getElementById('filterByGenreBtn').addEventListener('click', function() {
    var genreOptions = document.getElementById('genreOptions');
    genreOptions.style.display = genreOptions.style.display === 'block' ? 'none' : 'block'; 
});

// Event handler for genre dropdown change
document.getElementById('genreDropdown').addEventListener('change', function() {
    applyFilters();
    document.getElementById('genreOptions').style.display = 'none'; 
});

// Event handler for price filter button click
document.getElementById('filterByPriceBtn').addEventListener('click', function() {
    var priceOptions = document.getElementById('priceOptions');
    priceOptions.style.display = priceOptions.style.display === 'block' ? 'none' : 'block'; 
});

// Event handlers for price range input change
document.getElementById('minPrice').addEventListener('input', function() {
    document.getElementById('minPriceValue').textContent = this.value;
    applyFilters(); 
});

document.getElementById('maxPrice').addEventListener('input', function() {
    document.getElementById('maxPriceValue').textContent = this.value;
    applyFilters(); 
});

document.getElementById('showAddToCartOnly').addEventListener('click', function() {
    this.classList.toggle('active'); 
    applyFilters(); 
});

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
    dropdown.appendChild(new Option('Select Genre', '')); 
    genres.forEach(function(genre) {
        dropdown.appendChild(new Option(genre, genre));
    });
}

// Get unique list of game genres and create dropdown
var uniqueGenres = getUniqueGenres();
createFilterByGenreDropdown(uniqueGenres);
