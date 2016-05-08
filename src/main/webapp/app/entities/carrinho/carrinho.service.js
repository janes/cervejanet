(function() {
    'use strict';
    angular
        .module('cervejanetApp')
        .factory('Carrinho', Carrinho);

    Carrinho.$inject = ['$resource'];

    function Carrinho ($resource) {
        var resourceUrl =  'api/carrinhos/:id';

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
