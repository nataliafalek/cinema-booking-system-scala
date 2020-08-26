const url = process.env.REACT_APP_API_URL;

export function postJson(path, body) {
    return (fetch(url + path, {
        method: "POST",
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    }));
}