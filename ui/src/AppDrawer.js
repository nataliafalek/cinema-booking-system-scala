import React from 'react';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import CssBaseline from '@material-ui/core/CssBaseline';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MovieIcon from '@material-ui/icons/Movie';
import PersonIcon from '@material-ui/icons/Person';
import useStyles from './material-styles/useStyles';
import Button from '@material-ui/core/Button';
import HashRouter from "react-router-dom/HashRouter";
import Route from "react-router-dom/Route";
import Movies from "./Movies";
import NavLink from "react-router-dom/NavLink";
import Profile from "./Profile";

export default function AppDrawer() {
    const classes = useStyles();

    return (
        <div className={classes.root}>
            <HashRouter>
                <Route path="/movies" component={Movies}/>
                <Route path="/profile" component={Profile}/>
                <CssBaseline/>
                <AppBar position="fixed" className={classes.appBar}>
                    <Toolbar>
                        <Typography variant="h6" className={classes.title}>
                            NatiCinemaBO
                        </Typography>
                        <Button color="inherit">
                            <span className={classes.spanRedColor}>Login</span>
                        </Button>
                    </Toolbar>
                </AppBar>
                <Drawer
                    className={classes.drawer}
                    variant="permanent"
                    classes={{
                        paper: classes.drawerPaper,
                    }}
                >
                    <Toolbar/>
                    <div className={classes.drawerContainer}>
                        <List>
                            <ListItem button key={"movies"} component={NavLink} to="/movies">
                                <ListItemIcon className={classes.drawerIcon}><MovieIcon/></ListItemIcon>
                                <ListItemText primary={"MOVIES"}/>
                            </ListItem>
                            <ListItem button key={"profile"} component={NavLink} to="/profile">
                                <ListItemIcon className={classes.drawerIcon}><PersonIcon/></ListItemIcon>
                                <ListItemText primary={"PROFILE"}/>
                            </ListItem>
                        </List>
                    </div>
                </Drawer>
            </HashRouter>
        </div>
    );
}