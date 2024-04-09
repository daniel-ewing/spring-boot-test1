var ArrayList = Java.type('java.util.ArrayList');

var users = execution.getVariable("users");
var initialUsersLength = users.length;
var lastUser = users.pop();
var finalUsersLength = users.length;

execution.setVariable("initialUsersLength", initialUsersLength);
execution.setVariable("lastUser", lastUser);
execution.setVariable("finalUsersLength", finalUsersLength);
