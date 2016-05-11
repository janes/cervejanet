(function() {
    'use strict';
    angular
        .module('cervejanetApp')
        .factory('ItemPedido', ItemPedido);

    ItemPedido.$inject = ['$resource'];

    function ItemPedido ($resource) {
        var resourceUrl =  'api/item-pedidos/:id';

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
