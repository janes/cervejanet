'use strict';

describe('Controller Tests', function() {

    describe('Catalogo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCatalogo, MockProduto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCatalogo = jasmine.createSpy('MockCatalogo');
            MockProduto = jasmine.createSpy('MockProduto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Catalogo': MockCatalogo,
                'Produto': MockProduto
            };
            createController = function() {
                $injector.get('$controller')("CatalogoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cervejanetApp:catalogoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
