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

export default function AppSkeleton() {
    const classes = useStyles();

    return (
        <div className={classes.root}>
            <CssBaseline/>
            <AppBarTop styles={classes}/>
            <HashRouter>
                <main className={classes.content}>
                    <Toolbar/>
                    <SidebarMenu styles={classes}/>
                    <Route path="/movies" component={Movies}/>
                    <Route path="/profile" component={Profile}/>
                </main>
            </HashRouter>
        </div>
    );
}

function AppBarTop(props) {
    return(
        <AppBar position="fixed" className={props.styles.appBar}>
            <Toolbar>
                <Typography variant="h6" className={props.styles.title}>
                    NatiCinemaBO
                </Typography>
                <Button color="inherit">
                    <span className={props.styles.spanRedColor}>Login</span>
                </Button>
            </Toolbar>
        </AppBar>
    )
}

function MenuItem(props) {
    return(
        <ListItem button key={props.name} component={NavLink} to={props.link}>
            <ListItemIcon className={props.styles.drawerIcon}>{props.icon}</ListItemIcon>
            <ListItemText primary={props.name}/>
        </ListItem>
    )
}

function SidebarMenu(props) {
    return(
        <Drawer className={props.styles.drawer} variant="permanent" classes={{paper: props.styles.drawerPaper}}>
            <Toolbar/>
            <div className={props.styles.drawerContainer}>
                <List>
                    <MenuItem styles={props.styles} name={"MOVIES"} link={"/movies"} icon={<MovieIcon/>} />
                    <MenuItem styles={props.styles} name={"PROFILE"} link={"/profile"} icon={<PersonIcon/>} />
                </List>
            </div>
        </Drawer>
    )
}