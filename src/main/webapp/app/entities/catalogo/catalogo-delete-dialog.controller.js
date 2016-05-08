(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('CatalogoDeleteController',CatalogoDeleteController);

    CatalogoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Catalogo'];

    function CatalogoDeleteController($uibModalInstance, entity, Catalogo) {
        var vm = this;
        vm.catalogo = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Catalogo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
