import TextField from "@material-ui/core/TextField";
import React from "react";

export default function SeatTextField(props) {
    return (
        <TextField
            size={"medium"}
            id="standard-read-only-input"
            label={props.label}
            defaultValue={props.value}
            className={props.classNameProps}
            InputProps={{
                readOnly: true,
            }}
        />
    )
}