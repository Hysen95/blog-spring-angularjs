

angular.module("App").controller("ArticleAPIController",
		["$scope", "$rootScope", "ArticleAPIService", "API_CONSTANTS",
			function ($scope, $rootScope, ArticleAPIService, API_CONSTANTS) {

    let self = this;
    initialize();
    self.list = [];

    self.submit = submit;
    self.fetchAll = fetchAll;
    self.create = create;
    self.update = update;
    self.edit = edit;
    self.permDelete = permDelete;
    self.softDelete = softDelete;
    self.softReactive = softReactive;
    self.reset = reset;

    fetchAll();

    function fetchAll () {

    	ArticleAPIService.fetchAll()
            .then(
            function (d) {

                self.list = d;

},
            function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.fetchAll);

}
        );

}

    function create (model) {

    	ArticleAPIService.create(model)
            .then(
            fetchAll,
            function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.create);

}
        );

}

    function update (model, id) {

    	ArticleAPIService.update(model, id)
            .then(
            fetchAll,
            function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.update);

}
        );

}

    function permDelete (id) {

    	ArticleAPIService.permDelete(id)
            .then(
            fetchAll,
            function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.permDelete);

}
        );

}

    function softDelete (id) {

    	ArticleAPIService.softDelete(id)
            .then(
            fetchAll,
            function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.softDelete);

}
        );

}

    function softReactive (id) {

    	ArticleAPIService.softReactive(id)
	        .then(
	        fetchAll,
	        function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.softReactive);

}
	    );

}

    function submit () {

    	if (self.model.id === null) {

            create(self.model);

} else {

            update(self.model, self.model.id);

}
        reset();

}

    function edit (id) {

        for (let i = 0; i < self.list.length; i++) {

            if (self.list[i].id === id) {

                self.model = angular.copy(self.list[i]);
                break;

}

}

}

    function initialize () {

        self.model = {
        		"id": null,
        		"title": null,
        		"subtitle": null,
        		"body": null,
        		"tags": null,
        		"user": {
        			"id": null
        		}
        };

}


    function reset () {

    	initialize();
        $scope.form.$setPristine();

}

}]);
