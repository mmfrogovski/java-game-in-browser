let chatUnit = {
    init() {
        this.chatbox = document.querySelector(".chatbox");

        this.readyBtn = document.querySelector(".ready");
        this.gameId = this.readyBtn.id;

        this.msgTextArea = this.chatbox.querySelector("input");

        this.chatMessageContainer = this.chatbox.querySelector(".messages");
        this.myroomId = this.chatMessageContainer.id;
        this.bindEvent();
        this.readyBtn.addEventListener('click', e => {
            this.setReady(this.name);
            this.readyBtn.style.display = "none";
            e.preventDefault();
            this.send("ready");
        });

    },
    setReady(name) {
        let tds = document.getElementsByTagName("td");

        for (let i = 0; i < tds.length; i++) {
            if (tds[i].id === name) {
                tds[i].innerHTML = "ready";
            }
        }
        this.readyCheck();
    }
    ,
    readyCheck() {
        let tds = document.getElementsByTagName("td");
        let g = 0;
        for (let i = 0; i < tds.length; i++) {
            if (tds[i].innerHTML === "ready") {
                g++;
            }
        }
        if (1 < g) {
            window.location.replace("http://localhost:8080/java-game-in-browser/multiplayerGame/" + this.gameId)
        }

    }
    ,
    bindEvent() {
        this.openSocket();
        this.msgTextArea.addEventListener("keyup", e => {
            if (e.keyCode === 13) {
                e.preventDefault();
                this.send("");
            }
        });

    },
    send(e) {
        if (e === "ready") {
            this.sendMessage({
                name: this.name,
                text: "-ready",
                roomId: this.myroomId
            });
        } else {
            this.sendMessage({
                name: this.name,
                text: this.msgTextArea.value,
                roomId: this.myroomId
            });
        }
    },
    onOpenSock() {

    },
    onOtherMessage(msg) {
        let msgBlock = document.createElement("div");
        msgBlock.className = "msg";
        let fromBlock = document.createElement("div");
        fromBlock.className = "from";
        fromBlock.innerText = msg.name;
        let textBlock = document.createElement("div");
        if (msg.text === "-ready") {
            this.setReady(msg.name)
        }
        textBlock.className = "text";
        textBlock.innerText = msg.text;

        if (msg.roomId === this.myroomId) {
            msgBlock.appendChild(fromBlock);
            msgBlock.appendChild(textBlock);
            this.chatMessageContainer.appendChild(msgBlock);
        }

    },
    onMyMessage(msg) {
        let msgBlock = document.createElement("div");
        msgBlock.className = "msg";
        let fromBlock = document.createElement("div");
        fromBlock.className = "from";
        fromBlock.innerText = msg.name;
        let textBlock = document.createElement("div");
        if (msg.text === "-ready") {
            this.setReady(msg.name)
        }
        textBlock.className = "text";
        textBlock.innerText = msg.text;
        msgBlock.appendChild(fromBlock);
        msgBlock.appendChild(textBlock);
        this.chatMessageContainer.appendChild(msgBlock);
    }
    ,
    onClose() {
        this.ws.close();
    },
    sendMessage(msg) {
        this.onMyMessage({name: "I,m", text: msg.text});
        this.msgTextArea.value = "";
        this.ws.send(JSON.stringify(msg));
    },
    openSocket() {
        this.name = this.msgTextArea.name;
        this.ws = new WebSocket("ws://localhost:8080/java-game-in-browser/room/" + this.name + "/" + this.myroomId);
        this.ws.onopen = () => this.onOpenSock();
        this.ws.onmessage = (e) => this.onOtherMessage(JSON.parse(e.data));
        this.ws.onclose = () => this.onClose();
    }
};

window.addEventListener("load", e => chatUnit.init());



