document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector('form');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const phoneNumberInput = document.getElementById('phoneNumber');
    const fnameInput = document.getElementById('fname');
    const lnameInput = document.getElementById('lname');
    const dobInput = document.getElementById('dob');
    const registerButton = document.querySelector('.btn-register');

    // Error message elements
    const usernameError = document.getElementById('usernameError');
    const passwordError = document.getElementById('passwordError');
    const confirmPasswordError = document.getElementById('confirmPasswordError');
    const phoneNumberError = document.getElementById('phoneNumber').nextElementSibling;
    const fnameError = document.getElementById('fname').nextElementSibling;
    const lnameError = document.getElementById('lname').nextElementSibling;
    const dobError = document.getElementById('dob').nextElementSibling;

    // Regular expressions for validation
    const usernamePattern = /^[a-zA-Z0-9]{1,20}$/;
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    const phoneNumberPattern = /^\d+$/;
    const namePattern = /^[A-Za-z]+$/;

    // Event listeners for input validation
    usernameInput.addEventListener('input', validateUsername);
    passwordInput.addEventListener('input', validatePassword);
    confirmPasswordInput.addEventListener('input', validateConfirmPassword);
    phoneNumberInput.addEventListener('input', validatePhoneNumber);
    fnameInput.addEventListener('input', validateName);
    lnameInput.addEventListener('input', validateName);
    dobInput.addEventListener('input', validateDOB);

    // Validate username
    function validateUsername() {
        if (!usernamePattern.test(usernameInput.value)) {
            usernameInput.classList.add('is-invalid');
            usernameError.textContent = "Username must be between 1 and 20 characters and contain only letters and numbers.";
        } else if (usernameInput.value.length < 4) {
            usernameInput.classList.add('is-invalid');
            usernameError.textContent = "Username must be at least 4 characters long.";
        } else {
            usernameInput.classList.remove('is-invalid');
            usernameError.textContent = "";
        }
        checkFormValidity();
    }

    // Validate password
    function validatePassword() {
        if (!passwordPattern.test(passwordInput.value)) {
            passwordInput.classList.add('is-invalid');
            passwordError.textContent = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character.";
        } else {
            passwordInput.classList.remove('is-invalid');
            passwordError.textContent = "";
        }
        checkFormValidity();
    }

    // Validate confirm password
    function validateConfirmPassword() {
        if (confirmPasswordInput.value !== passwordInput.value) {
            confirmPasswordInput.classList.add('is-invalid');
            confirmPasswordError.textContent = "Passwords do not match.";
        } else {
            confirmPasswordInput.classList.remove('is-invalid');
            confirmPasswordError.textContent = "";
        }
        checkFormValidity();
    }

    // Validate phone number
    function validatePhoneNumber() {
        if (!phoneNumberPattern.test(phoneNumberInput.value)) {
            phoneNumberInput.classList.add('is-invalid');
            phoneNumberError.textContent = "Phone number should only contain digits.";
        } else {
            phoneNumberInput.classList.remove('is-invalid');
            phoneNumberError.textContent = "";
        }
        checkFormValidity();
    }

    // Validate first and last name
    function validateName() {
        if (!namePattern.test(this.value)) {
            this.classList.add('is-invalid');
            this.nextElementSibling.textContent = "Name must contain only letters.";
        } else {
            this.classList.remove('is-invalid');
            this.nextElementSibling.textContent = "";
            // Convert to capitalized first letter, rest lowercase
            this.value = this.value.charAt(0).toUpperCase() + this.value.slice(1).toLowerCase();
        }
        checkFormValidity();
    }

    // Validate date of birth for minimum age of 16
    function validateDOB() {
        const dobValue = dobInput.value;
        const selectedDate = new Date(dobValue);
        const currentDate = new Date();
        const minAge = 16;

        // Calculate age based on date difference
        const age = currentDate.getFullYear() - selectedDate.getFullYear();

        // Check if the user hasn't reached the minimum age
        if (age < minAge) {
            dobInput.classList.add('is-invalid');
            dobError.textContent = `You must be at least ${minAge} years old.`;
        } else {
            dobInput.classList.remove('is-invalid');
            dobError.textContent = "";
        }
        checkFormValidity();
    }

    // Function to check form validity and enable/disable register button
    function checkFormValidity() {
        if (form.checkValidity() && !usernameInput.classList.contains('is-invalid') && !passwordInput.classList.contains('is-invalid') &&
            !confirmPasswordInput.classList.contains('is-invalid') && !phoneNumberInput.classList.contains('is-invalid') &&
            !fnameInput.classList.contains('is-invalid') && !lnameInput.classList.contains('is-invalid') &&
            !dobInput.classList.contains('is-invalid')) {
            registerButton.removeAttribute('disabled');
        } else {
            registerButton.setAttribute('disabled', 'disabled');
        }
    }

    // Form validation on submit
    function validateForm(event) {
        validateUsername();
        validatePassword();
        validateConfirmPassword();
        validatePhoneNumber();
        validateName.call(fnameInput);
        validateName.call(lnameInput);
        validateDOB();

        // Prevent form submission if any field is invalid
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }

        // Add 'was-validated' class to display validation messages
        form.classList.add('was-validated');

        // Check form validity after adding 'was-validated' class
        checkFormValidity();
    }

    // Enable/disable register button based on form validity on input
    form.addEventListener('input', checkFormValidity);

    // Form submission handler
    form.addEventListener('submit', validateForm);
});
