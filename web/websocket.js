window.onload = init;
var socket = new WebSocket("ws://localhost:8080/blackjack_war_exploded/actions");
socket.onmessage = onMessage;

var playerName;




function onMessage(event) {
    var messFrom = JSON.parse(event.data);
    if (messFrom.action === "correctName") {
        correctName(messFrom);
    }
    if (messFrom.action === "correctName2") {
        correctName2(messFrom);
    }
    if (messFrom.action === "wrongName") {
        wrongName(messFrom);
    }
    if (messFrom.action === "wrongName2") {
        wrongName2(messFrom);
    }
    if (messFrom.action === "correctTable") {
        correctTable();
    }
    if (messFrom.action === "wrongTable") {
        wrongTable();
    }
    if (messFrom.action === "newMessage") {
        newMessage(messFrom);
    }
    if (messFrom.action === "newCard") {
        newCard(messFrom);
    }
    if (messFrom.action === "gameResult") {
        gameResult(messFrom);
    }
    if (messFrom.action === "newGameStart") {
        newGameStart();
    }
    if (messFrom.action === "rankingRefreshed") {
        rankingRefreshed(messFrom);
    }
    if (messFrom.action === "opponentLost") {
        opponentLost();
    }
    if (messFrom.action === "correctPassword2") {
        correctPassword2();
    }
    if (messFrom.action === "wrongPassword2") {
        wrongPassword2()
    }
}




function register() {
    hideElement(document.getElementById("initialPage"));
    unhideElement(document.getElementById("regPage"));
}

function log() {
    hideElement(document.getElementById("initialPage"));
    unhideElement(document.getElementById("logPage"));
}

function sendName() {
    var messTo = {
        action: "sendName",
        name: document.getElementById("namePassword").value
    };
    socket.send(JSON.stringify(messTo));
}

function sendName2() {
    var messTo = {
        action: "sendName2",
        name: document.getElementById("namePassword2").value
    };
    socket.send(JSON.stringify(messTo));
}

function correctName(messFrom) {
    var x = document.getElementById("typeWhat");
    playerName = messFrom.name;
    clearElement(x);
    x.innerHTML = "Hello " + playerName + "!!! Type your password";
    hideElement(document.getElementById("nameButton"));
    unhideElement(document.getElementById("passwordButton"));
}

function correctName2(messFrom) {
    var x = document.getElementById("typeWhat2");
    playerName = messFrom.name;
    clearElement(x);
    x.innerHTML = "Hello " + playerName + "!!! Type your password";
    hideElement(document.getElementById("nameButton2"));
    unhideElement(document.getElementById("passwordButton2"));
}

function wrongName(messFrom) {
    var x = document.getElementById("typeWhat");
    var y = messFrom.name;
    clearElement(x);
    x.innerHTML = "Name " + y + " is wrong!!! Type another name";
}

function wrongName2(messFrom) {
    var x = document.getElementById("typeWhat2");
    var y = messFrom.name;
    clearElement(x);
    x.innerHTML = "Name " + y + " is wrong!!! Type another name";
}

function sendPassword() {
    hideElement(document.getElementById("regPage"));
    hideElement(document.getElementById("logPage"));
    unhideElement(document.getElementById("tablesPage"));
    var messTo = {
        action: "sendPassword",
        name: playerName,
        instruction: document.getElementById("namePassword").value
    };
    socket.send(JSON.stringify(messTo));
}

function sendPassword2() {
    var messTo = {
        action: "sendPassword2",
        name: playerName,
        instruction: document.getElementById("namePassword2").value
    };
    socket.send(JSON.stringify(messTo));
}

function correctPassword2() {
    hideElement(document.getElementById("regPage"));
    hideElement(document.getElementById("logPage"));
    unhideElement(document.getElementById("tablesPage"));
}

function wrongPassword2() {
    var x = document.getElementById("typeWhat2");
    clearElement(x);
    x.innerHTML = "Password for " + playerName + " is wrong!!! Try again";
}

function table1() {
    var messTo = {
        action: "table",
        name: playerName,
        instruction: "1"
    };
    socket.send(JSON.stringify(messTo));
}

function table2() {
    var messTo = {
        action: "table",
        name: playerName,
        instruction: "2"
    };
    socket.send(JSON.stringify(messTo));
}

