(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('ItemPedidoDetailController', ItemPedidoDetailController);

    ItemPedidoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ItemPedido', 'Carrinho', 'Produto'];

    function ItemPedidoDetailController($scope, $rootScope, $stateParams, entity, ItemPedido, Carrinho, Produto) {
        var vm = this;
        vm.itemPedido = entity;
        
        var unsubscribe = $rootScope.$on('cervejanetApp:itemPedidoUpdate', function(event, result) {
            vm.itemPedido = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
