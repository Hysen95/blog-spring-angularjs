

angular.module("App").controller("UserAPIController",
		["$q", "$scope", "$rootScope", "UserAPIService", "RoleAPIService", "API_CONSTANTS",
			function ($q, $scope, $rootScope, UserAPIService, RoleAPIService, API_CONSTANTS) {

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

    self.roles = [];
    RoleAPIService.fetchAll().then(function (response) {

    	self.roles = response;
    	for (let i = 0; i < self.roles.length; i++) {

    		self.roles[i].active = false;

}

});

    fetchAll();

    function fetchAll () {

        UserAPIService.fetchAll()
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

        UserAPIService.create(model)
            .then(
            fetchAll,
            function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.create);

}
        );

}

    function update (model, id) {

        UserAPIService.update(model, id)
            .then(
            fetchAll,
            function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.update);

}
        );

}

    function permDelete (id) {

        UserAPIService.permDelete(id)
            .then(
            fetchAll,
            function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.permDelete);

}
        );

}

    function softDelete (id) {

        UserAPIService.softDelete(id)
            .then(
            fetchAll,
            function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.softDelete);

}
        );

}

    function softReactive (id) {

    	UserAPIService.softReactive(id)
	        .then(
	        fetchAll,
	        function (errResponse) {

                $rootScope.errorAlert(API_CONSTANTS.messages.softReactive);

}
	    );

}

    function submit () {

    	setCurrentRoles();
    	if (self.model.id === null) {

            create(self.model);

} else {

            update(self.model, self.model.id);

}
        reset();

}

    function setCurrentRoles () {

    	self.model.roles = [];
    	for (let i = 0; i < self.roles.length; i++) {

    		if (self.roles[i].active) {

    			self.model.roles.push(self.roles[i]);

}

}

}

    function updateActiveRoles (roles) {

    	for (let i = 0; i < roles.length; i++) {

    		for (let j = 0; j < self.roles.length; j++) {

    			if (self.roles[j].id === roles[i].id) {

    				self.roles[j].active = true;

}

}

}

}

    function edit (id) {

        for (let i = 0; i < self.list.length; i++) {

            if (self.list[i].id === id) {

                self.model = angular.copy(self.list[i]);
                updateActiveRoles(self.list[i].roles);
                break;

}

}

}

    function initialize () {

        self.model = {
        		"id": null,
        		"firstName": null,
        		"lastName": null,
        		"email": null,
        		"username": null,
        		"password": null,
        		"createdAt": null,
        		"deletedAt": null,
        		"roles": [],
        		"articles": []
        };

}

    function initializeRoles () {

    	for (let i = 0; i < self.roles.length; i++) {

    		self.roles[i].active = false;

}

}

    function reset () {

    	initialize();
        initializeRoles();
        $scope.form.$setPristine();

}

}]);
