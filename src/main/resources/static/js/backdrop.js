// Toggle mobile drawer when the toggle button is clicked
document.querySelector('.drawer-toggle').addEventListener('click', function(event) {
    event.stopPropagation(); // Prevent the click event from propagating to the document body
    const drawer = document.querySelector('.drawer');
    drawer.classList.toggle('open');
});

// Close mobile drawer when clicking outside of it
document.body.addEventListener('click', function(event) {
    const drawer = document.querySelector('.drawer');
    if (drawer.classList.contains('open') && !event.target.closest('.drawer')) {
        drawer.classList.remove('open');
    }
});
