(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('CarrinhoDetailController', CarrinhoDetailController);

    CarrinhoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Carrinho'];

    function CarrinhoDetailController($scope, $rootScope, $stateParams, entity, Carrinho) {
        var vm = this;
        vm.carrinho = entity;
        
        var unsubscribe = $rootScope.$on('cervejanetApp:carrinhoUpdate', function(event, result) {
            vm.carrinho = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
