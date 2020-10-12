import useStyles from "../../material-styles/useStyles";
import React, {useEffect} from "react";
import {useSelector} from "react-redux";
import * as HttpService from "../../http/HttpService";
import _ from "lodash";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import TextField from '@material-ui/core/TextField';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';

export default function CinemaHallView() {
    const chosenMovie = useSelector(state => state.chosenMovie)
    const [cinemaHall, setCinemaHall] = React.useState([]);
    const [ticketPrices, setTicketPrices] = React.useState([]);
    const [chosenSeatsAndPrices, setChosenSeatsAndPrices] = React.useState([]);
    const classes = useStyles();

    useEffect(() => {
        listCinemaHall()
        listTicketPrices()
    }, []);

    const listCinemaHall = () => {
        HttpService.fetchJson(`cinemahall/list/${chosenMovie.scheduledMovieId}`).then(cinemaHall => {
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
        const includesSeat = chosenSeatsAndPrices.map(chosenSeatAndPrice => chosenSeatAndPrice.seat.id).includes(seat.id);
        if(!includesSeat) {
            setChosenSeatsAndPrices([...chosenSeatsAndPrices, seatAndPrice])
        }
    }

    const setPrice = (seat, price) => {
        const chosenSeatsAndPricesCopy = [...chosenSeatsAndPrices]
        const newSeatAndPrice = {
            seat: seat,
            price: price
        }
        const updatedPrice = chosenSeatsAndPricesCopy.map(seatAndPrice => seatAndPrice.seat.id === seat.id ? newSeatAndPrice : seatAndPrice)

        setChosenSeatsAndPrices(updatedPrice)
    }

    const deleteSeatAndPrice = (seat) => {
        const chosenSeatsAndPricesCopy = [...chosenSeatsAndPrices]
        const updatedList = _.filter(chosenSeatsAndPricesCopy, seatAndPrice => seatAndPrice.seat.id !== seat.id)

        setChosenSeatsAndPrices(updatedList)
    }

    const renderSeat = (seat) => {
        const includesSeat = chosenSeatsAndPrices.map(chosenSeatAndPrice => chosenSeatAndPrice.seat.id).includes(seat.id);
        const seatClass = includesSeat ? classes.cinemaHallSeatReserved : classes.cinemaHallSeatFree;
        return (
            <div className={seatClass}
                 key={`${seat.columnNumber}-${seat.columnNumber}-${seat.rowNumber}`}
                 onClick={() => chooseSeats(seat)}>
                {seat.seatNumber}
            </div>
        )
    }

    return (
        <div className={classes.cinemaHall}>
            <div>Title: {chosenMovie.title}</div>
            <div>Hour: {chosenMovie.startHour}</div>
            {_.map(cinemaHall, (rows, columnNumber) =>
                <div key={columnNumber} className={classes.cinemaHallRow}>
                    {rows.map(seat => renderSeat(seat))}
                </div>
            )}
            <div>
                {!_.isEmpty(chosenSeatsAndPrices) ? chosenSeatsAndPrices.map((seatAndPrice) =>
                    <form key={`${seatAndPrice.seat.columnNumber}-${seatAndPrice.seat.columnNumber}-${seatAndPrice.seat.rowNumber}`}
                          className={classes.root} noValidate autoComplete="off">
                        <SeatTextField value={seatAndPrice.seat.seatNumber} label={"Seat number"} />
                        <SeatTextField value={seatAndPrice.seat.rowNumber} label={"Row number"} />
                        <SeatTextField value={seatAndPrice.seat.columnNumber} label={"Column number"} />
                        <FormControl className={classes.formControl}>
                            <InputLabel id="demo-simple-select-label">Price</InputLabel>
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
                        <IconButton aria-label="delete" className={classes.margin} onClick={(() => deleteSeatAndPrice(seatAndPrice.seat))}>
                            <DeleteIcon fontSize="small" />
                        </IconButton>
                    </form>
                ) : null}
            </div>
        </div>
    )
}

function SeatTextField(props) {
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