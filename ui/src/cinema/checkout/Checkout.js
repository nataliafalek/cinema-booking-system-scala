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


export default function Checkout() {
    const steps = ['Choose seats', 'Personal details', 'Summary'];
    const classes = useStyles();
    const [activeStep, setActiveStep] = React.useState(0);

    function getStepContent(step) {
        switch (step) {
            case 0:
                return <CinemaHallView handleNext={handleNext}/>;
            case 1:
                return <PersonalData handleNext={handleNext}/>;
            case 2:
                return <Summary/>;
            default:
                throw new Error('Unknown step');
        }
    }

    const handleBack = () => {
        setActiveStep(activeStep - 1);
    };

    const handleNext = () => {
        setActiveStep(activeStep + 1);
    };

    return (
        <>
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
                    <>
                        {activeStep === steps.length ? (
                            <>
                                <Typography variant="h5" gutterBottom>
                                    PaprykPaprykPapryk
                                </Typography>
                                <Typography variant="subtitle1">
                                    PaprykPaprykPapryk
                                </Typography>
                            </>
                        ) : (
                            <>
                                {getStepContent(activeStep)}
                                <div className={classes.checkoutButtons}>
                                    {activeStep !== 0 && (
                                        <Button onClick={handleBack} className={classes.checkoutButton}>
                                            Back
                                        </Button>
                                    )}
                                </div>
                            </>
                        )}
                    </>
                </Paper>
            </main>
        </>
    );
}