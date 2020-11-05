import React from "react";
import useStyles from "../../material-styles/useStyles";

export default function PaymentSuccess() {
    const classes = useStyles();
    return (
        <div className={classes.paymentResult}>
            Payment Success. Check your mail box to get tickets.
        </div>
    )
}