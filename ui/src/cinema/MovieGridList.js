import React, {useEffect} from "react";
import useStyles from "../material-styles/useStyles";
import * as HttpService from "../http/HttpService";
import GridList from "@material-ui/core/GridList";
import GridListTile from "@material-ui/core/GridListTile";
import GridListTileBar from "@material-ui/core/GridListTileBar";

export default function MovieGridList() {
    const [movies, setMovies] = React.useState([]);
    const classes = useStyles();

    useEffect(() => {
        listAllMovies()
    }, []);

    const listAllMovies = () => {
        HttpService.fetchJson('movie/list').then( movies => {
            setMovies(movies)
        })
    }

    return (
        <div className={classes.movieList}>
            <GridList className={classes.gridList} cols={4}>
                {movies.map((movie) => (
                    <GridListTile key={movie.title}>
                        <img src={movie.imageUrl} alt={movie.title} />
                        <GridListTileBar
                            title={movie.title}
                            classes={{
                                root: classes.titleBar,
                                title: classes.movieTitle,
                            }}
                        />
                    </GridListTile>
                ))}
            </GridList>
        </div>
    );
}