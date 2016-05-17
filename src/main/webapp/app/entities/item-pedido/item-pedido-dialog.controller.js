(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('ItemPedidoDialogController', ItemPedidoDialogController);

    ItemPedidoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ItemPedido', 'Carrinho', 'Produto', '$log', '$rootScope'];

    function ItemPedidoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, ItemPedido, Carrinho, Produto, $log, $rootScope) {
    	 var vm = this;
         vm.itemPedido = entity;
         
         //verifica se ja existe este item de pedido no carrinho
         
         
         vm.itemPedido.produto = Produto.get({id : vm.itemPedido.produto.id});
         vm.itemPedido.carrinho = $rootScope.carrinho;
         
         vm.itemPedido.preco = 
         
        //verifica se ja existe um carrinho para usuario, se nao existir cria
        //utilizar o login do usaurio como chave?
         $rootScope.carrinho.itemPedidos.push( vm.itemPedido);
         $log.log($rootScope.carrinho);
        
        
        
        var onSaveSuccess = function (result) {
            $scope.$emit('cervejanetApp:itemPedidoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.itemPedido.id !== null) {
                ItemPedido.update(vm.itemPedido, onSaveSuccess, onSaveError);
            } else {
                ItemPedido.save(vm.itemPedido, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
