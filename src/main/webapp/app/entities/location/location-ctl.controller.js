(function() {
    'use strict';

    angular
        .module('jworkzoneApp')
        .controller('LocationCtlController', LocationCtlController);

    LocationCtlController.$inject = ['Location'];

    function LocationCtlController(Location) {

        var vm = this;

        vm.locations = [];

        loadAll();

        function loadAll() {
            Location.query(function(result) {
                vm.locations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
