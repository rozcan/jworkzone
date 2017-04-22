(function() {
    'use strict';

    angular
        .module('jworkzoneApp')
        .controller('WorkEntryCtlDialogController', WorkEntryCtlDialogController);

    WorkEntryCtlDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'WorkEntry', 'User', 'Region'];

    function WorkEntryCtlDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, WorkEntry, User, Region) {
        var vm = this;

        vm.workEntry = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.regions = Region.query({filter: 'workentry-is-null'});
        $q.all([vm.workEntry.$promise, vm.regions.$promise]).then(function() {
            if (!vm.workEntry.regionId) {
                return $q.reject();
            }
            return Region.get({id : vm.workEntry.regionId}).$promise;
        }).then(function(region) {
            vm.regions.push(region);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.workEntry.id !== null) {
                WorkEntry.update(vm.workEntry, onSaveSuccess, onSaveError);
            } else {
                WorkEntry.save(vm.workEntry, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jworkzoneApp:workEntryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
