import React from 'react';
import Paper from '@material-ui/core/Paper';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import CinemaHallView from "./CinemaHallView";
import PersonalData from "./PersonalData";
import Summary from "./Summary";
import useStyles from "../../material-styles/useStyles";

const steps = ['Choose seats', 'Personal details', 'Summary'];

function getStepContent(step) {
    switch (step) {
        case 0:
            return <CinemaHallView/>;
        case 1:
            return <PersonalData/>;
        case 2:
            return <Summary/>;
        default:
            throw new Error('Unknown step');
    }
}

export default function Checkout() {
    const classes = useStyles();
    const [activeStep, setActiveStep] = React.useState(0);

    const handleNext = () => {
        setActiveStep(activeStep + 1);
    };

    const handleBack = () => {
        setActiveStep(activeStep - 1);
    };

    return (
        <React.Fragment>
            <main className={classes.checkoutLayout}>
                <Paper className={classes.checkoutPaper}>
                    <Typography component="h1" variant="h4" align="center">
                        Movie Reservation
                    </Typography>
                    <Stepper activeStep={activeStep} className={classes.checkoutStepper}>
                        {steps.map((label) => (
                            <Step key={label}>
                                <StepLabel>{label}</StepLabel>
                            </Step>
                        ))}
                    </Stepper>
                    <React.Fragment>
                        {activeStep === steps.length ? (
                            <React.Fragment>
                                <Typography variant="h5" gutterBottom>
                                    Thank you for your order.
                                </Typography>
                                <Typography variant="subtitle1">
                                    Your order number is #2001539. We have emailed your order confirmation, and will
                                    send you an update when your order has shipped.
                                </Typography>
                            </React.Fragment>
                        ) : (
                            <React.Fragment>
                                {getStepContent(activeStep)}
                                <div className={classes.checkoutButtons}>
                                    {activeStep !== 0 && (
                                        <Button onClick={handleBack} className={classes.checkoutButton}>
                                            Back
                                        </Button>
                                    )}
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        onClick={handleNext}
                                        className={classes.checkoutButton}
                                    >
                                        {activeStep === steps.length - 1 ? 'Place order' : 'Next'}
                                    </Button>
                                </div>
                            </React.Fragment>
                        )}
                    </React.Fragment>
                </Paper>
            </main>
        </React.Fragment>
    );
}