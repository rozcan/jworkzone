(function() {
    'use strict';

    angular
        .module('jworkzoneApp')
        .controller('LocationCtlDetailController', LocationCtlDetailController);

    LocationCtlDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Location', 'Region', 'User'];

    function LocationCtlDetailController($scope, $rootScope, $stateParams, previousState, entity, Location, Region, User) {
        var vm = this;

        vm.location = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jworkzoneApp:locationUpdate', function(event, result) {
            vm.location = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
