import React, {useEffect} from "react";
import useStyles from "../material-styles/useStyles";
import * as HttpService from "../http/HttpService";
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardMedia from '@material-ui/core/CardMedia';
import Grid from '@material-ui/core/Grid';
import Container from '@material-ui/core/Container';

export default function MovieGridList() {
    const [movies, setMovies] = React.useState([]);
    const classes = useStyles();

    useEffect(() => {
        listAllMovies()
    }, []);

    const listAllMovies = () => {
        HttpService.fetchJson('movie/list').then(movies => {
            setMovies(movies)
        })
    }

    return (
        <Container className={classes.cardGrid} maxWidth={false}>
            <Grid container spacing={4}>
                {movies.map((movie) => (
                    <Grid item key={movie.title} xs={3}>
                        <Card className={classes.card}>
                            <CardMedia
                                className={classes.cardMedia}
                                image={movie.imageUrl}
                                title="Image title"
                            />
                            <CardActions>
                                <Button href={`#/cinema/movie/details/${movie.id}`} size="large" color="inherit">
                                    {movie.title}
                                </Button>
                            </CardActions>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
}