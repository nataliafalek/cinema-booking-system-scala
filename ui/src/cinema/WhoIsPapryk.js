import React from "react";
import useStyles from "../material-styles/useStyles";
import {Paper} from "@material-ui/core";

export default function WhoIsPapryk() {
    const classes = useStyles();

    return (
        <div className={"whoIsPapryk"}>
            <Paper className={classes.contentBackground}>
                <img src={"/paprykvega.jpeg"} alt={""} className={classes.whoIsPaprykImage}/>
            </Paper>
        </div>
    )
}