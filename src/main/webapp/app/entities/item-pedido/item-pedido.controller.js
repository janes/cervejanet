(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('ItemPedidoController', ItemPedidoController);

    ItemPedidoController.$inject = ['$scope', '$state', 'ItemPedido', 'Carrinho', '$rootScope','$log'];

    function ItemPedidoController ($scope, $state, ItemPedido, Carrinho, $rootScope, $log) {
        var vm = this;
        vm.carrinho = $rootScope.carrinho;
        $log.log(vm.carrinho.itemPedidos)
        vm.itemPedidos = [];
        vm.loadAll = function() {
        	$rootScope.carrinho.itemPedidos
        };

        vm.loadAll();
        
    }
})();
