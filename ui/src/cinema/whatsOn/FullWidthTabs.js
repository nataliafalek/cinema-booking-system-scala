import useStyles from "../../material-styles/useStyles";
import _ from "lodash";
import moment from "moment";
import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import MovieCard from "./MovieCard";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import {Paper} from "@material-ui/core";

export default function FullWidthTabs(props) {
    const classes = useStyles();
    const daysWithIndexList = _.range(0, 7).map(idx => {
            const dayName = moment().add(idx, 'days').format('dddd').toUpperCase()
            return {[dayName]: idx}
        }
    )
    const daysWithIndex = _.reduce(daysWithIndexList, _.extend)
    const [dayIndex, setDayIndex] = React.useState(0);

    const handleChange = (event, newValue) => {
        setDayIndex(newValue);
    };

    return (
        <div>
            <Paper className={classes.contentBackground}>
                <AppBar position="static" color="inherit" className={classes.whatsOnAppBar}>
                    <Tabs
                        value={dayIndex}
                        onChange={handleChange}
                        indicatorColor="inherit"
                        textColor="inherit"
                        variant="fullWidth"
                        aria-label="full width tabs example"
                        className={classes.whatsOnTabs}
                    >
                        {_.map(daysWithIndex, (movies, day) =>
                            <Tab className={classes.whatsOnTab}
                                 label={<span className={classes.tabLabel}>{day}</span>} {...({
                                id: `full-width-tab-${daysWithIndex[day]}`,
                                'aria-controls': `full-width-tabpanel-${daysWithIndex[day]}`,
                            })} key={daysWithIndex[day]}/>
                        )}
                    </Tabs>
                </AppBar>
                {_.map(daysWithIndex, (index, day) =>
                    <TabPanelContent key={index} index={index} moviesByDay={props.moviesByDay} day={day}
                                     dayIndex={dayIndex}/>
                )}
            </Paper>
        </div>
    );
}


function TabPanel(props) {
    const {children, value, index, ...other} = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`full-width-tabpanel-${index}`}
            aria-labelledby={`full-width-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box p={3}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

function TabPanelContent(props) {
    const moviesByDay = props.moviesByDay[props.day]
    return (
        <TabPanel value={props.dayIndex} index={props.index}>
            {!_.isEmpty(moviesByDay) ? <MovieCard key={props.index} movies={moviesByDay}/> : <NoRrepertoire/>}
        </TabPanel>
    )
}

function NoRrepertoire() {
    const classes = useStyles();
    return (
        <div className={classes.noRepertoire}>
            No repertoire
        </div>
    )
}
