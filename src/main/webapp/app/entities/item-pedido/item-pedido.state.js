(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('item-pedido', {
            parent: 'entity',
            url: '/item-pedido',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ItemPedidos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/item-pedido/item-pedidos.html',
                    controller: 'ItemPedidoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('item-pedido-detail', {
            parent: 'entity',
            url: '/item-pedido/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ItemPedido'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/item-pedido/item-pedido-detail.html',
                    controller: 'ItemPedidoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ItemPedido', function($stateParams, ItemPedido) {
                    return ItemPedido.get({id : $stateParams.id});
                }]
            }
        })
        .state('item-pedido.new', {
            parent: 'item-pedido',
            url: '/new/:id',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', 'Produto', function($stateParams, $state, $uibModal, $Produto) {
                $uibModal.open({
                    templateUrl: 'app/entities/item-pedido/item-pedido-dialog.html',
                    controller: 'ItemPedidoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                quantidade: 1,
                                preco: null,
                                id: null,
                                produto : {
                                	id: $stateParams.id
                                }
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('item-pedido', null, { reload: true });
                }, function() {
                    $state.go('item-pedido');
                });
            }]
        })
        .state('item-pedido.edit', {
            parent: 'item-pedido',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/item-pedido/item-pedido-dialog.html',
                    controller: 'ItemPedidoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ItemPedido', function(ItemPedido) {
                            return ItemPedido.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('item-pedido', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('item-pedido.delete', {
            parent: 'item-pedido',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', '$rootScope', function($stateParams, $state, $uibModal,$rootScope) {
                $uibModal.open({
                    templateUrl: 'app/entities/item-pedido/item-pedido-delete-dialog.html',
                    controller: 'ItemPedidoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ItemPedido', function() {
                        	
                        	var itemPedido = $rootScope.carrinho.itemPedidos.filter(function(item) {
                        	    return item.id == $stateParams.id ;
                        	});
                            return itemPedido[0];
                        }]
                    }
                }).result.then(function() {
                    $state.go('item-pedido', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
