(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('ItemPedidoDeleteController',ItemPedidoDeleteController);

    ItemPedidoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ItemPedido'];

    function ItemPedidoDeleteController($uibModalInstance, entity, ItemPedido) {
        var vm = this;
        vm.itemPedido = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ItemPedido.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
