document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById('changePasswordForm');
    const newPasswordInput = document.getElementById('newPassword');
    const passwordRequirements = document.getElementById('passwordRequirements');
    const confirmPasswordInput = document.getElementById('confirmNewPassword');
    const confirmPasswordError = document.getElementById('confirmPasswordError');
    const btnChangePassword = document.querySelector('.btn-primary');

    newPasswordInput.addEventListener('input', validatePassword);
    confirmPasswordInput.addEventListener('input', validateConfirmPassword);

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

    function validateConfirmPassword() {
        if (confirmPasswordInput.value !== newPasswordInput.value) {
            confirmPasswordInput.classList.add('is-invalid');
            confirmPasswordError.textContent = "Passwords do not match.";
        } else {
            confirmPasswordInput.classList.remove('is-invalid');
            confirmPasswordError.textContent = "";
        }
    }

    form.addEventListener('submit', function(event) {
        validatePassword();
        validateConfirmPassword();
        if (newPasswordInput.classList.contains('is-invalid') || confirmPasswordInput.classList.contains('is-invalid')) {
            event.preventDefault();
        }
    });

    // Function to check form validity 
    function validateForm() {
        const isNewPasswordValid = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/.test(newPasswordInput.value);
        const isConfirmPasswordValid = confirmPasswordInput.value === newPasswordInput.value;

        const isValid = isNewPasswordValid && isConfirmPasswordValid;

        if (isValid) {
            btnChangePassword.removeAttribute('disabled');
        } else {
            btnChangePassword.setAttribute('disabled', 'disabled');
        }
    }

    newPasswordInput.addEventListener('input', function() {
        validatePassword();
        validateForm();
    });

    confirmPasswordInput.addEventListener('input', function() {
        validateConfirmPassword();
        validateForm();
    });

    validateForm();
});
