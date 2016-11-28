'use strict';

angular.module('App').controller(
		'UserController', ['$scope', '$location', '$rootScope', 'UserService', 
			function($scope, $location, $rootScope, UserService) {
	
    var self = this;
    
    initialize();
    
    self.login = login;
    
    function login() {
    	UserService.login(self.user)
	        .then(
	        function(response) {
                var message = "Login successfully";
                $rootScope.successAlert(message);
                $rootScope.currentUser = response;
                $location.path('/');
	        },
	        function(errResponse){
                var message = "Login failed";
                $rootScope.errorAlert(message);
	        }
	    );
    }

    function submit() {
    	if (self.model.id === null){
            create(self.model);
        }
        else{
            update(self.model, self.model.id);
        }
        reset();
    }
    
    function initialize() {
        self.user = {
        		username: null,
        		password: null
        };
    }

    function reset(){
    	initialize();
        $scope.form.$setPristine();
    }

}]);
