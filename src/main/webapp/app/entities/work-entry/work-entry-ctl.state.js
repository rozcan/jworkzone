(function() {
    'use strict';

    angular
        .module('jworkzoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('work-entry-ctl', {
            parent: 'entity',
            url: '/work-entry-ctl',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WorkEntries'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/work-entry/work-entriesctl.html',
                    controller: 'WorkEntryCtlController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('work-entry-ctl-detail', {
            parent: 'work-entry-ctl',
            url: '/work-entry-ctl/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WorkEntry'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/work-entry/work-entry-ctl-detail.html',
                    controller: 'WorkEntryCtlDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'WorkEntry', function($stateParams, WorkEntry) {
                    return WorkEntry.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'work-entry-ctl',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('work-entry-ctl-detail.edit', {
            parent: 'work-entry-ctl-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-entry/work-entry-ctl-dialog.html',
                    controller: 'WorkEntryCtlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkEntry', function(WorkEntry) {
                            return WorkEntry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-entry-ctl.new', {
            parent: 'work-entry-ctl',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-entry/work-entry-ctl-dialog.html',
                    controller: 'WorkEntryCtlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                endDate: null,
                                uniqueId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('work-entry-ctl', null, { reload: 'work-entry-ctl' });
                }, function() {
                    $state.go('work-entry-ctl');
                });
            }]
        })
        .state('work-entry-ctl.edit', {
            parent: 'work-entry-ctl',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-entry/work-entry-ctl-dialog.html',
                    controller: 'WorkEntryCtlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkEntry', function(WorkEntry) {
                            return WorkEntry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-entry-ctl', null, { reload: 'work-entry-ctl' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-entry-ctl.delete', {
            parent: 'work-entry-ctl',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-entry/work-entry-ctl-delete-dialog.html',
                    controller: 'WorkEntryCtlDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WorkEntry', function(WorkEntry) {
                            return WorkEntry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-entry-ctl', null, { reload: 'work-entry-ctl' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
