import React from "react";
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';

export default function PersonalData() {
    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Shipping address
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={6}>
                    <TextField required id="firstName" name="firstName" label="First name" fullWidth/>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField required id="lastName" name="lastName" label="Last name" fullWidth/>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField required id="phone" name="Phone number" label="Phone number" type="number" fullWidth/>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField required id="email" name="Email" label="Email" fullWidth/>
                </Grid>
            </Grid>
        </React.Fragment>
    )
}