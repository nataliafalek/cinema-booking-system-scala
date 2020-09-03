import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import React from "react";

export default function FormItem(props) {
    return (
        <Grid item xs={12}>
            {props.isMultiline ? <TextField
                variant="outlined"
                required
                fullWidth
                multiline
                name={props.label}
                label={props.label}
                type={props.type}
                id={props.label}
                onChange={event => props.setFunc(event.target.value)}
            /> : <TextField
                variant="outlined"
                required
                fullWidth
                name={props.label}
                label={props.label}
                type={props.type}
                id={props.label}
                onChange={event => props.setFunc(event.target.value)}
            />
            }
        </Grid>
    )
}