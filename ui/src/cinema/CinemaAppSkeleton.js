import React from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import Toolbar from '@material-ui/core/Toolbar';
import useStyles from '../material-styles/useStyles';
import {HashRouter, Route} from "react-router-dom";
import Content from "./Content";
import AppBar from '@material-ui/core/AppBar';
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import WhoIsPapryk from "./WhoIsPapryk";

export default function CinemaAppSkeleton() {
    const classes = useStyles();
    return (
         <div className={classes.cinemaRoot}>
             <AppBar position="fixed" className={classes.appBar}>
                 <Toolbar className={classes.toolBar}>
                     <Typography variant="h6" className={classes.title}>
                         <Button href="/" color="inherit">PaprykCinema</Button>
                     </Typography>
                     <Typography className={classes.menuBar}>
                     <Button color="inherit">
                         <span className={classes.spanRedColor}>What's on?</span>
                     </Button>
                     </Typography>
                     <Typography className={classes.menuBar}>
                         <Button href="#/cinema/papryk" color="inherit">Who's Papryk?</Button>
                     </Typography>
                 </Toolbar>
             </AppBar>
             <CssBaseline/>
                <HashRouter>
                    <div>
                        <Route exact path="/" component={Content}/>
                        <Route exact path="/cinema/papryk" component={WhoIsPapryk}/>
                    </div>
                </HashRouter>
            </div>
    );
}