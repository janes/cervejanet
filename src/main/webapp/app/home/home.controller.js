(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$state', 'Produto', '$log', 'Principal', 'Carrinho', '$rootScope'];

    function HomeController ($scope, $state, Produto, $log, Principal, Carrinho, $rootScope) {
        var vm = this;
       /* Principal.identity().then(function(account) {
            vm.account = account;
        });*/
        vm.produtos = [];
        vm.loadAll = function() {
        	Produto.query(function(result) {
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
        		vm.itemPedido = { quantidade: 1, preco:produto.preco  ,produto: produto };
        		$rootScope.carrinho.itemPedidos.push(vm.itemPedido)
        	}
        	$log.log(vm.itemPedido)
           };
           
         vm.getImage = function(produto) { 
        	 return produto.image;
         }
               
        /*$scope.addToCart = function(product) {
        	//$log.log(product);
        	Carrinho.update(product)
            //$log.log(JSON.stringify($scope.shoppingBasket));
        };*/
        vm.createCart();
        vm.loadAll();
    }
})();
