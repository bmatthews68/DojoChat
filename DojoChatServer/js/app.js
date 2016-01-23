(function() {
  'use strict';

  angular.module('chatApp', ['chatServices', 'ngRoute']);

  angular.module('chatApp').config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/main', {
      templateUrl: 'view/main.html',
      controller: 'mainCtrl'
    }).when('/login', {
      templateUrl: 'view/login.html',
      controller: 'loginCtrl'
    }).when('/registration', {
      templateUrl: 'view/registration.html',
      controller: 'registrationCtrl'
    }).when('/set_password', {
      templateUrl: 'view/set_password.html',
      controller: 'setPasswordCtrl'
    }).otherwise({
      redirectTo: '/main'
    });
  }]);

  angular.module('chatApp').controller('mainCtrl', ['$scope', function($scope) {
  	$scope.logout = function() {
  	};
  }]);

  angular.module('chatApp').controller('loginCtrl', ['$scope', 'sessionService', function($scope, sessionService) {
    $scope.loginForm = {};
    $scope.submit = function() {
      sessionService.login($scope.loginForm, handleResponse, handleError);
    };

    function handleResponse(response) {
      if (response.result === 'OK') {
      } else {
        handleError({});
      }
    }

    function handleError(err) {
      $scope.loginForm.username = '';
      $scope.loginForm.password = '';
    }
  }]);

  angular.module('chatApp').controller('registrationCtrl', ['$scope', 'registrationService', function($scope, registrationService) {

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

  angular.module('chatApp').controller('setPasswordCtrl', ['$scope', '$routeParams', 'registrationService', function($scope, $routeParams, registrationService) {
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
        
  	  } else {
  		handleError({});
      }
  	}

  	function handleError(err) {	
  	  $scope.setPasswordForm.password = '';
  	}
  }]);
})();