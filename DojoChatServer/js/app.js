(function() {
  'use strict';

  angular.module('chatApp', ['chatServices', 'ngRoute']);

  angular.module('chatApp').config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/main', {
      templateUrl: 'view/main.html',
      controller: 'mainCtrl'
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
})();