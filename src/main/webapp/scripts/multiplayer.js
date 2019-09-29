let sendingNumber = {
    init() {
        this.re = /^(?:([0-9])(?!.*\1)){4}$/;
        this.userNumb = "";
        this.enter = document.querySelector(".enter");
        this.enterField = document.querySelector(".enterUserNumber");
        this.enterText = this.enterField.querySelector("input");

        this.sendBox = document.querySelector(".sendBox");
        this.textArea = this.sendBox.querySelector("input");
        this.userMove = document.querySelector(".move");
        this.userMove.disabled = true;
        this.name = document.querySelector(".sendBox").id;
        this.otherName = "";
        this.newGame = this.newGame = document.querySelector(".newGame");
        if (this.name === document.querySelector(".firstUserScore").id) {
            this.otherName = document.querySelector(".secondUserScore").id;
        } else {
            this.otherName = document.querySelector(".firstUserScore").id;
        }
        this.scoreOne = document.querySelector(".score1");
        this.scoreTwo = document.querySelector(".score2");
        this.setUserNumber();
        this.makeAMove();
        this.startNewGame();
    },
    startNewGame() {
        this.newGame.addEventListener('click', e => {
            this.newGame.style.display = "none";
            e.preventDefault();
            window.location.reload();
        });
    },
    setUserNumber() {
        this.openGameSocket();
            this.enter.addEventListener('click', e => {
                let OK = this.re.exec(this.enterText.value);
                if(OK) {
                    this.enterField.style.display = "none";
                    e.preventDefault();
                    this.userNumb = this.enterText.value;
                    this.sendNumb(this.userNumb);
                }else{
                    e.preventDefault();
                    alert("The move consists of 4 non-repeating numbers!")
                }
            });
        }
    ,
    makeAMove() {
        this.userMove.addEventListener('click', e => {
            let OK = this.re.exec(this.textArea.value);
            if(OK) {
                e.preventDefault();
                this.sendNumb();
            }else{
                e.preventDefault();
                alert("The move consists of 4 non-repeating numbers!")
            }
        });
    },
    sendNumb(e) {
        if (e) {
            this.sendMsg({
                name: this.name,
                text: e
            })
        } else {
            this.userMove.disabled = true;
            this.sendMsg({
                    name: this.name,
                    text: this.textArea.value,
                }
            )
        }
    },
    sendMsg(msg) {
        this.textArea.value = "";
        this.ws.send(JSON.stringify(msg));
    }
    ,
    onMessage(msg) {
        if (msg.text === "checked" && msg.name !== this.name) {
            this.userMove.disabled = false;
        } else {
            if(msg.text==="checked") return;
            let msgBlock = document.createElement("div");
            msgBlock.className = "msg";
            msgBlock.innerText = msg.userNumb + " " + msg.text;
            if (msg.name !== document.querySelector(".secondUserScore").id) {
                this.scoreOne.appendChild(msgBlock);
            } else {
                this.scoreTwo.appendChild(msgBlock);
            }
            if (msg.name === this.otherName) {
                this.userMove.disabled = false;
            }
            if (msg.text === "Bulls: 4 / Cows: 0") {
                this.userMove.disabled = true;
                let winnerMessage = document.createElement("div");
                winnerMessage.innerText = msg.name + "WIN!";
                this.userWin = document.querySelector(".userScore");
                this.userWin.appendChild(winnerMessage);
                this.newGame.style.display = "block";
            }
        }
    }

    ,
    onOpenSock() {

    }
    ,
    onClose() {
        this.ws.close();
    }
    ,
    openGameSocket() {
        this.ws = new WebSocket("ws://localhost:8080/java-game-in-browser/multiplayerGame/" + this.otherName);
        this.ws.onopen = () => this.onOpenSock();
        this.ws.onmessage = (e) => this.onMessage(JSON.parse(e.data));
        this.ws.onclose = () => this.onClose();
    }
};

window.addEventListener("load", e => sendingNumber.init());