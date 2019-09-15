let chatUnit ={
    init(){
        this.chatbox = document.querySelector(".chatbox");

        this.msgTextArea = this.chatbox.querySelector("input");
        this.chatMessageContainer = this.chatbox.querySelector(".messages");
        this.bindEvent();
    },
    bindEvent(){
        this.openSocket();
        this.msgTextArea.addEventListener("keyup", e=>{
            if(e.ctrlKey && e.keyCode === 13){
                e.preventDefault();
                this.send();
            }
        })
    },
    send(){
        this.sendMessage({
            name:this.name,
            text:this.msgTextArea.value
        });
    },
    onOpenSock(){

    },
    onMessage(msg){

        let msgBlock = document.createElement("div");
        msgBlock.className = "msg";
        let fromBlock = document.createElement("div");
        fromBlock.className = "from";
        fromBlock.innerText = msg.name;
        let textBlock = document.createElement("div");
        textBlock.className = "text";
        textBlock.innerText = msg.text;

        msgBlock.appendChild(fromBlock);
        msgBlock.appendChild(textBlock);
        this.chatMessageContainer.appendChild(msgBlock);
    }
    ,
    onClose(){

    },
    sendMessage(msg){
        this.onMessage({name:"I,m", text:msg.text});
        this.msgTextArea.value="";
        this.ws.send(JSON.stringify(msg));
    },
    openSocket(){
        this.name = this.msgTextArea.name;
        this.ws = new WebSocket("ws://localhost:8080/java-game-in-browser/room/" + this.name);
        this.ws.onopen = ()=>this.onOpenSock();
        this.ws.onmessage = (e)=>this.onMessage(JSON.parse(e.data));
        this.ws.onclose = ()=>this.onClose();

    }
};

window.addEventListener("load", e=>chatUnit.init());