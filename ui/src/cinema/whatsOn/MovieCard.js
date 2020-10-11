import useStyles from "../../material-styles/useStyles";
import Card from "@material-ui/core/Card";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import React from "react";

export default function MovieCard(props) {
    const classes = useStyles();

    return (
        props.movies.map((movie, idx) => <Card key={`${movie.title}-${idx}`} className={classes.movieCardContent}>
                <CardMedia
                    className={classes.media}
                    image={movie.imageUrl}
                />
                <CardContent >
                    <Typography className={classes.title} color="textSecondary" gutterBottom>
                        {movie.title}
                    </Typography>
                </CardContent>
                <CardActions>
                    {movie.startHours.map(hour =>
                        <Button size="small" key={hour}>{hour}</Button>
                    )}
                </CardActions>
            </Card>
        )
    )
}