(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$state', 'Home', '$log',  '$rootScope'];

    function HomeController ($scope, $state, Home, $log,   $rootScope) {
        var vm = this;
        vm.produtos = [];
        vm.loadAll = function() {
        	Home.query(function(result) {
                vm.produtos = result;
                $scope.produtos = result;
            });
        };
        
        vm.createCart = function() {
        
         if ($rootScope.carrinho == null ) {
        	 $rootScope.carrinho = {"clienteId": 0,"itemPedidos": []};
             } 
        };
        
        vm.addToCart = function(produto) {
            
        	vm.createCart();
        	vm.itemPedido = $rootScope.carrinho.itemPedidos.filter(function (itemPedido) {
        	    return itemPedido.produto.id == produto.id;
        	})[0];
        	
        	if (typeof vm.itemPedido !== 'undefined' && vm.itemPedido !== null) {
        		vm.itemPedido.produto = produto;
        		vm.itemPedido.quantidade = vm.itemPedido.quantidade +1;
        	}else{
        		vm.itemPedido = { id: produto.id ,quantidade: 1, preco:produto.preco ,produto: produto };
        		$rootScope.carrinho.itemPedidos.push(vm.itemPedido)
        	}
        	$log.log(vm.itemPedido)
           };
           
        vm.createCart();
        vm.loadAll();
    }
})();
