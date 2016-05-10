(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('CarrinhoController', CarrinhoController);

    CarrinhoController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Carrinho'];

    function CarrinhoController($scope, $rootScope, $stateParams, entity, Carrinho) {
        var vm = this;
        vm.carrinho = entity;
        
        var unsubscribe = $rootScope.$on('cervejanetApp:carrinhoUpdate', function(event, result) {
            vm.carrinho = result;
        });
        $scope.$on('$destroy', unsubscribe);
        
        $scope.addToCart = function(product_id) {
          console.log(product_id)
        };

    }
})();
