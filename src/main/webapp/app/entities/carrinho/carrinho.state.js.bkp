(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('carrinho', {
            parent: 'entity',
            url: '/carrinho',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Carrinhos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carrinho/carrinhos.html',
                    controller: 'CarrinhoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('carrinho-detail', {
            parent: 'entity',
            url: '/carrinho/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Carrinho'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carrinho/carrinho-detail.html',
                    controller: 'CarrinhoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Carrinho', function($stateParams, Carrinho) {
                    return Carrinho.get({id : $stateParams.id});
                }]
            }
        })
        .state('carrinho.new', {
            parent: 'carrinho',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrinho/carrinho-dialog.html',
                    controller: 'CarrinhoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                clienteId: null,
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
        })
        .state('carrinho.edit', {
            parent: 'carrinho',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrinho/carrinho-dialog.html',
                    controller: 'CarrinhoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Carrinho', function(Carrinho) {
                            return Carrinho.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('carrinho', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carrinho.delete', {
            parent: 'carrinho',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrinho/carrinho-delete-dialog.html',
                    controller: 'CarrinhoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Carrinho', function(Carrinho) {
                            return Carrinho.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('carrinho', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
