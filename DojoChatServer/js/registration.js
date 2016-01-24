(function() {
  'use strict';

  angular.module('chatRegistrationApp', ['chatServices', 'ngRoute']);

  angular.module('chatRegistrationApp').config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/registration', {
      templateUrl: 'view/registration.html',
      controller: 'registrationCtrl'
    }).when('/registered', {
      templateUrl: 'view/registered.html'
    }).when('/set_password', {
      templateUrl: 'view/set_password.html',
      controller: 'setPasswordCtrl'
    }).otherwise({
      redirectTo: '/registration'
    });
  }]);

  angular.module('chatRegistrationApp').controller('registrationCtrl', ['$scope', 'registrationService', function($scope, registrationService) {

	  $scope.registrationForm = {};

	  $scope.submit = function() {
      registrationService.begin($scope.registrationForm, handleResponse, handleError);
	  };

	  function handleResponse(response) {
      if (response.result === "OK") {
      } else {
      	handleError({});
      }
	  }

  	function handleError(err) {
  	}
  }]);

  angular.module('chatRegistrationApp').controller('setPasswordCtrl', ['$scope', '$routeParams', '$location', 'registrationService', function($scope, $routeParams, registrationService) {
  	$scope.setPasswordForm = {};
  	$scope.submit = function() {
      registrationService.complete(
      	{ token: $routeParams.token},
      	$scope.setPasswordForm, 
      	handleResponse, 
      	handleError);
  	};

  	function handleResponse(response) {
  	  if (response.result === 'OK') {
        $location.path('/login');
  	  } else {
  		handleError({});
      }
  	}

  	function handleError(err) {	
  	  $scope.setPasswordForm.password = '';
  	  $scope.setPasswordForm.confirmPassword = '';
  	}
  }]);
})();