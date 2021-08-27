import React, { useEffect } from 'react'
import axios from 'axios';
const TestPayment = () => {
    useEffect(() => {
        const { OmiseCard } = window;
        OmiseCard.configure({
            publicKey: "pkey_test_5oxck5cs1o151q73uqw"
        });

        var button = document.querySelector("#checkoutButton");
        var form = document.querySelector("#checkoutForm");
        console.log(form)
        button.addEventListener("click", (event) => {
            event.preventDefault();
            console.log(form)
            OmiseCard.open({
                amount: 12345,
                currency: "THB",
                defaultPaymentMethod: "credit_card",
                onCreateTokenSuccess: (nonce) => {
                    if (nonce.startsWith("tokn_")) {
                        form.omiseToken.value = nonce;
                    } else {
                        form.omiseSource.value = nonce;
                    };
                    axios.post("/charge", {
                        omiseToken: form.omiseToken.value,
                        omiseSource: form.omiseSource.value
                    })
                }
            });
        });
    }, [])
    return (
        <>
            <form id="checkoutForm" method="POST" action="/charge">
                <input type="hidden" name="omiseToken" />
                <input type="hidden" name="omiseSource" />
                <button type="submit" id="checkoutButton">Checkout</button>
            </form>
        </>
    )
}

export default TestPayment
