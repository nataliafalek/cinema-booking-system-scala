export const USER_LOGIN = "USER_LOGIN";

export function loginUser(user) {
    return {
        type: USER_LOGIN,
        user: user
    }
}