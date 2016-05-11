(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('ItemPedidoDialogController', ItemPedidoDialogController);

    ItemPedidoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ItemPedido', 'Carrinho', 'Produto', '$log'];

    function ItemPedidoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, ItemPedido, Carrinho, Produto, $log) {
        var vm = this;
        vm.itemPedido = entity;
        $log.log($stateParams);
        //verifica se ja existe um carrinho para usuario, se nao existir cria
        //utilizar o login do usaurio como chave?
        
        vm.carrinho = Carrinho.get({id : '1'});
        $log.log(vm.carrinho.id);
        vm.produtos = Produto.query({filter: 'itempedido-is-null'});
        $q.all([vm.itemPedido.$promise, vm.produtos.$promise]).then(function() {
            if (!vm.itemPedido.produto || !vm.itemPedido.produto.id) {
                return $q.reject();
            }
            return Produto.get({id : vm.itemPedido.produto.id}).$promise;
        }).then(function(produto) {
            vm.produtos.push(produto);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

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
