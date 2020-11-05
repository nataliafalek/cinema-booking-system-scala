import useStyles from "../material-styles/useStyles";
import Carousel from "react-material-ui-carousel";
import {Paper} from "@material-ui/core";
import React from "react";

export default function MovieCarousel() {
    const classes = useStyles();
    return (
        <Carousel className={classes.movieCarousel}>
            <div>
                <Paper className={classes.cinemaImage}/>
                <p className={classes.movieCarouselHeader}>Papryk Cinema</p>
                <p className={classes.movieCarouselText}>
                    Probably the best movie theater with the greatest movies in the world.
                    The cinema is located in Kraśnik and offers unique expirience.
                </p>
            </div>
            <div>
                <Paper className={classes.petlaImage}/>
                <p className={classes.movieCarouselHeader}>Pętla</p>
                <p className={classes.movieCarouselText}>
                    Based on a true story about a 2nd generation cop who has designs on becoming a detective
                    but his goals descend into bribery, corruption and addiction.</p>
            </div>
            <div>
                <Paper className={classes.marathonImage}/>
                <p className={classes.movieCarouselHeader}>Papryk Marathon</p>
                <p className={classes.movieCarouselText}>
                    All night Papryk marathon. Watch the best movies all night long!
                </p>
            </div>
            <div>
                <Paper className={classes.badboyImage}/>
                <p className={classes.movieCarouselHeader}>Bad Boy</p>
                <p className={classes.movieCarouselText}>
                    The rise and fall of a soccer club owner,
                    discovering the harsh reality of the sport, often connected with crime and fraud.
                </p>
            </div>
        </Carousel>
    )
}
