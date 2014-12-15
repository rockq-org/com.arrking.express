#!/usr/bin/env node
var path = require("path");
    fs = require ('fs');
    ncp = require('ncp').ncp;

ncp.limit = 16;

var _NMR = process.env.NODE_PATH;
if (!_NMR) {
  console.log("To copy icons during prepare, need to set the NODE_PATH environment variable to point to the system's node_modules path");
  return;
}

var cordova_util = require('cordova-lib/src/cordova/util');
var projRoot = cordova_util.isCordova(process.cwd());
var projXml = cordova_util.projectConfig(projRoot);
var ConfigParser = require('cordova-lib/src/ConfigParser/ConfigParser');
var projConfig = new ConfigParser(projXml);
var projName = projConfig.name();

var fileset = {
  android: {
			"JPushPlugin.java":  "src/cn/jpush/phonegap/JPushPlugin.java",
			"Tory.java":  "src/com/arrking/tory/Tory.java",
            "AndroidManifest.xml": ""
	}
};

for (var platform in fileset) {
    var files = fileset[platform];

    var srcPath = 'extras/jpush/android/';
    var dstPath = 'platforms/android/';

    Object.keys(files).forEach(function(key) {
        var val = files[key];
        var src = path.join(srcPath, key);
        var dest = path.join(dstPath, val);

        fs.exists(dest, function(exists) {
            if (exists) {
                ncp(src, dest, function (err) {
                    if (err) {
                        return console.error(err);
                    } else {
                        return console.log("Copied " + src + " to " + dest);
                    }
                });
            }
        });
    });
};
