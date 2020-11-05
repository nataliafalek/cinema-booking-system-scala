import React from "react";
import MovieCarousel from "./MovieCarousel";
import MovieGridList from "./MovieGridList";
import {Paper} from "@material-ui/core";
import useStyles from "../material-styles/useStyles";

export default function Content() {
    const classes = useStyles();

    return (
        <>
            <MovieCarousel/>
            <Paper className={classes.contentBackground}>
                <MovieGridList/>
            </Paper>
        </>
    )
}