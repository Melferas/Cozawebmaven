/* 
 Created on : 15-ene-2018, 18:16:26
 Author     : Julio Sorroche García
 */
$(document).ready(function () {
    var socket = new WebSocket("ws://localhost:8080/Cozawebmaven/actions");
    socket.onmessage = onMessage;

    function onMessage(event) {
        var device = JSON.parse(event.data);
        if (device.action === "show") {
            $("#currentTemp").text(device.temp);
            $("#currentLight").text(device.light+"%");
            $("#currentPres").text(device.pres);
            $("#currentMode").text(device.mode);
        }
    }
    
    $('#controlTempplus').click(function ()
    {
        var data = {
            action: "tempup"
        };
        socket.send(JSON.stringify(data));

        console.log("Aqui esta");
    });

    $('#controlTempless').click(function ()
    {
        var data = {
            action: "tempdown"
        };
        socket.send(JSON.stringify(data));
    });

    $('#controlLuzplus').click(function ()
    {
        var data = {
            action: "lightup"
        };
        socket.send(JSON.stringify(data));
    });
    $('#controlLuzless').click(function ()
    {
        var data = {
            action: "lightdown"
        };
        socket.send(JSON.stringify(data));
    });

    $('#controlPresChange').click(function ()
    {
        var data = {
            action: "preschange"
        };
        socket.send(JSON.stringify(data));
    });
}
);
