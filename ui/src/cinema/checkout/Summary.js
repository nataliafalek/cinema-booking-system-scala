import React, {useEffect} from "react";
import useStyles from "../../material-styles/useStyles";
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import useLocalStorage from "../../localstorage/useLocalStorage";
import SeatTextField from "../../common/SeatTextField";
import * as HttpService from "../../http/HttpService";
import _ from "lodash";
import CheckoutNextButton from "./CheckoutNextButton";
import CheckoutBackButton from "./CheckoutBackButton";

export default function Summary(props) {
    const classes = useStyles();
    const chosenSeatsAndPrices = useLocalStorage('chosenSeatsAndPrices')[0];
    const chosenMovie = useLocalStorage('chosenMovie')[0];
    const personalDataId = useLocalStorage('personalDataId')[0];
    const [personalData, setPersonalData] = useLocalStorage('personalData', null);
    const sumToPay = _.sumBy(chosenSeatsAndPrices.map(chosenSeatAndPrice => chosenSeatAndPrice.price),
        price => price.ticketPrice)

    useEffect(() => {
        getPersonalData()
    }, []);

    const getPersonalData = () => {
        HttpService.fetchJson(`personaldata/get/${personalDataId}`).then(personalData => {
            setPersonalData(personalData)
        })
    }

    const saveReservation = () => {
        const reservationDto = {
            personalDataId: parseInt(personalDataId),
            scheduledMovieId: chosenMovie.scheduledMovieId,
            chosenSeatsAndPrices: _.map(chosenSeatsAndPrices, chosenSeatAndPrice => ({
                seatId: chosenSeatAndPrice.seat.seatId,
                priceId: chosenSeatAndPrice.price.id
            }))
        }
        HttpService.postJson('reservation/make', reservationDto)
            .then(data =>
                console.log("TODO REDIRECT TO PAYMENT", reservationDto)
            )
    }

    return (
        <div className={classes.checkoutSummary}>
            <Grid container spacing={2}>
                <SummaryText title={"Movie"} value={chosenMovie.title}/>
                <SummaryText title={"Start Hour"} value={chosenMovie.startHour}/>
            </Grid>
            {personalData ? <>
                <Grid container spacing={2}>
                    <SummaryText title={"First name"} value={personalData.firstName}/>
                    <SummaryText title={"Last Name"} value={personalData.lastName}/>
                </Grid>
                <Grid container spacing={2}>
                    <SummaryText title={"Email"} value={personalData.email}/>
                    <SummaryText title={"Phone"} value={personalData.phoneNumber}/>
                </Grid>
            </> : null}
            <Typography variant="h6" gutterBottom className={classes.title}>
                Tickets
            </Typography>
            {chosenSeatsAndPrices.map((seatAndPrice) => (
                <form
                    key={`${seatAndPrice.seat.columnNumber}-${seatAndPrice.seat.columnNumber}-${seatAndPrice.seat.rowNumber}`}
                    className={classes.root} noValidate autoComplete="off">
                    <SeatTextField value={seatAndPrice.seat.seatNumber} label={"Seat number"}/>
                    <SeatTextField value={seatAndPrice.seat.rowNumber} label={"Row number"}/>
                    <SeatTextField value={`${seatAndPrice.price.ticketPrice}$`} label={"Price"}/>
                </form>
            ))}
            <Grid container spacing={2}>
                <SummaryText title={""} value={""}/>
                <SummaryText title={"Payment"} value={`${sumToPay}$`}/>
            </Grid>
            <CheckoutNextButton function={saveReservation} name={"Payment"}/>
            <CheckoutBackButton function={props.handleBack} name={"Back"}/>
        </div>
    );
}

function SummaryText(props) {
    const classes = useStyles();
    return (
        <Grid item xs={12} sm={6}>
            <Typography variant="h6" gutterBottom className={classes.title}>
                {props.title}
            </Typography>
            <Typography gutterBottom>{props.value}</Typography>
        </Grid>
    )
}