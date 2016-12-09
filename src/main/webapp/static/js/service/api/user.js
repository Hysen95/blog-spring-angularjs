

angular.module("App").factory("UserAPIService",
		["$http", "$q", "API_CONSTANTS",
			function ($http, $q, API_CONSTANTS) {

	let self = this;

	let currentService = "user";

    let REST_SERVICE_URI = API_CONSTANTS.restServiceURI + currentService + "/";

    let factory = {
        "fetchAll": fetchAll,
        "fetchById": fetchById,
        "create": create,
        "update": update,
        "permDelete": permDelete,
        "softDelete": softDelete,
        "softReactive": softReactive
    };

    return factory;

    function fetchAll () {

        let deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
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

    function fetchById (id) {

        let deferred = $q.defer();
        $http.get(REST_SERVICE_URI, id)
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

    function create (model) {

        let deferred = $q.defer();
        $http.post(REST_SERVICE_URI, model)
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


    function update (model, id) {

        let deferred = $q.defer();
        $http.put(REST_SERVICE_URI + id, model)
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

    function permDelete (id) {

        let deferred = $q.defer();
        $http.delete(REST_SERVICE_URI + id)
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

    function softDelete (id) {

        let deferred = $q.defer();
        $http.delete(REST_SERVICE_URI + "soft/" + id)
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

    function softReactive (id) {

    	let deferred = $q.defer();
        $http.put(REST_SERVICE_URI + "soft/" + id)
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
