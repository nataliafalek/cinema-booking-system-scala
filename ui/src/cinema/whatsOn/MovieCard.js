import useStyles from "../../material-styles/useStyles";
import Card from "@material-ui/core/Card";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import React from "react";
import {useHistory} from "react-router-dom";
import useLocalStorage from "../../localstorage/useLocalStorage";
import Grid from "@material-ui/core/Grid";

export default function MovieCard(props) {
    const history = useHistory();
    const classes = useStyles();
    const [chosenMovie, setChosenMovie] = useLocalStorage('chosenMovie', null);

    const chooseMovie = (movie, scheduledMovieWithHours) => {
        const chosenMovie = {
            title: movie.title,
            movieId: movie.id,
            startHour: scheduledMovieWithHours[1],
            scheduledMovieId: scheduledMovieWithHours[0]
        }
        setChosenMovie(chosenMovie)
        history.push("/cinema/checkout");
    }

    return (
        <Grid container spacing={4}>{
            props.movies.map((movie, idx) =>
                <Grid item key={movie.title} xs={12}>
                    <Card key={`${movie.title}-${idx}`} className={classes.movieCardContent}>
                        <CardMedia
                            className={classes.movieCardMedia}
                            image={movie.imageUrl}
                        />
                        <CardContent>
                            <Typography className={classes.movieCardMediaTitle} variant={"h4"} color="inherit"
                                        gutterBottom>
                                {movie.title}
                            </Typography>
                        </CardContent>
                        <CardActions>
                            {movie.scheduledMovieIdWithStartTime.map((scheduledMovieWithHours) =>
                                <Button size="large"
                                        color={"inherit"}
                                        key={scheduledMovieWithHours[0]} onClick={() =>
                                    chooseMovie(movie, scheduledMovieWithHours)}>
                                    <span className={classes.movieCardMediaHour}>
                                     {scheduledMovieWithHours[1]}
                                    </span>
                                </Button>
                            )}
                        </CardActions>
                    </Card>
                </Grid>
            )
        }
        </Grid>
    )
}