(function() {
    'use strict';
    angular
        .module('cervejanetApp')
        .factory('Catalogo', Catalogo);

    Catalogo.$inject = ['$resource'];

    function Catalogo ($resource) {
        var resourceUrl =  'api/catalogos/:id';

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
