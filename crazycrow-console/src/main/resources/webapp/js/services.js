angular.module('AppResources', ['ngResource'])
        .factory('Crows', function ($resource) {
            return $resource("http://localhost\\:9090/api/crows/:name", {}, {});
        });
