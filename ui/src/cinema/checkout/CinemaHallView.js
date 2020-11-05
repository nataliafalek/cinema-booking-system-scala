import useStyles from "../../material-styles/useStyles";
import React, {useEffect} from "react";
import * as HttpService from "../../http/HttpService";
import _ from "lodash";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import useLocalStorage from "../../localstorage/useLocalStorage";
import SeatTextField from "../../common/SeatTextField";
import CheckoutNextButton from "./CheckoutNextButton";
import Grid from "@material-ui/core/Grid";

export default function CinemaHallView(props) {
    const chosenMovie = useLocalStorage('chosenMovie')[0];
    const [cinemaHall, setCinemaHall] = React.useState([]);
    const [ticketPrices, setTicketPrices] = React.useState([]);
    const [chosenSeatsAndPrices, setChosenSeatsAndPrices] = React.useState([]);
    const [chosenSeatsAndPricesStorage, setChosenSeatsAndPricesStorage] = useLocalStorage('chosenSeatsAndPrices', null);

    const classes = useStyles();

    useEffect(() => {
        listCinemaHall()
        listTicketPrices()
    }, []);

    const listCinemaHall = () => {
        HttpService.fetchJson(`cinemahall/seats/list/${chosenMovie.scheduledMovieId}`).then(cinemaHall => {
            setCinemaHall(cinemaHall)
        })
    }

    const listTicketPrices = () => {
        HttpService.fetchJson("ticket/prices/list").then(ticketPrices => {
            setTicketPrices(ticketPrices)
        })
    }

    const chooseSeats = (seat) => {
        const seatAndPrice = {
            seat: seat,
            price: ''
        }
        const includesSeat = chosenSeatsAndPrices.map(chosenSeatAndPrice =>
            chosenSeatAndPrice.seat.seatId).includes(seat.seatId);
        if (!includesSeat) {
            setChosenSeatsAndPrices([...chosenSeatsAndPrices, seatAndPrice])
        }
    }

    const setPrice = (seat, price) => {
        const chosenSeatsAndPricesCopy = [...chosenSeatsAndPrices]
        const newSeatAndPrice = {
            seat: seat,
            price: price
        }
        const updatedPrice = chosenSeatsAndPricesCopy.map(seatAndPrice =>
            seatAndPrice.seat.seatId === seat.seatId ? newSeatAndPrice : seatAndPrice)

        setChosenSeatsAndPrices(updatedPrice)
    }

    const deleteSeatAndPrice = (seat) => {
        const chosenSeatsAndPricesCopy = [...chosenSeatsAndPrices]
        const updatedList = _.filter(chosenSeatsAndPricesCopy, seatAndPrice => seatAndPrice.seat.seatId !== seat.seatId)

        setChosenSeatsAndPrices(updatedList)
    }

    const renderSeat = (seat) => {
        const includesSeat = chosenSeatsAndPrices.map(chosenSeatAndPrice =>
            chosenSeatAndPrice.seat.seatId).includes(seat.seatId) || !seat.isFree;
        const seatClass = includesSeat ? classes.cinemaHallSeatReserved : classes.cinemaHallSeatFree;
        return (
            <div className={seatClass}
                 key={seat.seatId}
                 onClick={() => chooseSeats(seat)}>
                {seat.seatNumber}
            </div>
        )
    }

    const chooseSeatsAndPrices = () => {
        setChosenSeatsAndPricesStorage(chosenSeatsAndPrices)
        props.handleNext()
    }

    return (
        <div className={classes.cinemaHall}>
            <div className={classes.cinemaHallViewHeader}><span>Title: {chosenMovie.title}</span></div>
            <div className={classes.cinemaHallViewHeader}><span>Hour: {chosenMovie.startHour}</span></div>
            <div className={classes.cinemaHallView}>
                <div className={classes.cinemaHallViewScreen}>SCREEN</div>
                {_.map(cinemaHall, (rows, rowNumber) =>
                    <div key={rowNumber} className={classes.cinemaHallRow}>
                        {rows.map(seat => renderSeat(seat))} <span
                        className={classes.cinemaHallViewRowNumber}>{rowNumber}</span>
                    </div>
                )}
            </div>
            <div>
                {!_.isEmpty(chosenSeatsAndPrices) ? chosenSeatsAndPrices.map((seatAndPrice) =>
                    <Grid container
                          spacing={4}
                          key={`${seatAndPrice.seat.columnNumber}-${seatAndPrice.seat.columnNumber}-${seatAndPrice.seat.rowNumber}`}>
                        <Grid item xs={3}>
                            <SeatTextField value={seatAndPrice.seat.seatNumber} label={"Seat number"}
                                           classNameProps={classes.cinemaHallViewForm}/>
                        </Grid>
                        <Grid item xs={3}>
                            <SeatTextField value={seatAndPrice.seat.rowNumber} label={"Row number"}
                                           classNameProps={classes.cinemaHallViewForm}/>
                        </Grid>
                        <Grid item xs={3}>
                            <FormControl className={classes.formControl}>
                                <InputLabel>Price</InputLabel>
                                <Select
                                    labelId="demo-simple-select-outlined-label"
                                    id="demo-simple-select-outlined"
                                    value={seatAndPrice.price}
                                    onChange={(event) =>
                                        setPrice(seatAndPrice.seat, event.target.value)}
                                    label="Price"
                                >
                                    {ticketPrices.map((ticket, idx) =>
                                        <MenuItem key={idx}
                                                  value={ticket}>{ticket.ticketType} - {ticket.ticketPrice}$</MenuItem>
                                    )}
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={3}>
                            <IconButton color={"inherit"} size={"large"} aria-label="delete" className={classes.margin}
                                        onClick={(() =>
                                            deleteSeatAndPrice(seatAndPrice.seat))}>
                                <DeleteIcon fontSize="small"/>
                            </IconButton>
                        </Grid>
                    </Grid>
                ) : null}
            </div>
            <CheckoutNextButton function={chooseSeatsAndPrices}
                                disabled={_.isEmpty(chosenSeatsAndPrices) ||
                                _.some(chosenSeatsAndPrices, (chosenSeatAndPrice) =>
                                    _.isEmpty(chosenSeatAndPrice.price))}
                                name={"Next"}/>
        </div>
    )
}