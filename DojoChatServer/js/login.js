(function() {
  'use strict';

  angular.module('chatLoginApp', ['chatServices', 'ngRoute']);

  angular.module('chatLoginApp').config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/login', {
      templateUrl: 'view/login.html',
      controller: 'loginCtrl'
    }).otherwise({
      redirectTo: '/login'
    });
  }]);

  angular.module('chatLoginApp').controller('loginCtrl', ['$scope', '$location', 'sessionService', function($scope, $location, sessionService) {
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

  angular.module('chatLoginApp').controller('registerCtrl', ['$scope', '$location', function($scope, $location) {
    $scope.submit = function() {
      $location.url("/registration.html#/registration");
    }
  }]);
})();