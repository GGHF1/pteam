document.addEventListener('DOMContentLoaded', function () {
    const cardNumberInput = document.getElementById('cardNumber');
    const cardHolderNameInput = document.getElementById('cardHolderName');
    const expirationDateInput = document.getElementById('expirationDate');
    const cvvInput = document.getElementById('cvv');
    const cardImage = document.getElementById('cardImage');
    const step1 = document.getElementById('step1');
    const step2 = document.getElementById('step2');
    const nextButton = document.getElementById('nextButton');
    const errorMessage = document.getElementById('errorMessage'); // New error message element
    const errorMsg = document.getElementById('errorMsg'); // New error message element
    const expiryErrorMessage = document.getElementById('expiryErrorMessage'); // New error message element

    const displayCardNumber = document.getElementById('displayCardNumber');
    const displayCardHolderName = document.getElementById('displayCardHolderName');
    const displayExpirationDate = document.getElementById('displayExpirationDate');
    const displayCVV = document.getElementById('displayCVV');

    // Function to show the back side of the card
    function showBackSide() {
        step1.classList.add('d-none');
        step2.classList.remove('d-none');
        cardImage.src = "/images/ba2.png";
        // Hide all attributes except CVV on the backside
        displayCardNumber.style.display = 'none';
        displayCardHolderName.style.display = 'none';
        displayExpirationDate.style.display = 'none';
        displayCVV.style.display = 'block';
    }

    // Function to enable/disable next button based on form validation
    function checkFormValidity() {
        const cardNumberValid = validateCardNumber(cardNumberInput.value);
        const cardHolderNameValid = validateCardHolderName(cardHolderNameInput.value);
        const expirationDateValid = isExpirationDateValid(expirationDateInput.value);

        if (cardNumberValid && cardHolderNameValid && expirationDateValid) {
            nextButton.disabled = false;
        } else {
            nextButton.disabled = true;
        }
    }

    // Event listener for the Next button
    nextButton.addEventListener('click', function() {
        if (!validateCardNumber(cardNumberInput.value)) {
            
        } else {
            
            showBackSide();
        }
    });

    // Hide CVV initially
    displayCVV.style.display = 'none';

    // Event listener for CVV input
    cvvInput.addEventListener('input', function () {
        displayCVV.textContent = this.value;
    });

    // Event listener for card number input
    cardNumberInput.addEventListener('input', function () {
        displayCardNumber.textContent = formatCardNumber(this.value);
        checkFormValidity();
    });

    // Event listener for card holder name input
    cardHolderNameInput.addEventListener('input', function () {
        const isValid = validateCardHolderName(this.value);
        displayCardHolderName.textContent = this.value.toUpperCase();
        if (!isValid) {
            cardHolderNameInput.classList.add('invalid-input');
            errorMsg.textContent = 'Only letters and spaces allowed!';
        } else {
            cardHolderNameInput.classList.remove('invalid-input');
            errorMsg.textContent = '';
        }
        checkFormValidity();
    });

    // Event listener for expiration date input
    expirationDateInput.addEventListener('input', function () {
        const expirationDateString = this.value;
        const [year, month] = expirationDateString.split('-');
        if (year && month) {
            displayExpirationDate.textContent = `${month}/${year.slice(2)}`;
            if (!isExpirationDateValid(expirationDateString)) {
                expirationDateInput.classList.add('invalid-input');
                expiryErrorMessage.textContent = 'Card has expired';
            } else {
                expirationDateInput.classList.remove('invalid-input');
                expiryErrorMessage.textContent = '';
            }
        } else {
            displayExpirationDate.textContent = 'MM/YY';
            expiryErrorMessage.textContent = '';
        }
        checkFormValidity();
    });

    // Function to format card number
    function formatCardNumber(value) {
        return value.replace(/\s?/g, '').replace(/(\d{4})/g, '$1 ').trim();
    }

    // Function to validate card number and apply red border if invalid
    function validateCardNumber(cardNumber) {
        const cardNumberPattern = /^(4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|2(?:22[1-9]|2[3-9][0-9]|[3-6][0-9]{2}|7[01][0-9]|720)[0-9]{12}|3[47][0-9]{13})$/;
        if (cardNumberPattern.test(cardNumber.replace(/\s+/g, ''))) {
            cardNumberInput.classList.remove('invalid-input');
            errorMessage.textContent = '';
            return true;
        } else {
            cardNumberInput.classList.add('invalid-input');
            errorMessage.textContent = 'Invalid card number!';
            return false;
        }
    }

    // Function to validate cardholder name (only letters and spaces allowed)
    function validateCardHolderName(cardHolderName) {
        return /^[a-zA-Z\s]+$/.test(cardHolderName.trim()); // Only letters and spaces allowed
    }

    // Function to check if expiration date is valid (future date)
    function isExpirationDateValid(expirationDateString) {
        const [year, month] = expirationDateString.split('-');
        // Calculate the last day of the expiration month
        const expirationDate = new Date(year, month, 0); // 0 means the last day of the previous month

        // Get the current date, without time component
        const currentDate = new Date();
        currentDate.setHours(0, 0, 0, 0); // Set current date to midnight

        // Compare expiration date (end of the month) with current date
        return expirationDate >= currentDate;
    }
});