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
        	$log.log(id);
        	_.pullAllWith($rootScope.carrinho.itemPedidos, [{ 'produto': { 'id': id }}], _.isEqual);
        	$log.log($rootScope.carrinho.itemPedidos);
            $uibModalInstance.close(true);
            return $rootScope.carrinho.itemPedidos;
        };
        
    }
})();
