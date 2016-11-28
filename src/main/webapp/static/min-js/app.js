
'use strict';

var App = angular.module('App', ["ngFlash", "ngRoute", "720kb.tooltips", "ui.bootstrap"])
.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.timeout = 5000;
    $httpProvider.interceptors.push('APIAuthInterceptor');
    $httpProvider.interceptors.push('UserAuthInterceptor');
}])
.config(['$routeProvider', function($routeProvider) {
	  $routeProvider
	  	.when("/user/login", {
	  		templateUrl: "user/login",
	  		controller: "UserController",
	  		controllerAs: "ctrl"
	  	})
	  	.when("/user/logout", {
	  		templateUrl: "user/logout",
	  		controller: "UserController",
	  		controllerAs: "ctrl"
	  	})
	  	.when("/admin/user", {
	  		templateUrl: "admin/user/index",
	  		controller: "UserAPIController",
	  		controllerAs: "ctrl"
	  	})
	    .when("/admin/article", { 
	    	templateUrl: "admin/article/index",
	  		controller: "ArticleAPIController",
	  		controllerAs: "ctrl"
	  	})
	    .when("/admin/role", { 
	    	templateUrl: "admin/role/index",
	  		controller: "RoleAPIController",
	  		controllerAs: "ctrl"
	  	})
	    .otherwise({ 
	    	redirectTo: "/" 
	    });
}])
.controller("AppController", ["$rootScope", "$location", function($rootScope, $location) {
	$rootScope.getClass = function (path) {
		var location = $location.path().substr(0, path.length);
		return (location === path) ? 'active' : '';
	}
}])
.constant('API_CONSTANTS', {
  restServiceURI: 'http://localhost:' + '8080' + '/' + 'first-spring-project' + '/api/' + 'v1' + '/'
});


'use strict';

