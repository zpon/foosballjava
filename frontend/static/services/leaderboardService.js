﻿(function () {
    'use strict';

    var leaderboardServices = angular.module('leaderboardService', []);

    leaderboardServices.factory('leaderboard', ['$http', '$q', function ($http, $q) {

        return {
            getLeaderboard: function () {
                var deferred = $q.defer();

                $http.get("http://" + window.location.host + "/api/leaderboard/index")
                   .success(function (data, status, headers, config) {
                       console.log(data);
                       deferred.resolve(data);
                   }).error(function (data, status, headers, config) {
                       deferred.reject("Server error");
                });

                return deferred.promise;
            }
        };

    }]);
})();
