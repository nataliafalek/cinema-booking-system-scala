export const USER_LOGIN = "USER_LOGIN";
export const CHOSEN_MOVIE = "CHOSEN_MOVIE";

export function loginUser(user) {
    return {
        type: USER_LOGIN,
        user: user
    }
}

export function chooseMovie(chosenMovie) {
    return {
        type: CHOSEN_MOVIE,
        chosenMovie: chosenMovie
    }
}