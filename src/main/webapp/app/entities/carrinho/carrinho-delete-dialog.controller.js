(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('CarrinhoDeleteController',CarrinhoDeleteController);

    CarrinhoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Carrinho'];

    function CarrinhoDeleteController($uibModalInstance, entity, Carrinho) {
        var vm = this;
        vm.carrinho = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Carrinho.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
