

function getToken(token) {
    alert("token: " + token)
}

function showName(name) {
    alert("Here's the name: " + name);
}

function sendGetRequest(token) {
    var xhr = new XMLHttpRequest();

    xhr.open('GET', 'http://localhost:8081/game/list-ready', false);
    xhr.setRequestHeader('Authorization', token);
    xhr.send();

    if (xhr.status != 200) {
        alert( xhr.status + ': ' + xhr.statusText );
    } else {
        alert( xhr.status + ': ' + xhr.response );
    }
}