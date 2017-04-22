(function() {
    'use strict';

    angular
        .module('jworkzoneApp')
        .controller('WorkEntryCtlDeleteController',WorkEntryCtlDeleteController);

    WorkEntryCtlDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkEntry'];

    function WorkEntryCtlDeleteController($uibModalInstance, entity, WorkEntry) {
        var vm = this;

        vm.workEntry = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WorkEntry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
