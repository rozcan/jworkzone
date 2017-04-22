(function() {
    'use strict';
    angular
        .module('jworkzoneApp')
        .factory('WorkEntry', WorkEntry);

    WorkEntry.$inject = ['$resource', 'DateUtils'];

    function WorkEntry ($resource, DateUtils) {
        var resourceUrl =  'api/work-entries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
