(function() {
    'use strict';

    angular
        .module('jworkzoneApp')
        .controller('WorkEntryCtlDetailController', WorkEntryCtlDetailController);

    WorkEntryCtlDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkEntry', 'User', 'Region'];

    function WorkEntryCtlDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkEntry, User, Region) {
        var vm = this;

        vm.workEntry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jworkzoneApp:workEntryUpdate', function(event, result) {
            vm.workEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
