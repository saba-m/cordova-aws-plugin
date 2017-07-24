var exec = require('cordova/exec');

exports.echo = function(arg0,arg1,arg2,arg3, success, error) {
    exec(success, error, "BabyscriptsAWS", "echo", [arg0,arg1,arg2,arg3]);
};
