(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('ItemPedidoDeleteController',ItemPedidoDeleteController);

    ItemPedidoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ItemPedido', '$rootScope', '$log'];

    function ItemPedidoDeleteController($uibModalInstance, entity, ItemPedido, $rootScope, $log) {
        var vm = this;
        vm.itemPedido = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
        	var pedido = $rootScope.carrinho.itemPedidos.filter(function (item) {
        	    return id != item.id;
        	});
        	$rootScope.carrinho.itemPedidos = pedido;
            $uibModalInstance.close(true);
            return pedido;
        };
        
    }
})();
