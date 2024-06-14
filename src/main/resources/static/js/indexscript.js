// Toggle mobile drawer when the toggle button is clicked
document.querySelector('.mobile-drawer-toggle').addEventListener('click', function(event) {
    event.stopPropagation(); // Prevent the click event from propagating to the document body
    document.querySelector('.mobile-drawer').classList.toggle('active');
});

// Close mobile drawer when clicking outside of it
document.body.addEventListener('click', function(event) {
    const mobileDrawer = document.querySelector('.mobile-drawer');
    if (mobileDrawer.classList.contains('active') && !event.target.closest('.mobile-drawer')) {
        mobileDrawer.classList.remove('active');
    }
});
