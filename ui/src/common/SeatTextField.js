import TextField from "@material-ui/core/TextField";
import React from "react";

export default function SeatTextField(props) {
    return(
        <TextField
            id="standard-read-only-input"
            label={props.label}
            defaultValue={props.value}
            InputProps={{
                readOnly: true,
            }}
        />
    )
}