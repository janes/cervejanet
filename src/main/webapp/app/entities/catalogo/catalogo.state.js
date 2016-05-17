(function() {
    'use strict';

    angular
        .module('cervejanetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('catalogo', {
            parent: 'entity',
            url: '/catalogo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Catalogos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/catalogo/catalogos.html',
                    controller: 'CatalogoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('catalogo-detail', {
            parent: 'entity',
            url: '/catalogo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Catalogo'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/catalogo/catalogo-detail.html',
                    controller: 'CatalogoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Catalogo', function($stateParams, Catalogo) {
                    return Catalogo.get({id : $stateParams.id});
                }]
            }
        })
        .state('catalogo.new', {
            parent: 'catalogo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/catalogo/catalogo-dialog.html',
                    controller: 'CatalogoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('catalogo', null, { reload: true });
                }, function() {
                    $state.go('catalogo');
                });
            }]
        })
        .state('catalogo.edit', {
            parent: 'catalogo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/catalogo/catalogo-dialog.html',
                    controller: 'CatalogoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Catalogo', function(Catalogo) {
                            return Catalogo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('catalogo', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('catalogo.delete', {
            parent: 'catalogo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/catalogo/catalogo-delete-dialog.html',
                    controller: 'CatalogoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Catalogo', function(Catalogo) {
                            return Catalogo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('catalogo', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
