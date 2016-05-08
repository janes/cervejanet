'use strict';

describe('Controller Tests', function() {

    describe('Carrinho Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCarrinho;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCarrinho = jasmine.createSpy('MockCarrinho');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Carrinho': MockCarrinho
            };
            createController = function() {
                $injector.get('$controller')("CarrinhoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cervejanetApp:carrinhoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
