(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$state', 'Produto', '$log', 'Principal', 'Carrinho'];

    function HomeController ($scope, $state, Produto, $log, Principal, Carrinho) {
        var vm = this;
        Principal.identity().then(function(account) {
            vm.account = account;
        });
        vm.produtos = [];
        vm.loadAll = function() {
        	Produto.query(function(result) {
                vm.produtos = result;
            });
        };
        $scope.addToCart = function(product) {
        	//$log.log(product);
        	Carrinho.update(product)
            //$log.log(JSON.stringify($scope.shoppingBasket));
        };

        vm.loadAll();
        
        
        
    }
})();
