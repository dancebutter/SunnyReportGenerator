var myApp = angular.module("myApp", ['angular-js-xlsx']);
myCtrl.$inject = ['$scope'];
function myCtrl($scope) {
    $scope.reportTemplates = null;
    $scope.tags = null;
    $scope.jsonSheet = null;
    init();
    function resetTags() {
        for(var i = 0; i < $scope.tags.length; i++) {
            $scope.tags[i].actived = '';
            $scope.tags[i].visible = false;
        }
    };
    $scope.clickTag = function(value) {
        resetTags();
        value.actived = 'active';
        value.visible = true;
    };
    $scope.read = function(workbook) {
        console.log(workbook);
        $scope.jsonSheet = XLSX.utils.sheet_to_json(workbook.Sheets.sheet1);
        $scope.$apply();

    };
    $scope.error = function(e) {
        console.log("[ERROR] Error happened during loading.");
        console.log(e);
    };

    function init() {
        $scope.tags = [{
            name : 'Templates',
            actived : 'active',
            visible : true
        }, {
            name : 'Upload',
            actived : '',
            visible : false
        }, {
            name : 'Reports',
            actived : '',
            visible : false
        }];

        $scope.jsonSheet = [{
            'name' : 'N/A',
            'gender' : 'N/A',
            'grade' : 'N/A'
        }];

        $scope.reportTemplates = null;
        if(localStorage && localStorage.reportTemplates) {
            $scope.reportTemplates = localStorage.reportTemplates;
        } else {
            $scope.reportTemplates = {
                "N/A" : "N/A"
            };
        }
    }
}
myApp.controller('myCtrl', myCtrl);
