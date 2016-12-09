

angular.module("App").factory("UserService", ["$http", "$q", "APP_CONSTANTS", function ($http, $q, APP_CONSTANTS) {

	let currentService = "user";

    let USER_CONTROLLER_URI = APP_CONSTANTS.baseUrl + "/" + currentService + "/";

    let factory = {
            "login": login,
            "logout": logout,
            "getUserAuthenticated": getUserAuthenticated
    };

    return factory;

    function login (user) {

    	let deferred = $q.defer();
        $http.post(USER_CONTROLLER_URI + "authenticate/", user)
            .then(
            function (response) {

                deferred.resolve(response.data);

},
            function (errResponse) {

                deferred.reject(errResponse);

}
        );
        return deferred.promise;

}

    function logout () {

    	let deferred = $q.defer();
        $http.post(USER_CONTROLLER_URI + "logout/")
            .then(
            function (response) {

                deferred.resolve(response.data);

},
            function (errResponse) {

                console.error("Error in logout " + currentService);
                deferred.reject(errResponse);

}
        );
        return deferred.promise;

}

    function getUserAuthenticated () {

    	let deferred = $q.defer();
        $http.post(USER_CONTROLLER_URI + "authenticated/")
            .then(
            function (response) {

                deferred.resolve(response.data);

},
            function (errResponse) {

                deferred.reject(errResponse);

}
        );
        return deferred.promise;

}

}]);
