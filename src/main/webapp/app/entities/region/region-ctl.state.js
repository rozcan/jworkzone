(function() {
    'use strict';

    angular
        .module('jworkzoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('region-ctl', {
            parent: 'entity',
            url: '/region-ctl',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Regions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/region/regionsctl.html',
                    controller: 'RegionCtlController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('region-ctl-detail', {
            parent: 'region-ctl',
            url: '/region-ctl/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Region'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/region/region-ctl-detail.html',
                    controller: 'RegionCtlDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Region', function($stateParams, Region) {
                    return Region.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'region-ctl',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('region-ctl-detail.edit', {
            parent: 'region-ctl-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/region/region-ctl-dialog.html',
                    controller: 'RegionCtlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Region', function(Region) {
                            return Region.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('region-ctl.new', {
            parent: 'region-ctl',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/region/region-ctl-dialog.html',
                    controller: 'RegionCtlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                regionName: null,
                                uuid: null,
                                major: null,
                                minor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('region-ctl', null, { reload: 'region-ctl' });
                }, function() {
                    $state.go('region-ctl');
                });
            }]
        })
        .state('region-ctl.edit', {
            parent: 'region-ctl',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/region/region-ctl-dialog.html',
                    controller: 'RegionCtlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Region', function(Region) {
                            return Region.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('region-ctl', null, { reload: 'region-ctl' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('region-ctl.delete', {
            parent: 'region-ctl',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/region/region-ctl-delete-dialog.html',
                    controller: 'RegionCtlDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Region', function(Region) {
                            return Region.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('region-ctl', null, { reload: 'region-ctl' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
