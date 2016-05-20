(function() {
    'use strict';
    angular
        .module('cervejanetApp')
        .factory('Home', Home);

    Home.$inject = ['$resource'];

    function Home ($resource) {
        var resourceUrl =  'api/home-produtos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
