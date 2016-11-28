
'use strict';

angular.module("App").service("UserSessionInterceptor", ['$injector', '$rootScope', function($injector, $rootScope) {
	return {
		 'request': function(config) {
			var UserService = $injector.get("UserService");
			return UserService.getUserAuthenticated().then(
				function(response) {
					$rootScope.currentUser = response;
				}
			);
			return config;
		},
		'responseError': function(response) {
	        return response;
	    }
	}
}]);