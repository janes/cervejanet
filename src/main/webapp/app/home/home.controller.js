(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$state', 'Produto', '$log'];

    function HomeController ($scope, $state, Produto, $log) {
        var vm = this;
        vm.produtos = [];
        vm.loadAll = function() {
        	Produto.query(function(result) {
                vm.produtos = result;
            });
        };
        $scope.addToCart = function(product_id) {
        	$log.log(product_id);
            $scope.shoppingBasket.push(product_id);
            $log.log(JSON.stringify($scope.shoppingBasket));
        };

        vm.loadAll();
        
        function addToCart(){
            CarrinhoService.update(item);
        }
        
    }
})();
