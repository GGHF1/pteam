$(document).ready(function() {
    // Check if URL parameter gameAdded is true (indicating game was already added)
    var urlParams = new URLSearchParams(window.location.search);
    var gameAdded = urlParams.get('gameAdded');
    if (gameAdded == 'true') {
        // Show a message or perform an action indicating game is already added
        console.log('Game is already added to cart or pending orders.');
    }

    // Function to update cart count dynamically
    function updateCartCount(count) {
        $('#cartCount').text(count);
    }

    // Fetch cart items using AJAX
    $.get('/cart/items', function(data) {
        // Clear previous cart items and total price
        $('#cartItems').empty();
        $('#totalPrice').text('Total Price: $0.00');

        if (data.length === 0) {
            $('#cartItems').append('<p>Cart is empty</p>');
        } else {
            // Populate modal with cart items and calculate total price
            var totalPrice = 0;
            data.forEach(function(item) {
                if (item.game && item.game.title) {
                    $('#cartItems').append('<p><button class="btn btn-sm btn-danger remove-btn" data-id="' + item.id + '"> - </button> ' + item.game.title + ' - $' + item.game.price.toFixed(2) + '</p>');
                    totalPrice += parseFloat(item.game.price);
                } else {
                    // Handle missing or null game property
                    console.error('Invalid item:', item);
                }
            });

            // Update total price in the modal footer
            $('#totalPrice').text('Total Price: $' + totalPrice.toFixed(2));

            // Update hidden input field for total price
            $('#totalPriceInput').val(totalPrice.toFixed(2));
        }

        // Update cart count after fetching cart items
        updateCartCount(data.length);
    });

    // Remove button click event handler
    $(document).on('click', '.remove-btn', function() {
        var cartItemId = $(this).data('id');
        // Send AJAX request to remove the item from the cart
        $.post('/cart/remove/' + cartItemId, function(data) {
            // Refresh the cart modal after removing the item
            $('.cart-icon').trigger('click');
            window.location.href = '/store';
        });
    });
});
