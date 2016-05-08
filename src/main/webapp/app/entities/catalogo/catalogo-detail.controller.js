(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .controller('CatalogoDetailController', CatalogoDetailController);

    CatalogoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Catalogo', 'Produto'];

    function CatalogoDetailController($scope, $rootScope, $stateParams, entity, Catalogo, Produto) {
        var vm = this;
        vm.catalogo = entity;
        
        var unsubscribe = $rootScope.$on('cervejanetApp:catalogoUpdate', function(event, result) {
            vm.catalogo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
