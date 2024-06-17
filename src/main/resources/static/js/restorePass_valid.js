document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById('changePasswordForm');
    const usernameInput = document.getElementById('username');
    const emailAddressInput = document.getElementById('emailAddress');
    const phoneNumberInput = document.getElementById('phoneNumber');
    const newPasswordInput = document.getElementById('newPassword');
    const passwordRequirements = document.getElementById('passwordRequirements');
    const confirmPasswordInput = document.getElementById('confirmNewPassword');
    const confirmPasswordError = document.getElementById('confirmPasswordError');
    const btnRestorePassword = document.querySelector('.btn-restore');

    // Function to validate password requirements
    function validatePassword() {
        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if (!passwordPattern.test(newPasswordInput.value)) {
            passwordRequirements.innerHTML = `
                <ul>
                    <li>Password must be at least 8 characters long</li>
                    <li>Must contain at least one uppercase letter</li>
                    <li>Must contain at least one lowercase letter</li>
                    <li>Must contain at least one number</li>
                    <li>Must contain at least one special character</li>
                </ul>
            `;
            newPasswordInput.classList.add('is-invalid');
        } else {
            passwordRequirements.innerHTML = '';
            newPasswordInput.classList.remove('is-invalid');
        }
    }

    // Function to validate confirmed password
    function validateConfirmPassword() {
        if (confirmPasswordInput.value !== newPasswordInput.value) {
            confirmPasswordInput.classList.add('is-invalid');
            confirmPasswordError.textContent = "Passwords do not match.";
        } else {
            confirmPasswordInput.classList.remove('is-invalid');
            confirmPasswordError.textContent = "";
        }
    }

    // Function to validate all form fields
    function validateForm() {
        const isUsernameValid = usernameInput.value.trim() !== '';
        const isEmailAddressValid = emailAddressInput.value.trim() !== '' && /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailAddressInput.value);
        const isPhoneNumberValid = /^\+\d{1,3}\d{6,14}$/.test(phoneNumberInput.value);
        const isNewPasswordValid = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/.test(newPasswordInput.value);
        const isConfirmPasswordValid = confirmPasswordInput.value === newPasswordInput.value;

        const isValid = isUsernameValid && isEmailAddressValid && isPhoneNumberValid && isNewPasswordValid && isConfirmPasswordValid;

        if (isValid) {
            btnRestorePassword.removeAttribute('disabled');
        } else {
            btnRestorePassword.setAttribute('disabled', 'disabled');
        }
    }

    // Event listeners for input fields
    usernameInput.addEventListener('input', validateForm);
    emailAddressInput.addEventListener('input', validateForm);
    phoneNumberInput.addEventListener('input', validateForm);
    newPasswordInput.addEventListener('input', function() {
        validatePassword();
        validateConfirmPassword();
        validateForm();
    });
    confirmPasswordInput.addEventListener('input', function() {
        validateConfirmPassword();
        validateForm();
    });

    // Form submission validation
    form.addEventListener('submit', function(event) {
        validatePassword();
        validateConfirmPassword();
        validateForm();
        if (!btnRestorePassword.hasAttribute('disabled')) {
            // Form is valid, proceed with submission
        } else {
            event.preventDefault();
        }
    });
});
