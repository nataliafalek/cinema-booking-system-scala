import useStyles from "../material-styles/useStyles";
import Carousel from "react-material-ui-carousel";
import {Paper} from "@material-ui/core";
import React from "react";

export default function MovieCarousel() {
    const classes = useStyles();
    return (
        <Carousel className={classes.movieCarousel}>
            <Paper className={classes.petlaImage}>
                <p className={classes.movieCarouselHeader}>Pętla</p>
            </Paper>
            <Paper className={classes.badboyImage}>
                <p className={classes.movieCarouselHeader}>Bad Boy</p>
            </Paper>
        </Carousel>
    )
}
