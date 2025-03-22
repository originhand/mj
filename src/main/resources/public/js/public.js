function open(msg) {
    /* let msgBoxEL = document.getElementById("msg_box");
     document.getElementById("msg").innerText=msg;
     msgBoxEL.display="block";
     setTimeout(()=>{
         msgBoxEL.display='none';
     },3000)*/
    let msgBoxEL = $("#msg_box");
    $("#msg").text(msg);
    msgBoxEL.show();
    setTimeout(() => {
        msgBoxEL.hide();
    }, 3000)
}