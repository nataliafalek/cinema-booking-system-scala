import React from 'react';
import Drawer from '@material-ui/core/Drawer';
import CssBaseline from '@material-ui/core/CssBaseline';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MovieIcon from '@material-ui/icons/Movie';
import PersonIcon from '@material-ui/icons/Person';
import TodayIcon from '@material-ui/icons/Today';
import useStyles from '../material-styles/useStyles';
import {HashRouter, NavLink, Route} from "react-router-dom";
import Movies from "./Movies";
import Profile from "./Profile";
import LoginUser from "./LoginUser";
import Schedule from "./Schedule";
import {Divider} from "@material-ui/core";
import AppBarTop from "../common/AppBarTop";

export default function BoAppSkeleton() {
    const classes = useStyles();

    const isLogged = () => {
        //TODO temporarily commented
        // const user = useSelector(state => state.user)
        // return _.values(user).some(value => value !== null)
        return true
    }
    return (
        isLogged() ? <div className={classes.root}>
                <CssBaseline/>
                <AppBarTop/>
                <HashRouter>
                    <main className={classes.content}>
                        <Toolbar/>
                        <SidebarMenu styles={classes}/>
                        <Route path="/bo/movies" component={Movies}/>
                        <Route path="/bo/profile" component={Profile}/>
                        <Route path="/bo/schedule" component={Schedule}/>
                    </main>
                </HashRouter>
            </div> :
            <HashRouter>
                <CssBaseline/>
                <Route path="/" component={LoginUser}/>
            </HashRouter>
    );
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
                    <MenuItem styles={props.styles} name={"MOVIES"} link={"/bo/movies"} icon={<MovieIcon/>} />
                    <MenuItem styles={props.styles} name={"SCHEDULE"} link={"/bo/schedule"} icon={<TodayIcon/>} />
                    <Divider/>
                    <MenuItem styles={props.styles} name={"PROFILE"} link={"/bo/profile"} icon={<PersonIcon/>} />
                </List>
            </div>
        </Drawer>
    )
}