import React from 'react';
import useStyles from "./material-styles/useStyles";
import Button from '@material-ui/core/Button';
import MovieIcon from '@material-ui/icons/Movie';

export default function Movies() {
    const classes = useStyles();

    return (
        <div>
            <Button
                variant="contained"
                color="secondary"
                className={classes.button}
                startIcon={<MovieIcon />}
            >
                Add movie
            </Button>
        </div>
    )
}