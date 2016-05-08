(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$state', 'Produto'];

    function HomeController ($scope, $state, Produto) {
        var vm = this;
        vm.produtos = [];
        vm.loadAll = function() {
        	Produto.query(function(result) {
                vm.produtos = result;
            });
        };

        vm.loadAll();
        
    }
})();
