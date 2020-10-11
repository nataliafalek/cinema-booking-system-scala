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
            <AppBar position="static" color="default">
                <Tabs
                    value={dayIndex}
                    onChange={handleChange}
                    indicatorColor="primary"
                    textColor="primary"
                    variant="fullWidth"
                    aria-label="full width tabs example"
                    className={classes.whatsOnTabs}
                >
                    {_.map(daysWithIndex, (movies, day) =>
                        <Tab className={classes.whatsOnTab} label={day} {...({
                            id: `full-width-tab-${daysWithIndex[day]}`,
                            'aria-controls': `full-width-tabpanel-${daysWithIndex[day]}`,
                        })} key={daysWithIndex[day]}/>
                    )}
                </Tabs>
            </AppBar>
            {_.map(daysWithIndex, (index, day) =>
                <TabPanelContent key={index} index={index} moviesByDay={props.moviesByDay} day={day} dayIndex={dayIndex} />
            )}
        </div>
    );
}


function TabPanel(props) {
    const { children, value, index, ...other } = props;

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
    return(
        <TabPanel value={props.dayIndex} index={props.index}>
            {!_.isEmpty(moviesByDay) ? <MovieCard key={props.index} movies={moviesByDay} /> : "No repertoire"}
        </TabPanel>
    )
}