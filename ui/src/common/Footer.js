import React from 'react';
import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';
import useStyles from "../material-styles/useStyles";

export default function Footer(props) {
    const classes = useStyles();
    const {description, title} = props;

    return (
        <footer className={classes.footer}>
            <Container maxWidth={false}>
                <Typography variant="h6" align="center" gutterBottom>
                    {title}
                </Typography>
                <Typography variant="subtitle1" align="center" component="p">
                    {description}
                </Typography>
            </Container>
        </footer>
    );
}
