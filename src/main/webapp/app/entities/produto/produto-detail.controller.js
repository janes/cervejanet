(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('ProdutoDetailController', ProdutoDetailController);

    ProdutoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Produto', 'Catalogo'];

    function ProdutoDetailController($scope, $rootScope, $stateParams, entity, Produto, Catalogo) {
        var vm = this;
        vm.produto = entity;
        
        var unsubscribe = $rootScope.$on('cervejanetApp:produtoUpdate', function(event, result) {
            vm.produto = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
