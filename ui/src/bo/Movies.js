import React, {useEffect} from 'react';
import useStyles from "../material-styles/useStyles";
import Button from '@material-ui/core/Button';
import MovieIcon from '@material-ui/icons/Movie';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormItem from '../common/FormItem'
import Grid from '@material-ui/core/Grid';
import Container from '@material-ui/core/Container';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import * as HttpService from '../http/HttpService';
import _ from 'lodash';

export default function Movies() {
    const classes = useStyles();
    const [movies, setMovies] = React.useState([]);

    useEffect(() => {
        listAllMovies()
    }, []);

    const listAllMovies = () => {
        HttpService.fetchJson('movie/list').then( movies => {
            setMovies(movies)
        })
    }

    return (
        <div>
            <MovieDialog styles={classes} refreshMoviesData={listAllMovies} />
            <TableContainer component={Paper}>
                <Table className={classes.table} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Title</TableCell>
                            <TableCell align="right">Description</TableCell>
                            <TableCell align="right">Duration (seconds)</TableCell>
                            <TableCell align="right">Image URL</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {movies.map((row, idx) => (
                            <TableRow key={`${row.title}-${idx}`}>
                                <TableCell component="th" scope="row">
                                    {row.title}
                                </TableCell>
                                <TableCell align="right">{row.description}</TableCell>
                                <TableCell align="right">{row.durationInSeconds}</TableCell>
                                <TableCell align="right">{row.imageUrl}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    )
}

function MovieDialog(props) {
    const [open, setOpen] = React.useState(false);
    const [title, setTitle] = React.useState("")
    const [description, setDescription] = React.useState("")
    const [imageUrl, setImageUrl] = React.useState("")
    const [duration, setDuration] = React.useState(0)

    const openDialog = () => {
        setOpen(true);
    }

    const closeDialog = () => {
        setOpen(false);
    }

    const addMovie = () => {
        const newMovie = {
            title: title,
            description: description,
            imageUrl: imageUrl,
            durationInSeconds: _.toNumber(duration)
        }
        HttpService.postJson('movie/add', newMovie)
            .then(result => {
                if (result.status === 200) {
                    closeDialog()
                    props.refreshMoviesData()
                }
            })
    }

    return (
        <div>
            <Button
                variant="contained"
                color="secondary"
                className={props.styles.button}
                startIcon={<MovieIcon />}
                onClick={openDialog}
            >
                Add movie
            </Button>
            <Dialog open={open} onClose={closeDialog} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Add new movie</DialogTitle>
                <DialogContent>
                    <DialogContentText>Add new movie to the repertoire.</DialogContentText>
                    <Container component="main" maxWidth="xs">
                        <CssBaseline />
                        <div className={props.styles.paper}>
                            <form className={props.styles.form} noValidate>
                                <Grid container spacing={2}>
                                    <FormItem label={"Title"} isMultiline={false} setFunc={setTitle}/>
                                    <FormItem label={"Description"} isMultiline={true} setFunc={setDescription}/>
                                    <FormItem label={"Image url"} isMultiline={false} setFunc={setImageUrl}/>
                                    <FormItem label={"Duration (seconds)"} type={"number"} isMultiline={false} setFunc={setDuration}/>
                                </Grid>
                            </form>
                        </div>
                    </Container>
                </DialogContent>
                <DialogActions>
                    <Button onClick={closeDialog} color="primary">Cancel</Button>
                    <Button onClick={addMovie} color="primary">Add</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}