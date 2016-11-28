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
