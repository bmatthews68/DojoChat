(function() {
  'use strict';

  angular.module('chatServices', ['ngResource']);

  angular.module('chatServices').factory('registrationService', [ '$resource', registrationService ]);

  angular.module('chatServices').factory('sessionService', [ '$resource', sessionService ]);

  function registrationService($resource) {
    return $resource('/api/registrations/:token', { token: '@token'}, {
      'begin':    { method: 'POST' },
      'complete': { method: 'PUT'  }
    });
  }

  function sessionService($resource) {
  	return $resource('/api/sessions/:id', { id: '@id'}, {
  		'login':  { method: 'POST'   },
  		'logout': { method: 'DELETE' }
  	});
  }

})();