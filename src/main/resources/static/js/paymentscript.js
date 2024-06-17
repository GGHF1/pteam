function updateCardId() {
    var selectedCardId = document.getElementById("cardId").value;
    document.getElementById("selectedCardId").value = selectedCardId;
}

function validateForm() {
    var isValid = true;
    var form = document.getElementById("paymentForm");
    var inputs = form.querySelectorAll("input[required]");
    inputs.forEach(function(input) {
        if (!input.value) {
            isValid = false;
        }
    });
    
    // Check if the city contains only letters
    var cityInput = document.getElementById("city");
    var cityValue = cityInput.value;    
    if (!/^[a-zA-Z\s]+$/.test(cityValue)) {
        cityInput.classList.add("is-invalid");
        isValid = false;
    } else {
        cityInput.classList.remove("is-invalid");
    }
    
    return isValid;
}

function checkFormCompletion() {
    var cardSelected = document.getElementById("cardId").value !== "";
    var termsAccepted = document.getElementById("termsCheckbox").checked;
    var formComplete = cardSelected && termsAccepted && validateForm();
    document.getElementById("purchaseButton").disabled = !formComplete;
}

document.addEventListener("DOMContentLoaded", function() {
    // Listen for changes in the card selection and form inputs
    document.getElementById("cardId").addEventListener("change", checkFormCompletion);
    var formInputs = document.querySelectorAll("input[required], #termsCheckbox");
    formInputs.forEach(function(input) {
        input.addEventListener("input", checkFormCompletion);
    });

    // Listen for city input changes to provide instant feedback
    document.getElementById("city").addEventListener("input", function() {
        var cityInput = document.getElementById("city");
        var cityValue = cityInput.value;
        if (!/^[a-zA-Z\s]+$/.test(cityValue)) {
            cityInput.classList.add("is-invalid");
        } else {
            cityInput.classList.remove("is-invalid");
        }
    });

    // Highlight unfilled required fields when hovering over the disabled button
    document.getElementById("purchaseButton").addEventListener("mouseover", function() {
        var formInputs = document.querySelectorAll("input[required]:not(:valid)");
        formInputs.forEach(function(input) {
            input.style.border = "1px solid red";
        });

        // Highlight Terms checkbox and Select Card if not completed
        var termsAccepted = document.getElementById("termsCheckbox").checked;
        var cardSelected = document.getElementById("cardId").value !== "";
        var termsCheckbox = document.getElementById("termsCheckbox");
        var cardDropdown = document.getElementById("cardId");

        if (!termsAccepted) {
            termsCheckbox.style.outline = "1px solid red";
        } else {
            termsCheckbox.style.outline = "";
        }

        if (!cardSelected) {
            cardDropdown.style.outline = "1px solid red";
        } else {
            cardDropdown.style.outline = "";
        }
    });

    // Reset styles on form fields when moving mouse away from the button
    document.getElementById("purchaseButton").addEventListener("mouseout", function() {
        var formInputs = document.querySelectorAll("input[required]");
        formInputs.forEach(function(input) {
            input.style.border = "";
        });

        // Reset Terms checkbox and Select Card highlighting
        var termsCheckbox = document.getElementById("termsCheckbox");
        var cardDropdown = document.getElementById("cardId");

        termsCheckbox.style.outline = "";
        cardDropdown.style.outline = "";
    });
});
