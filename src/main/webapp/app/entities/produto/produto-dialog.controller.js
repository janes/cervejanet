(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('ProdutoDialogController', ProdutoDialogController);

    ProdutoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Produto', 'Catalogo'];

    function ProdutoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Produto, Catalogo) {
        var vm = this;
        vm.produto = entity;
        vm.catalogos = Catalogo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('cervejanetApp:produtoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.produto.id !== null) {
                Produto.update(vm.produto, onSaveSuccess, onSaveError);
            } else {
                Produto.save(vm.produto, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
