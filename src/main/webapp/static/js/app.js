

let App = angular.module("App", [
	"ngFlash",
	"ngRoute",
	"720kb.tooltips",
	"ui.bootstrap",
	"wt.responsive"
])
.config(["$httpProvider", function ($httpProvider) {

    $httpProvider.defaults.timeout = 5000;
    $httpProvider.interceptors.push("APIAuthInterceptor");
//    $httpProvider.interceptors.push("UserSessionInterceptor");

}])
.config(["$routeProvider", function ($routeProvider) {

	  $routeProvider
	  	.when("/user/login", {
	  		"templateUrl": "user/login",
	  		"controller": "UserController",
	  		"controllerAs": "ctrl"
	  	})
	  	.when("/admin/user", {
	  		"templateUrl": "admin/user/index",
	  		"controller": "UserAPIController",
	  		"controllerAs": "ctrl"
	  	})
	    .when("/admin/article", {
	    	"templateUrl": "admin/article/index",
	  		"controller": "ArticleAPIController",
	  		"controllerAs": "ctrl"
	  	})
	    .when("/admin/role", {
	    	"templateUrl": "admin/role/index",
	  		"controller": "RoleAPIController",
	  		"controllerAs": "ctrl"
	  	})
	    .otherwise({
	    	"redirectTo": "/"
	    });

}])
.constant("APP_CONSTANTS", {
	"baseUrl": "http://localhost:" + "8080" + "/" + "first-spring-project",
	"flashMessage": {
		"timeout": 3000
	}
})
.constant("API_CONSTANTS", {
	  "restServiceURI": "http://localhost:"
		+ "8080"
		+ "/"
		+ "first-spring-project"
		+ "/api/"
		+ "v1"
		+ "/",
	  "messages": {
	  		"fetchAll": "Fetching all",
	  		"create": "Not created",
	  		"update": "Not updated",
	  		"permDelete": "Not permanent deleted",
	  		"softDelete": "Not soft deleted",
	  		"softReactive": "Not soft reactivated"
	  }
})
.controller("AppController",
		["$scope", "$rootScope", "$location", "UserService", "Flash", "APP_CONSTANTS",
			function ($scope, $rootScope, $location, UserService, Flash, APP_CONSTANTS) {

	let self = this;

    $rootScope.successAlert = function (message) {

        let id = Flash.create("success", message, APP_CONSTANTS.flashMessage.timeout, {"class": "custom-class", "id": "custom-id"}, true);

};

    $rootScope.errorAlert = function (message) {

        let id = Flash.create("danger", message, APP_CONSTANTS.flashMessage.timeout, {"class": "custom-class", "id": "custom-id"}, true);

};

	self.getClass = function getClass (path) {

		let location = $location.path().substr(0, path.length);
		return location === path ? "active" : "";

	};

	self.logout = function logout () {

    	UserService.logout()
	        .then(
	        function () {

                $rootScope.currentUser = null;
                let message = "Logout successfully";
                $rootScope.successAlert(message);

			},
	        function () {

                let message = "Logout failed";
                $rootScope.errorAlert(message);

			}
	    );

	};

}]);

