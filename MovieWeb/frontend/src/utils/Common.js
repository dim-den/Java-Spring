export const getEmail = () => {
    const userStr = sessionStorage.getItem('email');
    if (userStr) return JSON.parse(userStr);
    else return null;
}

// return the token from the session storage
export const getToken = () => {
    return sessionStorage.getItem('token') || null;
}

export const getRole = () => {
    const roleStr = sessionStorage.getItem('role');
    if (roleStr) return JSON.parse(roleStr);
    else return null;
}

// remove the token and user from the session storage
export const removeUserSession = () => {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('email');
    sessionStorage.removeItem('role');
}

// set the token and user from the session storage
export const setUserSession = (token, email, role) => {
    sessionStorage.setItem('token', token);
    sessionStorage.setItem('email', JSON.stringify(email));
    sessionStorage.setItem('role', JSON.stringify(role));
}

export const userAuthrorized = () => {
    return getToken() !== null;
}

export const haveAccess = required_role => {
    let user_role = getRole();

    if(user_role === null) return false;
    else if(user_role === required_role) return true;
    else if(user_role === "ADMIN") return true;
    else return false;
}

export const makeTokenizedRequest = (path, method = 'GET', body = null) => {
    return fetch(path, {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': getToken()
        },
        body: body
    });
}