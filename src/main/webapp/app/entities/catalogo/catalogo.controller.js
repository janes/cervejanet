(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('CatalogoController', CatalogoController);

    CatalogoController.$inject = ['$scope', '$state', 'Catalogo'];

    function CatalogoController ($scope, $state, Catalogo) {
        var vm = this;
        vm.catalogos = [];
        vm.loadAll = function() {
            Catalogo.query(function(result) {
                vm.catalogos = result;
            });
        };

        vm.loadAll();
        
    }
})();
