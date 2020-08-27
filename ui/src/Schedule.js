import React, {useEffect} from 'react';
import _ from 'lodash';
import moment from 'moment';
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import TableContainer from "@material-ui/core/TableContainer";
import useStyles from "./material-styles/useStyles";
import Button from "@material-ui/core/Button";
import TodayIcon from "@material-ui/icons/Today";
import * as HttpService from "./http/HttpService";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import Container from "@material-ui/core/Container";
import CssBaseline from "@material-ui/core/CssBaseline";
import Grid from "@material-ui/core/Grid";
import DialogActions from "@material-ui/core/DialogActions";
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

export default function Schedule() {
    const daysOfWeek = _.range(0, 7, 1).map(day =>
        moment().add(day, 'days').format("YYYY-MM-DD"))
    const hours = _.range(0, 14, 1).map(hour =>
        moment().hours(10).minutes(0).seconds(0).milliseconds(0).add(hour, 'hour').format("HH:mm:ss"))
    const classes = useStyles();

    return(
        <div>
            <ScheduleDialog/>
            <TableContainer component={Paper}>
                <Table className={classes.table} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Hour</TableCell>
                            {daysOfWeek.map ( day => <TableCell>{day}</TableCell>)}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {hours.map((row, idx) => (
                            <TableRow key={`${row}-${idx}`}>
                                <TableCell component="th" scope="row">
                                    {row}
                                </TableCell>
                                <TableCell align="right"></TableCell>
                                <TableCell align="right"></TableCell>
                                <TableCell align="right"></TableCell>
                                <TableCell align="right"></TableCell>
                                <TableCell align="right"></TableCell>
                                <TableCell align="right"></TableCell>
                                <TableCell align="right"></TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>

    )
}


function ScheduleDialog(props) {
    const [open, setOpen] = React.useState(false);
    const [cinemaHalls, setCinemaHalls] = React.useState([]);
    const [movies, setMovies] = React.useState([]);
    const [cinemaHallId, setCinemaHallId] = React.useState(1)
    const [dateOfProjection, setDateOfProjection] = React.useState(null)
    const [movie, setMovie] = React.useState('')
    const classes = useStyles();

    useEffect(() => {
        listAllMovies()
    });

    const listAllMovies = () => {
        HttpService.fetchJson('movie/list').then( movies => {
            setMovies(movies)
        })
    }

    const listAllCinemaHalls = () => {
        HttpService.fetchJson('cinemahall/list').then( cinemahalls => {
            setCinemaHalls(cinemahalls)
        })
    }

    const openDialog = () => {
        setOpen(true);
    }

    const closeDialog = () => {
        setOpen(false);
    }

    const handleMovieID = (event) => {
        setMovie(event.target.value);
    };

    return (
        <div>
            <Button
                variant="contained"
                color="secondary"
                className={classes.button}
                startIcon={<TodayIcon />}
                onClick={openDialog}
            >
                New schedule
            </Button>
            <Dialog open={open} onClose={closeDialog} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Add new scheduled movie</DialogTitle>
                <DialogContent>
                    <DialogContentText>Add schedule for this week.</DialogContentText>
                    <Container component="main" maxWidth="xs">
                        <CssBaseline />
                        <div className={classes.paper}>
                            <form className={classes.form} noValidate>
                                <Grid container spacing={2}>
                                    <FormControl variant="outlined" className={classes.formControl}>
                                        <InputLabel id="demo-simple-select-outlined-label">Movie</InputLabel>
                                        <Select
                                            labelId="demo-simple-select-outlined-label"
                                            id="demo-simple-select-outlined"
                                            value={movie.title}
                                            onChange={handleMovieID}
                                            label="Movie"
                                        >
                                            {movies.map ( movie =>
                                                <MenuItem value={movie.id}>{movie.title}</MenuItem>
                                            )}
                                        </Select>
                                    </FormControl>
                                </Grid>
                            </form>
                        </div>
                    </Container>
                </DialogContent>
                <DialogActions>
                    <Button onClick={closeDialog} color="primary">Cancel</Button>
                    <Button color="primary">Add</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}