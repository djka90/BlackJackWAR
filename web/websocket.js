window.onload = init;
var socket = new WebSocket("ws://localhost:8080/blackjack_war_exploded/actions");
socket.onmessage = onMessage;

var playerName;




function onMessage(event) {
    var device = JSON.parse(event.data);
    if (device.action === "correctName") {
        correctName(device);
    }
    if (device.action === "wrongName") {
        wrongName(device);
    }
    if (device.action === "correctTable") {
        correctTable();
    }
    if (device.action === "wrongTable") {
        wrongTable();
    }
    if (device.action === "remove") {
        document.getElementById(device.id).remove();
        //device.parentNode.removeChild(device);
    }
    if (device.action === "toggle") {
        var node = document.getElementById(device.id);
        var statusText = node.children[2];
        if (device.status === "On") {
            statusText.innerHTML = "Status: " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn off</a>)";
        } else if (device.status === "Off") {
            statusText.innerHTML = "Status: " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn on</a>)";
        }
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
    var x = document.getElementById("typeWhat");
    var y = document.getElementById("namePassword").value;
    playerName = y;
    clearElement(x);
    x.innerHTML = "Hello " + y + "!!! Type your password";
    hideElement(document.getElementById("nameButton"));
    unhideElement(document.getElementById("passwordButton"));
}

function sendName2() {
    var DeviceAction = {
        action: "sendName2",
        name: document.getElementById("namePassword2").value
    };
    socket.send(JSON.stringify(DeviceAction));
}

function correctName(device) {
    var x = document.getElementById("typeWhat2");
    playerName = device.name;
    clearElement(x);
    x.innerHTML = "Hello " + playerName + "!!! Type your password";
    hideElement(document.getElementById("nameButton2"));
    unhideElement(document.getElementById("passwordButton2"));
}

function wrongName(device) {
    var x = document.getElementById("typeWhat2");
    var y = device.name;
    clearElement(x);
    x.innerHTML = "Name " + y + " is wrong!!! Type another name";
}

function sendPassword() {
    var x = document.getElementById("namePassword").value;
    hideElement(document.getElementById("regPage"));
    hideElement(document.getElementById("logPage"));
    unhideElement(document.getElementById("tablesPage"));
}

function sendPassword2() {
    var x = document.getElementById("namePassword2").value;
    hideElement(document.getElementById("regPage"));
    hideElement(document.getElementById("logPage"));
    unhideElement(document.getElementById("tablesPage"));
}

function table1() {
    var DeviceAction = {
        action: "table1",
        name: playerName
    };
    socket.send(JSON.stringify(DeviceAction));
}

function table5() {
    var DeviceAction = {
        action: "table5",
        name: playerName
    };
    socket.send(JSON.stringify(DeviceAction));
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
    var x = document.getElementById("ranking");
    clearElement(x);
    x.innerHTML = "1. ddd 40<br>2. eee 30<br>3. fff 20<br>3. ggg 20<br>3. hhh 20<br>3. ccc 20<br>3. ccc 20<br>3. ccc 20<br>3. ccc 20<br>3. ccc 20<br>3. ccc 20<br>3. ccc 20<br>3. ccc 20<br>3. ccc 20";
}

function sendMessage() {
    var x = document.getElementById("chat");
    var y = document.getElementById("sms").value;
    x.insertAdjacentHTML( 'afterBegin', y+'<br>' );
}

function newGame() {
    var x = document.getElementById("result");
    clearElement(x);
    x.innerHTML = "Playing...<br><br>Your cards: ";
    hideElement(document.getElementById("newButton"));
    unhideElement(document.getElementById("addButton"));
    unhideElement(document.getElementById("passButton"));
}

function addCard() {
    var x = document.getElementById("result");
    x.insertAdjacentHTML( 'beforeEnd', 'AS ' );
}

function pass() {
    var x = document.getElementById("result");
    clearElement(x);
    x.innerHTML = "You WIN!!!<br><br>Your points: <br><br>Opponent's points: ";
    hideElement(document.getElementById("addButton"));
    hideElement(document.getElementById("passButton"));
    unhideElement(document.getElementById("newButton"));
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


/*function onMessage(event) {
    var device = JSON.parse(event.data);
    if (device.action === "add") {
        printDeviceElement(device);
    }
    if (device.action === "remove") {
        document.getElementById(device.id).remove();
        //device.parentNode.removeChild(device);
    }
    if (device.action === "toggle") {
        var node = document.getElementById(device.id);
        var statusText = node.children[2];
        if (device.status === "On") {
            statusText.innerHTML = "Status: " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn off</a>)";
        } else if (device.status === "Off") {
            statusText.innerHTML = "Status: " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn on</a>)";
        }
    }
}

function addDevice(name, type, description) {
    var DeviceAction = {
        action: "add",
        name: name,
        type: type,
        description: description
    };
    socket.send(JSON.stringify(DeviceAction));
}

function removeDevice(element) {
    var id = element;
    var DeviceAction = {
        action: "remove",
        id: id
    };
    socket.send(JSON.stringify(DeviceAction));
}

function toggleDevice(element) {
    var id = element;
    var DeviceAction = {
        action: "toggle",
        id: id
    };
    socket.send(JSON.stringify(DeviceAction));
}

function printDeviceElement(device) {
    var content = document.getElementById("content");

    var deviceDiv = document.createElement("div");
    deviceDiv.setAttribute("id", device.id);
    deviceDiv.setAttribute("class", "device " + device.type);
    content.appendChild(deviceDiv);

    var deviceName = document.createElement("span");
    deviceName.setAttribute("class", "deviceName");
    deviceName.innerHTML = device.name;
    deviceDiv.appendChild(deviceName);

    var deviceType = document.createElement("span");
    deviceType.innerHTML = "<b>Type:</b> " + device.type;
    deviceDiv.appendChild(deviceType);

    var deviceStatus = document.createElement("span");
    if (device.status === "On") {
        deviceStatus.innerHTML = "<b>Status:</b> " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn off</a>)";
    } else if (device.status === "Off") {
        deviceStatus.innerHTML = "<b>Status:</b> " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn on</a>)";
        //deviceDiv.setAttribute("class", "device off");
    }
    deviceDiv.appendChild(deviceStatus);

    var deviceDescription = document.createElement("span");
    deviceDescription.innerHTML = "<b>Comments:</b> " + device.description;
    deviceDiv.appendChild(deviceDescription);

    var removeDevice = document.createElement("span");
    removeDevice.setAttribute("class", "removeDevice");
    removeDevice.innerHTML = "<a href=\"#\" OnClick=removeDevice(" + device.id + ")>Remove device</a>";
    deviceDiv.appendChild(removeDevice);
}

function showForm() {
    document.getElementById("addDeviceForm").style.display = '';
}

function hideForm() {
    document.getElementById("addDeviceForm").style.display = "none";
}

function formSubmit() {
    var form = document.getElementById("addDeviceForm");
    var name = form.elements["device_name"].value;
    var type = form.elements["device_type"].value;
    var description = form.elements["device_description"].value;
    hideForm();
    document.getElementById("addDeviceForm").reset();
    addDevice(name, type, description);
}

function init() {
    hideForm();
}*/