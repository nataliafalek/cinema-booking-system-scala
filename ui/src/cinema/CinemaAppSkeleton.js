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
import WhatsOn from "./whatsOn/WhatsOn";
import Checkout from "./checkout/Checkout";
import Footer from "../common/Footer";
import MovieDetails from "./whatsOn/MovieDetails";
import PaymentSuccess from "./checkout/PaymentSuccess";
import PaymentFailed from "./checkout/PaymentFailed";

export default function CinemaAppSkeleton() {
    const classes = useStyles();
    return (
        <div className={classes.cinemaRoot}>
            <AppBar position="fixed" color={'inherit'} className={classes.appBar}>
                <Toolbar className={classes.toolBar}>
                    <Typography variant="h6" className={classes.title}>
                        <Button href="/" color="inherit" className={classes.paprykLogoButton}>
                            <img className={classes.paprykLogo} src={"/logo-papryk-simple.png"} alt={""}/>
                        </Button>
                    </Typography>
                    <Button href="#/cinema/whatsOn" color="inherit"><span className={classes.menuTab2}>What's on?</span></Button>
                    <Button href="#/cinema/papryk" color="inherit"><span
                        className={classes.menuTab1}>Who's Papryk?</span></Button>
                </Toolbar>
            </AppBar>
            <CssBaseline/>
            <HashRouter>
                <div>
                    <Route exact path="/" component={Content}/>
                    <Route exact path="/cinema/papryk" component={WhoIsPapryk}/>
                    <Route exact path="/cinema/whatsOn" component={WhatsOn}/>
                    <Route exact path="/cinema/checkout" component={Checkout}/>
                    <Route exact path="/cinema/paymentSuccess" component={PaymentSuccess}/>
                    <Route exact path="/cinema/paymentFailed" component={PaymentFailed}/>
                    <Route path="/cinema/movie/details/:movieId" component={(props) => <MovieDetails {...props}/>}/>
                </div>
            </HashRouter>
            <Footer title={"Papryk Cinema"} description={"Probably the best movie theater in the world."}/>
        </div>
    );
}