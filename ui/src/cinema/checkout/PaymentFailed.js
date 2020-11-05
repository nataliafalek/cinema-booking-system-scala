import React from "react";
import useStyles from "../../material-styles/useStyles";

export default function PaymentFailed() {
    const classes = useStyles();
    return (
        <div className={classes.paymentResult}>
            Payment Failed.
        </div>
    )
}