import {createMuiTheme} from "@material-ui/core/styles";

export const muiTheme = createMuiTheme({
    palette: {
        type: 'dark',
    },
    overrides: {
        MuiStepIcon: {
            root: {
                color: '#2C003D',
                '&$active': {
                    color: '#E299FF',
                },
                '&$completed': {
                    color: '#E299FF',
                },
            },
        },
        MuiStepLabel: {
            label: {
                color: '#B30089',
                fontSize: 20,
                '&$active': {
                    color: '#B30089',
                },
                '&$completed': {
                    color: '#AF00F5',
                }
            }
        },
    }
});