function table3() {
    var messTo = {
        action: "table",
        name: playerName,
        instruction: "3"
    };
    socket.send(JSON.stringify(messTo));
}

function table4() {
    var messTo = {
        action: "table",
        name: playerName,
        instruction: "4"
    };
    socket.send(JSON.stringify(messTo));
}

function table5() {
    var messTo = {
        action: "table",
        name: playerName,
        instruction: "5"
    };
    socket.send(JSON.stringify(messTo));
}

function correctTable() {
    hideElement(document.getElementById("tablesPage"));
    unhideElement(document.getElementById("mainPage"));
}

function wrongTable() {
    var x = document.getElementById("chooseTable");
    x.innerHTML = "This table is occupied... Try to choose another one";
}

function refreshRanking() {
    var messTo = {
        action: "refreshRanking",
        name: playerName
    };
    socket.send(JSON.stringify(messTo));
}

function rankingRefreshed(messFrom) {
    var x = document.getElementById("ranking");
    clearElement(x);
    x.innerHTML = messFrom.instruction;
}

function sendMessage() {
    var messTo = {
        action: "sendMessage",
        name: playerName,
        instruction: document.getElementById("sms").value
    };
    socket.send(JSON.stringify(messTo));
}

function newMessage(messFrom) {
    var x = document.getElementById("chat");
    x.insertAdjacentHTML( 'afterBegin', messFrom.instruction+'<br>' );
}

function newGame() {
    var x = document.getElementById("result");
    clearElement(x);
    x.innerHTML = "Waiting for Opponent...";
    hideElement(document.getElementById("newButton"));
    var messTo = {
        action: "newGame",
        name: playerName
    };
    socket.send(JSON.stringify(messTo));
}

function newGameStart() {
    var x = document.getElementById("result");
    clearElement(x);
    x.innerHTML = "Playing...<br><br>Your cards: ";
    unhideElement(document.getElementById("addButton"));
    unhideElement(document.getElementById("passButton"));
}

function addCard() {
    var messTo = {
        action: "addCard",
        name: playerName
    };
    socket.send(JSON.stringify(messTo));
}

function newCard(messFrom) {
    var x = document.getElementById("result");
    x.insertAdjacentHTML( 'beforeEnd', messFrom.instruction+' ' );
}

function pass() {
    var x = document.getElementById("result");
    clearElement(x);
    x.innerHTML = "Waiting for Opponent...";
    hideElement(document.getElementById("addButton"));
    hideElement(document.getElementById("passButton"));
    var messTo = {
        action: "pass",
        name: playerName
    };
    socket.send(JSON.stringify(messTo));
}

function gameResult(messFrom) {
    var x = document.getElementById("result");
    clearElement(x);
    //x.innerHTML = "You WIN!!!<br><br>Your points: <br><br>Opponent's points: ";
    x.innerHTML = messFrom.instruction;
    unhideElement(document.getElementById("newButton"));
}

function opponentLost() {
    var x = document.getElementById("chooseTable");
    clearElement(x);
    x.innerHTML = "Opponent lost... Choose a table again for playing";
    hideElement(document.getElementById("mainPage"));
    unhideElement(document.getElementById("tablesPage"));
    hideElement(document.getElementById("addButton"));
    hideElement(document.getElementById("passButton"));
    unhideElement(document.getElementById("newButton"));
    var a = document.getElementById("ranking");
    clearElement(a);
    a.innerHTML = "Here you can check ranking";
    var b = document.getElementById("result");
    clearElement(b);
    b.innerHTML = "Here you can play!!!";
    var c = document.getElementById("chat");
    clearElement(c);
    c.innerHTML = "Here you can text opponent";
}

function clearElement(element) {
    var x = document.getElementById(element.id);
    x.innerHTML = "";
}

function hideElement(element) {
    var x = document.getElementById(element.id);
    x.style.display = "none";
}

function unhideElement(element) {
    var x = document.getElementById(element.id);
    x.style.display = "";
}

function init() {
    hideElement(document.getElementById("passwordButton"));
    hideElement(document.getElementById("regPage"));
    hideElement(document.getElementById("passwordButton2"));
    hideElement(document.getElementById("logPage"));
    hideElement(document.getElementById("addButton"));
    hideElement(document.getElementById("passButton"));
    hideElement(document.getElementById("tablesPage"));
    hideElement(document.getElementById("mainPage"));
}


