'use strict';

describe('Controller Tests', function() {

    describe('WorkEntry Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWorkEntry, MockUser, MockRegion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWorkEntry = jasmine.createSpy('MockWorkEntry');
            MockUser = jasmine.createSpy('MockUser');
            MockRegion = jasmine.createSpy('MockRegion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WorkEntry': MockWorkEntry,
                'User': MockUser,
                'Region': MockRegion
            };
            createController = function() {
                $injector.get('$controller')("WorkEntryCtlDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jworkzoneApp:workEntryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
