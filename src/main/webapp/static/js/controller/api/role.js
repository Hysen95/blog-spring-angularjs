

angular.module("App").controller("RoleAPIController",
		["$scope", "$rootScope", "RoleAPIService", "API_CONSTANTS",
			function ($scope, $rootScope, RoleAPIService, API_CONSTANTS) {

    let self = this;
    initialize();
    self.list = [];

    self.submit = submit;
    self.fetchAll = fetchAll;
    self.create = create;
    self.update = update;
    self.edit = edit;
    self.permDelete = permDelete;
    self.reset = reset;

    fetchAll();

    function fetchAll () {

    	RoleAPIService.fetchAll()
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

    	RoleAPIService.create(model)
            .then(
            fetchAll,
            function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.create);

}
        );

}

    function update (model, id) {

    	RoleAPIService.update(model, id)
            .then(
            fetchAll,
            function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.update);

}
        );

}

    function permDelete (id) {

    	RoleAPIService.permDelete(id)
            .then(
            fetchAll,
            function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.permDelete);

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
        		"name": null,
        		"isDefault": 0,
        		"parentId": null
        };

}


    function reset () {

    	initialize();
        $scope.form.$setPristine();

}

}]);