angular.module('App').controller(
		'UserController', ['$scope', '$location', '$rootScope', 'UserService', 'Flash', 
			function($scope, $location, $rootScope, UserService, Flash) {
	
    var self = this;
    
    initialize();
    
    self.login = login;
    
    $scope.successAlert = function (message) {
        var id = Flash.create('success', message, 5000, {class: 'custom-class', id: 'custom-id'}, true);
    }
    
    $scope.errorAlert = function (message) {
        var id = Flash.create('danger', message, 5000, {class: 'custom-class', id: 'custom-id'}, true);
    }
    
    function login() {
    	UserService.login(self.user)
	        .then(
	        function(response) {
                var message = "Login successfully";
                $scope.successAlert(message);
                $rootScope.currentUser = response;
                $location.path('/');
	        },
	        function(errResponse){
                var message = "Login failed";
                $scope.errorAlert(message);
	        }
	    );
    }
    
    $rootScope.logout = function() {
    	UserService.logout()
	        .then(
	        function(response) {
                var message = "Logout successfully";
                $scope.successAlert(message);
                $rootScope.currentUser = null;
                $location.path('/');
	        },
	        function(errResponse){
                var message = "Logout failed";
                $scope.errorAlert(message);
	        }
	    );
    }
    
    function getUserAuthenticated() {
    	UserService.getUserAuthenticated()
	        .then(
	        function(response) {
	            return response;
	        },
	        function(errResponse){
	            var message = "Fetching authenticated user failed";
	            $scope.errorAlert(message);
	            return response;
	        }
	    );
    }

    function submit() {
    	if (self.model.userId === null){
            create(self.model);
        }
        else{
            update(self.model, self.model.userId);
        }
        reset();
    }

    function edit(id){
        for(var i = 0; i < self.list.length; i++){
            if(self.list[i].userId === id) {
                self.model = angular.copy(self.list[i]);
                break;
            }
        }
    }
    
    function softDeleteLink(id) {
        if (self.model.userId === id) {
            reset();
        }
        softDelete(id);
    }
    
    function softReactiveLink(id) {
        if (self.model.userId === id) {
            reset();
        }
        softReactive(id);
    }

    function permDeleteLink(id){
        if (self.model.userId === id) {
            reset();
        }
        permDelete(id);
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


angular.module("App")
	.factory('APIAuthInterceptor', function() {  
	    return {
	        'request': ["config", function(config) {
	            config.headers = config.headers || {};
	            var encodedString = btoa("gabriele:gabriele");
	            config.headers.Authorization = 'Basic ' + encodedString;
	            return config;
	        }]
	    };
	})
	.factory("UserAuthInterceptor", ['$rootScope', function($rootScope) {
		return {
		    'request': function() {
		    	
		    }
		};
	}]);
'use strict';

angular.module('App').factory('UserService', ['$http', '$q', function($http, $q){
	
	var currentService = "user";
	
	var port = 8080;
    
    var USER_CONTROLLER_URI = 'http://localhost:' + port + '/first-spring-project/' + currentService + '/';

    var factory = {
            login: login,
            logout: logout,
            getUserAuthenticated: getUserAuthenticated
    };

    return factory;
    
    function login(user) {
    	var deferred = $q.defer();
        $http.post(USER_CONTROLLER_URI + 'authenticate/', user)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function logout() {
    	var deferred = $q.defer();
        $http.post(USER_CONTROLLER_URI + 'logout/')
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error in logout ' + currentService);
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function getUserAuthenticated() {
    	var deferred = $q.defer();
        $http.post(USER_CONTROLLER_URI + 'authenticated/')
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);

'use strict';

angular.module('App').controller('ArticleAPIController', ['$scope', 'ArticleAPIService', 'Flash', function($scope, ArticleAPIService, Flash) {
	
    var self = this;
    initialize();
    self.list = [];

    self.submit = submit;
    self.fetchAll = fetchAll;
    self.create = create;
    self.update = update;
    self.edit = edit;
    self.permDeleteLink = permDeleteLink;
    self.softDeleteLink = softDeleteLink;
    self.softReactiveLink = softReactiveLink;
    self.reset = reset;
    
    $scope.successAlert = function (message) {
        var id = Flash.create('success', message, 5000, {class: 'custom-class', id: 'custom-id'}, true);
    }
    
    $scope.errorAlert = function (message) {
        var id = Flash.create('danger', message, 5000, {class: 'custom-class', id: 'custom-id'}, true);
    }

    fetchAll();

    function fetchAll(){
    	ArticleAPIService.fetchAll()
            .then(
            function(d) {
                self.list = d;
            },
            function(errResponse){
            	var message = 'Fetching Articles';
                $scope.errorAlert(message);
            }
        );
    }

    function create(model){
    	ArticleAPIService.create(model)
            .then(
            fetchAll,
            function(errResponse){
                var message = "Article not created";
                $scope.errorAlert(message);
            }
        );
    }

    function update(model, id){
    	ArticleAPIService.update(model, id)
            .then(
            fetchAll,
            function(errResponse){
                var message = "Article not updated";
                $scope.errorAlert(message);
            }
        );
    }

    function permDelete(id){
    	ArticleAPIService.permDelete(id)
            .then(
            fetchAll,
            function(errResponse){
                var message = "Article not deleted";
                $scope.errorAlert(message);
            }
        );
    }

    function softDelete(id){
    	ArticleAPIService.softDelete(id)
            .then(
            fetchAll,
            function(errResponse){
                var message = "Article not soft-deleted";
                $scope.errorAlert(message);
            }
        );
    }
    
    function softReactive(id) {
    	ArticleAPIService.softReactive(id)
	        .then(
	        fetchAll,
	        function(errResponse){
                var message = "Article not reactived";
                $scope.errorAlert(message);
	        }
	    );
    }

    function submit() {
    	if (self.model.articleId === null){
            create(self.model);
        }
        else{
            update(self.model, self.model.articleId);
        }
        reset();
    }

    function edit(id){
        for(var i = 0; i < self.list.length; i++){
            if(self.list[i].articleId === id) {
                self.model = angular.copy(self.list[i]);
                break;
            }
        }
    }
    
    function softDeleteLink(id) {
        if (self.model.articleId === id) {
            reset();
        }
        softDelete(id);
    }
    
    function softReactiveLink(id) {
        if (self.model.articleId === id) {
            reset();
        }
        softReactive(id);
    }

    function permDeleteLink(id){
        if (self.model.articleId === id) {
            reset();
        }
        permDelete(id);
    }
    
    function initialize() {
        self.model = {
        		articleId: null,
        		title: null,
        		subtitle: null,
        		body: null,
        		tags: null,
        		user: {
        			userId: null
        		}
        };
    }


    function reset(){
    	initialize();
        $scope.form.$setPristine();
    }

}]);

'use strict';

angular.module('App').controller('RoleAPIController', ['$scope', 'RoleAPIService', 'Flash', function($scope, RoleAPIService, Flash) {
	
    var self = this;
    initialize();
    self.list = [];

    self.submit = submit;
    self.fetchAll = fetchAll;
    self.create = create;
    self.update = update;
    self.edit = edit;
    self.permDeleteLink = permDeleteLink;
    self.reset = reset;
    
    $scope.successAlert = function (message) {
        var id = Flash.create('success', message, 5000, {class: 'custom-class', id: 'custom-id'}, true);
    }
    
    $scope.errorAlert = function (message) {
        var id = Flash.create('danger', message, 5000, {class: 'custom-class', id: 'custom-id'}, true);
    }

    fetchAll();

    function fetchAll(){
    	RoleAPIService.fetchAll()
            .then(
            function(d) {
                self.list = d;
            },
            function(errResponse){
            	var message = 'Fetching Roles';
                $scope.errorAlert(message);
            }
        );
    }

    function create(model){
    	RoleAPIService.create(model)
            .then(
            fetchAll,
            function(errResponse){
                var message = "Role not created";
                $scope.errorAlert(message);
            }
        );
    }

    function update(model, id){
    	RoleAPIService.update(model, id)
            .then(
            fetchAll,
            function(errResponse){
                var message = "Role not updated";
                $scope.errorAlert(message);
            }
        );
    }

    function permDelete(id){
    	RoleAPIService.permDelete(id)
            .then(
            fetchAll,
            function(errResponse){
                var message = "Role not deleted";
                $scope.errorAlert(message);
            }
        );
    }

    function submit() {
    	if (self.model.roleId === null){
            create(self.model);
        }
        else{
            update(self.model, self.model.roleId);
        }
        reset();
    }

    function edit(id){
        for(var i = 0; i < self.list.length; i++){
            if(self.list[i].roleId === id) {
                self.model = angular.copy(self.list[i]);
                break;
            }
        }
    }

    function permDeleteLink(id){
        if (self.model.roleId === id) {
            reset();
        }
        permDelete(id);
    }
    
    function initialize() {
        self.model = {
        		roleId: null,
        		name: null,
        		isDefault: 0,
        		parentId: null
        };
    }


    function reset(){
    	initialize();
        $scope.form.$setPristine();
    }

}]);

'use strict';

angular.module('App').controller('UserAPIController', ['$scope', 'UserAPIService', 'Flash', function($scope, UserAPIService, Flash) {
	
    var self = this;
    initialize();
    self.list = [];

    self.submit = submit;
    self.fetchAll = fetchAll;
    self.create = create;
    self.update = update;
    self.edit = edit;
    self.permDeleteLink = permDeleteLink;
    self.softDeleteLink = softDeleteLink;
    self.softReactiveLink = softReactiveLink;
    self.reset = reset;
    
    $scope.successAlert = function (message) {
        var id = Flash.create('success', message, 5000, {class: 'custom-class', id: 'custom-id'}, true);
    }
    
    $scope.errorAlert = function (message) {
        var id = Flash.create('danger', message, 5000, {class: 'custom-class', id: 'custom-id'}, true);
    }

    fetchAll();

    function fetchAll(){
        UserAPIService.fetchAll()
            .then(
            function(d) {
                self.list = d;
            },
            function(errResponse) {
            	var message = 'Fetching Users';
                $scope.errorAlert(message);
            }
        );
    }

    function create(model){
        UserAPIService.create(model)
            .then(
            fetchAll,
            function(errResponse){
                var message = "User not created";
                $scope.errorAlert(message);
            }
        );
    }

    function update(model, id){
        UserAPIService.update(model, id)
            .then(
            fetchAll,
            function(errResponse){
                var message = "User not updated";
                $scope.errorAlert(message);
            }
        );
    }

    function permDelete(id){
        UserAPIService.permDelete(id)
            .then(
            fetchAll,
            function(errResponse){
                var message = "User not deleted";
                $scope.errorAlert(message);
            }
        );
    }

    function softDelete(id){
        UserAPIService.softDelete(id)
            .then(
            fetchAll,
            function(errResponse){
                var message = "User not soft-deleted";
                $scope.errorAlert(message);
            }
        );
    }
    
    function softReactive(id) {
    	UserAPIService.softReactive(id)
	        .then(
	        fetchAll,
	        function(errResponse){
                var message = "User not reactived";
                $scope.errorAlert(message);
	        }
	    );
    }

    function submit() {
    	if (self.model.userId === null){
            create(self.model);
        }
        else{
            update(self.model, self.model.userId);
        }
        reset();
    }

    function edit(id){
        for(var i = 0; i < self.list.length; i++){
            if(self.list[i].userId === id) {
                self.model = angular.copy(self.list[i]);
                break;
            }
        }
    }
    
    function removeRole(id) {
        for(var i = 0; i < self.list.length; i++){
            if(self.list[i].roles.roleId === id) {
                self.list[i].roles.splice(id, 1);
                break;
            }
        }
    }
    
    function softDeleteLink(id) {
        if (self.model.userId === id) {
            reset();
        }
        softDelete(id);
    }
    
    function softReactiveLink(id) {
        if (self.model.userId === id) {
            reset();
        }
        softReactive(id);
    }

    function permDeleteLink(id){
        if (self.model.userId === id) {
            reset();
        }
        permDelete(id);
    }
    
    function initialize() {
        self.model = {
        		userId: null,
        		firstName: null,
        		lastName: null,
        		email: null,
        		username: null,
        		password: null,
        		roles: [],
        		articles: []
        };
    }


    function reset(){
    	initialize();
        $scope.form.$setPristine();
    }

}]);

'use strict';

angular.module('App').factory('ArticleAPIService', ['$http', '$q', 'API_CONSTANTS', function($http, $q, API_CONSTANTS){
	
	var currentService = 'article';

    var REST_SERVICE_URI = API_CONSTANTS.restServiceURI + currentService + '/';

    var factory = {
        fetchAll: fetchAll,
        create: create,
        update: update,
        permDelete: permDelete,
        softDelete: softDelete,
        softReactive: softReactive
    };

    return factory;

    function fetchAll() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function create(model) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, model)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


    function update(model, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI + id, model)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function permDelete(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI + id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function softDelete(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI + 'soft/' + id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function softReactive(id) {
    	var deferred = $q.defer();
        $http.put(REST_SERVICE_URI + 'soft/' + id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);

'use strict';

angular.module('App').factory('RoleAPIService', ['$http', '$q', 'API_CONSTANTS', function($http, $q, API_CONSTANTS){
	
	var currentService = 'role';

    var REST_SERVICE_URI = API_CONSTANTS.restServiceURI + currentService + '/';

    var factory = {
        fetchAll: fetchAll,
        create: create,
        update: update,
        permDelete: permDelete
    };

    return factory;

    function fetchAll() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function create(model) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, model)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


    function update(model, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI + id, model)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function permDelete(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI + id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);

'use strict';

angular.module('App').factory('UserAPIService', ['$http', '$q', 'API_CONSTANTS', function($http, $q, API_CONSTANTS){
	
	var currentService = 'user';

    var REST_SERVICE_URI = API_CONSTANTS.restServiceURI + currentService + '/';

    var factory = {
        fetchAll: fetchAll,
        create: create,
        update: update,
        permDelete: permDelete,
        softDelete: softDelete,
        softReactive: softReactive
    };

    return factory;

    function fetchAll() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function create(model) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, model)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


    function update(model, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI + id, model)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function permDelete(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI + id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function softDelete(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI + 'soft/' + id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function softReactive(id) {
    	var deferred = $q.defer();
        $http.put(REST_SERVICE_URI + 'soft/' + id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);
