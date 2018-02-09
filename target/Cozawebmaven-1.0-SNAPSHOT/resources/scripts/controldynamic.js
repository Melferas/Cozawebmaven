/* 
 Created on : 15-ene-2018, 18:16:26
 Author     : Julio Sorroche García
 */
$(document).ready(function () {

    AWS.config.region = 'eu-west-1'; // Region
    AWS.config.credentials = new AWS.CognitoIdentityCredentials({
        IdentityPoolId: 'eu-west-1:e66e0776-295c-4481-810b-92d2c1a3884d' //,
                // RoleArn: 'arn:aws:iam::889222416887:role/Cognito_IoTPruebaUnauth_Role',
                // AccountId: '889222416887' // your AWS account ID
    });

    AWS.config.credentials.get(function (err) {
        if (err) {
            console.warn("Get credential failed", err);
            return;
        }

        var onOpen = function () {
            client.subscribe(`$aws/things/Cozita1/shadow/update/documents`);
            // <Optional> Get actively current status (on startup and interval)
            client.subscribe(`$aws/things/Cozita1/shadow/get/accepted`, {onSuccess: function () {
                    var ping_for_get = new Paho.MQTT.Message("");
                    ping_for_get.destinationName = `$aws/things/Cozita1/shadow/get`;
                    var active_sync = function () {
                        client.send(ping_for_get);
                        setTimeout(active_sync, 60000);
                    };
                    active_sync();
                }});
        };

        var onMessage = function (message) {
            var obj = JSON.parse(message.payloadString);
            var state = ('current' in obj) ? obj.current : obj; // for normalize. NOTE: When receiving /update/documents there is a 'current' in JSON / ref: http://docs.aws.amazon.com/ja_jp/iot/latest/developerguide/thing-shadow-mqtt.html#update-documents-pub-sub-topic
            console.log(message.destinationName, state);
        };

        var onClose = function (e) {
            console.log(e);
        };

        var endpoint = SigV4Utils.getSignedUrl("wss", `data.iot.${AWS.config.region}.amazonaws.com`, "/mqtt", "iotdevicegateway", AWS.config.region, AWS.config.credentials.accessKeyId, AWS.config.credentials.secretAccessKey, AWS.config.credentials.sessionToken);
        var clientId = Math.random().toString(36).substring(7);
        client = new Paho.MQTT.Client(endpoint, clientId);
        client.connect({useSSL: true, timeout: 3, mqttVersion: 4, onSuccess: onOpen, onFailure: onClose});
        client.onMessageArrived = onMessage;
        client.onConnectionLost = onClose;
    });

    $.post('siteController.jsp', {
        temp: ""
    }, function (responseText) {
        $('#currentTemp').text(responseText);
    });
    $.post('siteController.jsp', {
        light: ""
    }, function (responseText) {
        $('#currentLight').text(responseText);
    });
    $.post('siteController.jsp', {
        pres: ""
    }, function (responseText) {
        $('#currentPres').text(responseText);
    });
    $('#controlTempplus').click(function ()
    {
        $.post('siteController.jsp', {
            temp: "subir"
        }, function (responseText) {
            $('#currentTemp').text(responseText);
        });
    });
    $('#controlTempless').click(function ()
    {
        $.post('siteController.jsp', {
            temp: "bajar"
        }, function (responseText) {
            $('#currentTemp').text(responseText);
        });
    });
    $('#controlLuzplus').click(function ()
    {
        $.post('siteController.jsp', {
            light: "subir"
        }, function (responseText) {
            $('#currentLight').text(responseText);
        });
    });
    $('#controlLuzless').click(function ()
    {
        $.post('siteController.jsp', {
            light: "bajar"
        }, function (responseText) {
            $('#currentLight').text(responseText);
        });
    });
    $('#controlPresChange').click(function ()
    {
        $.post('siteController.jsp', {
            pres: "cambiar"
        }, function (responseText) {
            $('#currentPres').text(responseText);
        });
    });
}
);
