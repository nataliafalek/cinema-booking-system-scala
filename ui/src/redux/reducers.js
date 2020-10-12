import {USER_LOGIN, CHOSEN_MOVIE} from "./actions";

const initialState = {
    user: {
        name: null,
        lastName: null
    },
    chosenMovie: {
        title: null,
        movieId: null,
        startHour: null,
        scheduledMovieId: null
    },
}

export default function(state = initialState, action) {
    switch(action.type) {
        case USER_LOGIN:
            return {
                ...state,
                user: action.user
            }
        case CHOSEN_MOVIE:
            return {
                ...state,
                chosenMovie: action.chosenMovie
            }
        default:
            return state;
    }
}