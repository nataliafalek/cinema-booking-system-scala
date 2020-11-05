import React, {useEffect} from "react";
import useStyles from "../../material-styles/useStyles";
import * as HttpService from "../../http/HttpService";
import Grid from "@material-ui/core/Grid";
import {Paper} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";

export default function MovieDetails(props) {
    const classes = useStyles();
    const movieId = props.match.params.movieId
    const [movie, setMovie] = React.useState('');

    useEffect(() => {
        findMovieDetails()
    }, []);

    const findMovieDetails = () => {
        HttpService.fetchJson(`movie/details/${movieId}`).then(movie => {
            console.log("movie", movie)
            setMovie(movie)
        })
    }

    return (
        <Paper className={classes.contentBackground}>
            <div className={classes.movieDetailsContent}>
                <Grid container spacing={2}>
                    <Grid item key={movie.title} xs={6}>
                        <img src={movie.imageUrl} alt={""} className={classes.movieDetailsImage}/>
                    </Grid>
                    <Grid item xs={6}>
                        <div className={classes.movieDetailsDescription}>
                            <Typography component="h1" variant="h2" className={classes.movieDetailsTitle}>
                                {movie.title}
                            </Typography>
                            <Typography component="h1" variant="h5">
                                {movie.description}
                            </Typography>
                        </div>

                    </Grid>
                </Grid>
            </div>
        </Paper>
    )
}