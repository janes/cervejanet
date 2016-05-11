(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('ItemPedidoController', ItemPedidoController);

    ItemPedidoController.$inject = ['$scope', '$state', 'ItemPedido'];

    function ItemPedidoController ($scope, $state, ItemPedido) {
        var vm = this;
        vm.itemPedidos = [];
        vm.loadAll = function() {
            ItemPedido.query(function(result) {
                vm.itemPedidos = result;
            });
        };

        vm.loadAll();
        
    }
})();
