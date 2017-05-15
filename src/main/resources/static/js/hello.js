angular.module('demo', ["jqwidgets"])
    .controller('Hello', function ($scope, $http) {
        $scope.dailyArray = new Array();
        $scope.weeklyArray = new Array();
        $scope.userDetails = new Array();
        $scope.niftyDetails = new Array();
        $scope. dailRange=500;
        $scope.rangeVal={dailyClose: 200,weeklyClose:300};
        $scope.sunRaiseDetails = new Array();
        $scope.dailyLevel = 0;
        $scope.weeklyp1 = 0;
        $scope.weeklyp2 = 0;
        $scope.parseInt = parseInt;
        $scope.selectedStock="";


        for (var i = 0; i < 15; i++) {
            $scope.dailyArray[i] = new Array();
            $scope.weeklyArray[i] = new Array();
            $scope.dailyArray[i].level='x';
            $scope.dailyArray[i].name='x';
            $scope.weeklyArray[i].level='x';
            $scope.weeklyArray[i].name='x';
        }



        $http({
            method: 'GET',
            dataType: "jsonp",
            url: '/calculateTimeDetails'
        }).then(function successCallback(response) {
            console.log(response.data);

        }, function errorCallback(response) {
            console.log(response.statusText);
        });

        $http({
            method: 'GET',
            dataType: "jsonp",
            url: '/findStocksAndCommodities'
        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.niftyDetails = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

        $scope.sunRaiseData = new Array();

        $http({
            method: 'GET',
            dataType: "jsonp",
            url: '/calculateTimeDetails'
        }).then(function successCallback(response) {
            console.log("time Details:  "+response.data);
            $scope.getTimeTableDetails(response.data);

        }, function errorCallback(response) {
            console.log(response.statusText);
        });



        $scope.getTimeTableDetails = function (responseData) {

            $scope.divDataHtml="<tr style='background: lightgrey;'><td>Day</td><td>startTime</td><td>endTime</td></tr>";
            $scope.sunRaiseData = responseData;
            for(var i=0;i<$scope.sunRaiseData.sunsetSunRaiseDetailses.length;i++){
                console.log("timed : : "+i);
                var tempSunData=$scope.sunRaiseData.sunsetSunRaiseDetailses[i];
                if(i==0 || i==7 || i==14){
                    $scope.divDataHtml=$scope.divDataHtml+"<tr><td style='background: lightgreen;'>"+tempSunData.day+"</td><td>"+tempSunData.startTime+"</td><td>"+tempSunData.endTime+"</td></tr>";
                }else{
                    $scope.divDataHtml=$scope.divDataHtml+"<tr><td>"+tempSunData.day+"</td><td>"+tempSunData.startTime+"</td><td>"+tempSunData.endTime+"</td></tr>";
                }

            }
            $scope.divDataHtml=$scope.divDataHtml+"";
            $('#sunRaiseTableId').html($scope.divDataHtml);
            $('#sunraisetd_id').html($scope.sunRaiseData.sunRaise);
            $('#sunsettd_id').html($scope.sunRaiseData.sunSet);
            $('#datetrtd_id').html($scope.sunRaiseData.todaysDate);
            $('#daytrtd_id').html($scope.sunRaiseData.todaysDay);


        }

        $scope.getdetails = function (range, type) {
            if (!range || !type) {
                return;
            }
            $http({
                method: 'GET',
                dataType: "jsonp",
                url: '/getRange/type/' + type + '/range/' + range + '/calculate'
            }).then(function successCallback(response) {
                console.log(response.data);
                if (type == "daily") {
                    $scope.dailyArray = response.data;
                    $scope.dailyLevel = range;
                } else {
                    $scope.weeklyArray = response.data;
                    $scope.weeklyp1 = $scope.weeklyArray[6].level;
                    $scope.weeklyp2 = $scope.weeklyArray[8].level;
                }
                if ($scope.weeklyp1 && $scope.weeklyp2 && $scope.dailyLevel) {
                    if($scope.dailyLevel<$scope.weeklyp1){
                        document.getElementById('Trend1').innerHTML="<div style='height: 50px;background:red'>negative</div>";
                    }else if($scope.dailyLevel>$scope.weeklyp2){
                        document.getElementById('Trend1').innerHTML="<div style='height: 50px;background:green'>positive</div>";
                    }else{
                        document.getElementById('Trend1').innerHTML="<div style='height: 50px;background:yellow'>neutral</div>";
                    }
                }
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        }

        $scope.updateDailyAndWeeklyLevels = function () {
            console.log( $scope.selectedNiftyStock);
            $scope.selectedStock=$scope.selectedNiftyStock.name;
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

        $scope.logoutUser = function () {
            $http({
                method: 'GET',
                dataType: "jsonp",
                url: '/logout'
            }).then(function successCallback(response) {

            }, function errorCallback(response) {
                console.log(response.statusText);
            });
            window.location.href = "newHomePage.html";
        }
        //private String userName;
        $scope.getBasicUserDetails();

        $(document).ready(function () {
            // prepare the data

            var favData = new Array();
            var roww;

            var tempfav = [];

            jQuery.ajax({
                type: "GET",
                url: "/getFavorites",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                async: false,
                success: function (data, status, jqXHR) {
                    favData = data;
                    console.log(data);
                    console.log(favData);

                },
                error: function (jqXHR, status) {
                    // error handler
                }
            });

            for (var i = 0; i < favData.length; i++) {
                console.log("in fav ite " + i);
                //data[i] = favData[i];
                tempfav[i] = new Object();
            }


            var source =
            {
                localdata: favData,
                datatype: "array",
                updaterow: function (rowid, rowdata, commit) {
                    console.log("updaterow" + rowdata);
                    roww = rowdata;
                    // synchronize with the server - send update command
                    // call commit with parameter true if the synchronization with the server is successful
                    // and with parameter false if the synchronization failder.
                    commit(true);
                },
                datafields: [
                    {name: 'symbol', type: 'string'},
                    {name: 'dailyClose', type: 'number'},
                    {name: 'weeklyClose', type: 'number'}
                ]
            };

            var dataAdapter = new $.jqx.dataAdapter(source);

            // initialize jqxGrid
            $("#favGrid").jqxGrid(
                {
                    width: 240,
                    height:180,
                    source: dataAdapter,
                    editable: true,
                    selectionmode: 'singlerow',
                    editmode: 'selectedrow',
                    columns: [
                        {text: 'Symbol', columntype: 'textbox', datafield: 'symbol', width: 120},
                        {text: 'daily', datafield: 'dailyClose', columntype: 'textbox', width: 60},
                        {text: 'weekly', columntype: 'textbox', datafield: 'weeklyClose', width: 60}
                    ]
                });

            $("#favGrid").on('rowselect', function (event) {
                console.log($scope.dailyArray);
                console.log(event.args);
                $scope.selectedStock=event.args.row.name;
                $scope.rangeVal.dailyClose=event.args.row.dailyClose;
                $scope.rangeVal.weeklyClose=event.args.row.weeklyClose;
                $scope.getdetails(event.args.row.dailyClose,"daily");
                $scope.getdetails(event.args.row.weeklyClose,"weekly");
            });
            // events
            var rowValues = "";
            $("#favGrid").on('cellendedit', function (event) {
                if (event.args.datafield === "name") {
                    tempfav[event.args.rowindex].name = event.args.value;
                    tempfav[event.args.rowindex].oldName = event.args.oldvalue;
                    tempfav[event.args.rowindex].pos = event.args.rowindex;
                    favData[event.args.rowindex].name=event.args.value;
                }
                else if (event.args.datafield === "dailyClose") {
                    tempfav[event.args.rowindex].dailyClose = event.args.value;
                    favData[event.args.rowindex].dailyClose=event.args.value;
                }
                else if (event.args.datafield === "weeklyClose") {
                    tempfav[event.args.rowindex].weeklyClose = event.args.value;
                    favData[event.args.rowindex].weeklyClose=event.args.value;
                }
                console.log(JSON.stringify(tempfav[event.args.rowindex]));

                if (Object.keys(tempfav[event.args.rowindex]).length == 5) {
                    console.log("updating data");

                    jQuery.ajax({
                        type: "POST",
                        url: "/updateFav",
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        async: false,
                        data: JSON.stringify(tempfav[event.args.rowindex]),
                        success: function (data, status, jqXHR) {
                            console.log(data);
                        },
                        error: function (jqXHR, status) {
                            // error handler
                        }
                    });
                    $scope.selectedStock=tempfav[event.args.rowindex].name;
                    $scope.rangeVal.dailyClose = parseFloat(tempfav[event.args.rowindex].dailyClose);
                    $scope.rangeVal.weeklyClose = parseFloat(tempfav[event.args.rowindex].weeklyClose);
                    $scope.getdetails(tempfav[event.args.rowindex].dailyClose,"daily");
                    $scope.getdetails(tempfav[event.args.rowindex].weeklyClose,"weekly");
                    $("#favGrid").jqxGrid('updatebounddata', 'cells');
                    tempfav[event.args.rowindex] = new Object();
                }
            });

        });



    });

