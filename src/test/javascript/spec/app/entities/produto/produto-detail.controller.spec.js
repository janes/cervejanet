'use strict';

describe('Controller Tests', function() {

    describe('Produto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProduto, MockCatalogo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProduto = jasmine.createSpy('MockProduto');
            MockCatalogo = jasmine.createSpy('MockCatalogo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Produto': MockProduto,
                'Catalogo': MockCatalogo
            };
            createController = function() {
                $injector.get('$controller')("ProdutoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cervejanetApp:produtoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
