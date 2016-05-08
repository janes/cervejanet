(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('CarrinhoDialogController', CarrinhoDialogController);

    CarrinhoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Carrinho'];

    function CarrinhoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Carrinho) {
        var vm = this;
        vm.carrinho = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('cervejanetApp:carrinhoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.carrinho.id !== null) {
                Carrinho.update(vm.carrinho, onSaveSuccess, onSaveError);
            } else {
                Carrinho.save(vm.carrinho, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
