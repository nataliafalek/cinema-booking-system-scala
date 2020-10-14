import Button from "@material-ui/core/Button";
import React from "react";
import useStyles from "../material-styles/useStyles";

export default function CheckoutButton(props) {
    const classes = useStyles();

    return (
        <Button
            variant="contained"
            color="primary"
            onClick={() => props.function()}
            className={classes.checkoutButton}
        >
            {props.name}
        </Button>
    )
}