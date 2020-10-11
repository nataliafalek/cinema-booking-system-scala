import React, {useEffect} from 'react';
import useStyles from "../../material-styles/useStyles";
import * as HttpService from "../../http/HttpService";
import _ from "lodash";
import FullWidthTabs from "./FullWidthTabs";

export default function WhatsOn() {
    const classes = useStyles();
    const [scheduledMovies, setScheduledMovies] = React.useState([]);

    useEffect(() => {
        listScheduledMovies()
    }, []);

    const listScheduledMovies = () => {
        HttpService.fetchJson('schedule/list').then( movies => {
            setScheduledMovies(movies)
        })
    }

    return (
        <div className={classes.whatsOn}>
            {!_.isEmpty(scheduledMovies) ? <FullWidthTabs moviesByDay={scheduledMovies}/> : null}
        </div>
    )
}

