document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector('form');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const phoneNumberInput = document.getElementById('phoneNumber');
    const fnameInput = document.getElementById('fname');
    const lnameInput = document.getElementById('lname');
    const dobInput = document.getElementById('dob');
    const emailInput = document.getElementById('email');
    const registerButton = document.querySelector('.btn-register');

    const usernameError = document.getElementById('usernameError');
    const passwordError = document.getElementById('passwordError');
    const confirmPasswordError = document.getElementById('confirmPasswordError');
    const phoneNumberError = document.getElementById('phoneNumber').nextElementSibling;
    const fnameError = document.getElementById('fname').nextElementSibling;
    const lnameError = document.getElementById('lname').nextElementSibling;
    const dobError = document.getElementById('dob').nextElementSibling;
    const emailError = document.getElementById('email').nextElementSibling;

    const usernamePattern = /^[a-zA-Z0-9]+$/; //Only English letters
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/; //Password format validation
    const phoneNumberPattern = /^\d+$/; //Phone number valdiation
    const namePattern = /^[A-Za-z]+$/; //Name Validation
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; //Email format validation

    // Event listeners for input validation
    usernameInput.addEventListener('input', validateUsername);
    passwordInput.addEventListener('input', validatePassword);
    confirmPasswordInput.addEventListener('input', validateConfirmPassword);
    phoneNumberInput.addEventListener('input', validatePhoneNumber);
    fnameInput.addEventListener('input', validateName);
    lnameInput.addEventListener('input', validateName);
    dobInput.addEventListener('input', validateDOB);
    emailInput.addEventListener('input', validateEmail);

    // Validate username
    function validateUsername() {
        if (!usernamePattern.test(usernameInput.value)) {
            usernameInput.classList.add('is-invalid');
            usernameError.textContent = "Username must contain only English letters and digits.";
        } else if (usernameInput.value.length < 4 || usernameInput.value.length > 20) {
            usernameInput.classList.add('is-invalid');
            usernameError.textContent = "Username must be between 4 and 20 characters.";
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

    // Validate email
    function validateEmail() {
        if (!emailPattern.test(emailInput.value)) {
            emailInput.classList.add('is-invalid');
            emailError.textContent = "Enter a valid email address.";
        } else {
            emailInput.classList.remove('is-invalid');
            emailError.textContent = "";
        }
        checkFormValidity();
    }

    // Validate DOB for min age of 16
    function validateDOB() {
        const dobValue = dobInput.value;
        const selectedDate = new Date(dobValue);
        const currentDate = new Date();
        const minAge = 16;

        const age = currentDate.getFullYear() - selectedDate.getFullYear();
        const monthDifference = currentDate.getMonth() - selectedDate.getMonth();
        const dayDifference = currentDate.getDate() - selectedDate.getDate();

        if (selectedDate > currentDate) {
            dobInput.classList.add('is-invalid');
            dobError.textContent = "Date of birth cannot be in the future.";
        }

        else if (age < minAge || (age === minAge && (monthDifference < 0 || (monthDifference === 0 && dayDifference < 0)))) {
            dobInput.classList.add('is-invalid');
            dobError.textContent = `You must be at least ${minAge} years old.`;
        } else {
            dobInput.classList.remove('is-invalid');
            dobError.textContent = "";
        }
        checkFormValidity();
    }

    // Function to check form validity
    function checkFormValidity() {
        if (form.checkValidity() && !usernameInput.classList.contains('is-invalid') && !passwordInput.classList.contains('is-invalid') &&
            !confirmPasswordInput.classList.contains('is-invalid') && !phoneNumberInput.classList.contains('is-invalid') &&
            !fnameInput.classList.contains('is-invalid') && !lnameInput.classList.contains('is-invalid') &&
            !dobInput.classList.contains('is-invalid') && !emailInput.classList.contains('is-invalid')) {
            registerButton.removeAttribute('disabled');
        } else {
            registerButton.setAttribute('disabled', 'disabled');
        }
    }

    function validateForm(event) {
        validateUsername();
        validatePassword();
        validateConfirmPassword();
        validatePhoneNumber();
        validateName.call(fnameInput);
        validateName.call(lnameInput);
        validateDOB();
        validateEmail();

        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }

        form.classList.add('was-validated');
        checkFormValidity();
    }

    form.addEventListener('input', checkFormValidity);
    form.addEventListener('submit', validateForm);
});
