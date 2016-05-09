(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                }
            }
        })
        .state('carrinho.add', {
            parent: 'carrinho',
            url: '/add',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrinho/carrinhos.html',
                    controller: 'CarrinhoController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                preco: null,
                                quantidade: null,
                                categoria: null,
                                imagem: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('carrinho', null, { reload: true });
                }, function() {
                    $state.go('carrinho');
                });
            }]
        });
        
        
        
    }
})();
