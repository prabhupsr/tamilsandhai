angular.module('demo', [])
    .controller('Hello', function ($scope, $http) {
        $scope.dailyArray = new Array();
        $scope.weeklyArray = new Array();
        $scope.userDetails = new Array();
        $scope.niftyDetails = new Array();
        $scope. dailRange=500;
        $scope.rangeVal={dailyClose: 200,weeklyClose:300};

        $http({
            method: 'GET',
            dataType: "jsonp",
            url: '/findNiftyStocks'
        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.niftyDetails = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });


        for (var i = 0; i < 15; i++) {
            $scope.dailyArray[i] = new Array();
            $scope.weeklyArray[i] = new Array();
        }

        $scope.getdetails = function (range, type) {
            if (!range || !type) {
                return;
            }
            $http({
                method: 'GET',
                dataType: "jsonp",
                url: '/getRange/type/' + type + '/range/' + range
            }).then(function successCallback(response) {
                console.log(response.data);
                if (type == "daily") {
                    $scope.dailyArray = response.data;
                } else {
                    $scope.weeklyArray = response.data;
                }

            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        }

        $scope.updateDailyAndWeeklyLevels = function () {
            console.log( $scope.selectedNiftyStock);
            $scope.rangeVal.dailyClose=$scope.selectedNiftyStock.dailyClose;
            console.log($scope.rangeVal);
            $scope.rangeVal.weeklyClose=$scope.selectedNiftyStock.weeklyClose;
            $scope.getdetails($scope.selectedNiftyStock.dailyClose, "daily");
            $scope.getdetails($scope.selectedNiftyStock.weeklyClose, "weekly");
        }

        $scope.getBasicUserDetails = function () {
            console.log("getUserDetails Method Called");
            $http({
                method: 'GET',
                dataType: "jsonp",
                url: '/getUserDetails'
            }).then(function successCallback(response) {
                $scope.userDetails = response.data;
                $scope.rangeVal.dailyClose=$scope.userDetails.lastSearchedDailyLevel;
                $scope.rangeVal.weeklyClose=$scope.userDetails.lastSearchedWeeklyLevel;
                $scope.getdetails($scope.userDetails.lastSearchedDailyLevel, "daily");
                $scope.getdetails($scope.userDetails.lastSearchedWeeklyLevel, "weekly");
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        }
        //private String userName;
        $scope.getBasicUserDetails();

    });

