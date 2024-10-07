
const projectInput = document.querySelector("#project-input");
const professionInput = document.querySelector("#profession-input");
const amountRange = document.querySelector("#amount-range");
const amountInput = document.querySelector("#amount-input");
const durationRange = document.querySelector("#duration-range");
const durationInput = document.querySelector("#duration-input");
const monthlyRange = document.querySelector("#monthly-range");
const monthlyInput = document.querySelector("#monthly-input");

const stepOneSubmitBtn = document.querySelector("#submit-step-one");

const taxRate = 12;
const monthlyRate = taxRate / 12 / 100;

const updateByAmountAndDuration = () => {
    let amount = parseFloat(amountRange.value);
    let duration = parseInt(durationRange.value);

    let monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -duration));

    monthlyInput.value = monthlyPayment.toFixed(2);
    monthlyRange.value = monthlyPayment.toFixed(2);
};

const updateByMonthlyAndDuration = () => {
    let monthlyPayment = parseFloat(monthlyRange.value);
    let duration = parseInt(durationRange.value);

    let amount = monthlyPayment * (1 - Math.pow(1 + monthlyRate, -duration)) / monthlyRate;

    amountInput.value = amount.toFixed(2);
    amountRange.value = amount.toFixed(2);
};

const updateByAmountAndMonthly = () => {
    let monthlyPayment = parseFloat(monthlyRange.value);
    let amount = parseFloat(amountRange.value);

    let duration = -Math.log(1 - (amount * monthlyRate) / monthlyPayment) / Math.log(1 + monthlyRate);

    durationInput.value = Math.round(duration);
    durationRange.value = Math.round(duration);
};

amountRange.addEventListener("input", () => {
    amountInput.value = amountRange.value;
    updateByAmountAndDuration();
});

durationRange.addEventListener("input", () => {
    durationInput.value = durationRange.value;
    updateByAmountAndDuration();
});

monthlyRange.addEventListener("input", () => {
    monthlyInput.value = monthlyRange.value;
    updateByMonthlyAndDuration();
});

amountInput.addEventListener("input", () => {
    amountRange.value = amountInput.value;
    updateByAmountAndDuration();
});

durationInput.addEventListener("input", () => {
    durationRange.value = durationInput.value;
    updateByAmountAndDuration();
});

monthlyInput.addEventListener("input", () => {
    monthlyRange.value = monthlyInput.value;
    updateByMonthlyAndDuration();
});

stepOneSubmitBtn.addEventListener("click", (e) => {
    // e.preventDefault();
    let data = {
        project: projectInput.value,
        profession: professionInput.value,
        amount: amountInput.value,
        duration: durationInput.value,
        monthly: monthlyInput.value
    }

    window.localStorage.setItem("creditData", JSON.stringify(data));
    console.log("data", data);
})
