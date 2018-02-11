/* 
 Created on : 15-ene-2018, 18:16:26
 Author     : Julio Sorroche García
 */
$(document).ready(function () {
    var socket = new WebSocket("ws://localhost:8080/Cozawebmaven/actions");
    socket.onmessage = onMessage;
    var lighttext;
    var temptext;
    function onMessage(event) {
        var device = JSON.parse(event.data);
        if (device.action === "show") {
            $("#currentTemp").text(device.temp);
            $("#currentLight").text(device.light + "%");
            $("#currentPres").text(device.pres);
            $("#currentMode").text(device.mode);
            $("#currentTime").text(device.prest);
        }
    }

    $('#controlTempplus').click(function ()
    {
        var enviar = parseInt($("#currentTemp").text());

        if (isNaN(enviar) || enviar > 99)
        {
            alert("Límite alcanzado");
        } else
        {
            var data = {
                action: "tempup"
            };
            socket.send(JSON.stringify(data));

        }
    });

    $('#controlTempless').click(function ()
    {
        var enviar = parseInt($("#currentTemp").text());

        if (isNaN(enviar) || enviar <= 0)
        {
            alert("Límite alcanzado");
        } else
        {
            var data = {
                action: "tempdown"
            };
            socket.send(JSON.stringify(data));
        }
    });

    $('#controlLuzplus').click(function ()
    {
        var enviar = parseInt($("#currentLight").text());

        if (isNaN(enviar) || enviar > 99)
        {
            alert("Límite alcanzado");
        } else
        {
            var data = {
                action: "lightup"
            };
            socket.send(JSON.stringify(data));
        }
    });
    $('#controlLuzless').click(function ()
    {
        var enviar = parseInt($("#currentLight").text());

        if (isNaN(enviar) || enviar <= 0)
        {
            alert("Límite alcanzado");
        } else
        {
            var data = {
                action: "lightdown"
            };
            socket.send(JSON.stringify(data));
        }
    });

    $('#controlPresChange').click(function ()
    {

        var data = {
            action: "preschange"
        };
        socket.send(JSON.stringify(data));

    });

    var prestime = 0;
    $("#currentTime").focus(function () {
        prestime = $(this).text();
    });

    $('#currentTime').blur(function ()
    {
        var enviar = parseInt($(this).text());
        if (isNaN(enviar) || enviar > 999999999999999999 || enviar < 0)
        {
            alert("Nivel de tiempo inadecuado");

            $(this).text(prestime);
        } else
        {
            var data = {
                action: "prestext",
                prestime: enviar
            };
            socket.send(JSON.stringify(data));
        }
    });


    $("#currentTemp").focus(function () {
        temptext = $(this).text();
    });

    $('#currentTemp').blur(function ()
    {
        var enviar = parseInt($(this).text());
        if (isNaN(enviar) || enviar > 100 || enviar < 0)
        {
            alert("Nivel de temperatura inadecuado");

            $(this).text(temptext);
        } else
        {
            var data = {
                action: "temptext",
                tempvalue: enviar
            };
            socket.send(JSON.stringify(data));
        }
    });




    $("#currentLight").focus(function () {
        lighttext = $(this).text();
    });

    $('#currentLight').blur(function ()
    {
        var enviar = parseInt($(this).text());
        if (isNaN(enviar) || enviar > 100 || enviar < 0)
        {
            alert("Nivel de luz inadecuado");

            $(this).text(lighttext);
        } else
        {
            var data = {
                action: "lightext",
                lightvalue: enviar
            };
            socket.send(JSON.stringify(data));
            $(this).text(enviar + "%");
        }

    });

}
);
