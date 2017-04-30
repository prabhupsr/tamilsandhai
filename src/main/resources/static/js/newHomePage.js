angular.module('demo1', [])
    .controller('Hello1', function ($scope, $http) {
        $scope.userName = "";
        $scope.password = "";
        $scope.infoText = "";


        $scope.getLoginData = function (responseData) {
            $scope.userdata=new Object();
            $scope.userdata.userName=$scope.userName;
            $scope.userdata.password=$scope.password;
            if($scope.userName=="" || $scope.password==""){
                $scope.infoText="userName and password cannot be empty";
                return;
            }
            $scope.infoText="Validating Login....."
            $http({
                method: 'POST',
                url: '/landingPage',
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify($scope.userdata),
            }).then(function successCallback(response) {
                console.log(response.data);
                if(response.data.errorText==""){
                    $scope.infoText="Successfully Validated ! Redirecting ...."
                    window.location.href = response.data.pageName;
                }
                else{
                    $scope.infoText=response.data.errorText;
                }
            }, function errorCallback(response) {
                console.log(response.statusText);
            });


        }

    });