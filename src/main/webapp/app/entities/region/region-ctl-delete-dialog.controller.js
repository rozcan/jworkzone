(function() {
    'use strict';

    angular
        .module('jworkzoneApp')
        .controller('RegionCtlDeleteController',RegionCtlDeleteController);

    RegionCtlDeleteController.$inject = ['$uibModalInstance', 'entity', 'Region'];

    function RegionCtlDeleteController($uibModalInstance, entity, Region) {
        var vm = this;

        vm.region = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Region.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
