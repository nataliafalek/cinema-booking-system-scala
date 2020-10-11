import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import useStyles from '../material-styles/useStyles';
import Button from '@material-ui/core/Button';

export default function AppBarTop() {
    const classes = useStyles();
    return(
        <AppBar position="fixed" className={classes.appBar}>
            <Toolbar>
                <Typography variant="h6" className={classes.title}>
                    <Button href="/" color="inherit">PaprykCinema</Button>
                </Typography>
                <Button color="inherit">
                    <span className={classes.spanRedColor}>Log out</span>
                </Button>
            </Toolbar>
        </AppBar>
    )
}