'use strict';

describe('Controller Tests', function() {

    describe('ItemPedido Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockItemPedido, MockCarrinho, MockProduto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockItemPedido = jasmine.createSpy('MockItemPedido');
            MockCarrinho = jasmine.createSpy('MockCarrinho');
            MockProduto = jasmine.createSpy('MockProduto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ItemPedido': MockItemPedido,
                'Carrinho': MockCarrinho,
                'Produto': MockProduto
            };
            createController = function() {
                $injector.get('$controller')("ItemPedidoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cervejanetApp:itemPedidoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
