var exec = require('cordova/exec');

exports.isDeviceRooted = function(num, message, success, error) {
    exec(success, error, "RootDetection", "isDeviceRooted", [num, message]);
};
