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
        	$log.log($rootScope.carrinho.itemPedidos);
            $rootScope.carrinho.itemPedidos = $rootScope.carrinho.itemPedidos.filter(function(item) {
            	return item.produto.id == id;
        	});
            $uibModalInstance.close(true);
            return $rootScope.carrinho.itemPedidos;
        };
        
    }
})();
