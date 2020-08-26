import {USER_LOGIN} from "./actions";

const initialState = {
    user: {
        name: null,
        lastName: null
    }
}

export default function(state = initialState, action) {
    switch(action.type) {
        case USER_LOGIN:
            return {
                ...state,
                user: action.user
            }
        default:
            return state;
    }
}