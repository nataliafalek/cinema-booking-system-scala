import Button from "@material-ui/core/Button";
import React from "react";

export default function CheckoutNextButton(props) {

    return (
        <Button
            onClick={() => props.function()}
            disabled={props.disabled}
            style={{
                marginTop: 50,
                fontSize: '20px',
                float: 'right'
            }}
        >
            {props.name}
        </Button>
    )
}