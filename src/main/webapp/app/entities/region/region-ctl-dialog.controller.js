(function() {
    'use strict';

    angular
        .module('jworkzoneApp')
        .controller('RegionCtlDialogController', RegionCtlDialogController);

    RegionCtlDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Region', 'Location'];

    function RegionCtlDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Region, Location) {
        var vm = this;

        vm.region = entity;
        vm.clear = clear;
        vm.save = save;
        vm.locations = Location.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.region.id !== null) {
                Region.update(vm.region, onSaveSuccess, onSaveError);
            } else {
                Region.save(vm.region, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jworkzoneApp:regionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
