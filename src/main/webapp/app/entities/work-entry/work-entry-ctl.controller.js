(function() {
    'use strict';

    angular
        .module('jworkzoneApp')
        .controller('WorkEntryCtlController', WorkEntryCtlController);

    WorkEntryCtlController.$inject = ['WorkEntry'];

    function WorkEntryCtlController(WorkEntry) {

        var vm = this;

        vm.workEntries = [];

        loadAll();

        function loadAll() {
            WorkEntry.query(function(result) {
                vm.workEntries = result;
                vm.searchQuery = null;
            });
        }
    }
})();
