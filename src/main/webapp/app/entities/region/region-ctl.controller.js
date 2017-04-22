(function() {
    'use strict';

    angular
        .module('jworkzoneApp')
        .controller('RegionCtlController', RegionCtlController);

    RegionCtlController.$inject = ['Region'];

    function RegionCtlController(Region) {

        var vm = this;

        vm.regions = [];

        loadAll();

        function loadAll() {
            Region.query(function(result) {
                vm.regions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
