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
    }).when('/registered', {
      templateUrl: 'view/registered.html'
    }).when('/set_password', {
      templateUrl: 'view/set_password.html',
      controller: 'setPasswordCtrl'
    }).otherwise({
      redirectTo: '/main'
    });
  }]);

  angular.module('chatApp').controller('mainCtrl', ['$scope', '$location', 'sessionService', function($scope, $location, sessionService) {
  	$scope.logout = function() {
  	  sessionService.logout();
  	  $location.path("/login");
  	};
  }]);

  angular.module('chatApp').controller('loginCtrl', ['$scope', '$location', 'sessionService', function($scope, $location, sessionService) {
    $scope.loginForm = {};
    $scope.submit = function() {
      sessionService.login($scope.loginForm, handleResponse, handleError);
    };

    function handleResponse(response) {
      if (response.result === 'OK') {
      	$location.path("/main");
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

  angular.module('chatApp').controller('setPasswordCtrl', ['$scope', '$routeParams', '$location', 'registrationService', function($scope, $routeParams, registrationService) {
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