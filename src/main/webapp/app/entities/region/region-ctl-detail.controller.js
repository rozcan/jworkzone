(function() {
    'use strict';

    angular
        .module('jworkzoneApp')
        .controller('RegionCtlDetailController', RegionCtlDetailController);

    RegionCtlDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Region', 'Location'];

    function RegionCtlDetailController($scope, $rootScope, $stateParams, previousState, entity, Region, Location) {
        var vm = this;

        vm.region = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jworkzoneApp:regionUpdate', function(event, result) {
            vm.region = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
