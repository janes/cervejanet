(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('CarrinhoController', CarrinhoController);

    CarrinhoController.$inject = ['$scope', '$state', 'Carrinho'];

    function CarrinhoController ($scope, $state, Carrinho) {
        var vm = this;
        vm.carrinhos = [];
        vm.loadAll = function() {
            Carrinho.query(function(result) {
                vm.carrinhos = result;
            });
        };

        vm.loadAll();
        
    }
})();
