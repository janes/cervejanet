(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('ItemPedidoController', ItemPedidoController);

    ItemPedidoController.$inject = ['$scope', '$state', 'ItemPedido', 'Carrinho', '$rootScope','$log'];

    function ItemPedidoController ($scope, $state, ItemPedido, Carrinho, $rootScope, $log) {
        var vm = this;
        vm.itemPedidos = [];
        vm.carrinho = $rootScope.carrinho;
        $log.log(vm.carrinho.itemPedidos)
        vm.itemPedidos = [];
        vm.loadAll = function() {
        	vm.itemPedidos = $rootScope.carrinho.itemPedidos
        };

        vm.loadAll();
        
        
        vm.getTotal =  function(){
            var total = 0;
            for(var i = 0; i < vm.itemPedidos.length; i++){
                var itemPedido = vm.itemPedidos[i];
                total += (itemPedido.preco * itemPedido.quantidade);
            }
            return total;
        }
        
        
    }
})();
