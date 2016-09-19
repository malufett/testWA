var exec = require('cordova/exec');
var Controller = {};

Controller.executeCMD = function (cmd, parameter, success, error) {
    exec(success, error, "WhatsAppExtension", cmd, parameter);
};

module.exports = Controller;