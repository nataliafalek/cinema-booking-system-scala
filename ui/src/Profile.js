import React, {Component} from 'react';
import Toolbar from "@material-ui/core/Toolbar";
import useStyles from "./material-styles/useStyles";

export default function Movies() {
    const classes = useStyles();

    return(
        <main className={classes.content}>
            <Toolbar/>
            PROFILES
        </main>
    )
}