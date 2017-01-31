var baseUrl = "http://localhost:8080/flashcards/api/v1/";

var app = angular.module('flashcardApp', []);

app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.withCredentials = true;
}]);

app.controller('flashCardsCtrl', function ($scope, $http, $sce, $window) {

    $scope.currentUser;
    $scope.showLogin = false;
    $scope.categories;
    $scope.myboxes;
    $scope.currentboxid;
    $scope.boxcontent;
    $scope.flashcards;
    $scope.boxes;
    $scope.currentCard = {};
    $scope.currentIndex = 0;
    
    $scope.getUser = function () {
        $http.get(baseUrl + 'users/me').then($scope.getUserCallback);
    };

    $scope.getUserCallback = function (response) {
        if (response.status != 200 || !response.data.id) {
            $window.location.href = '/login';
            return;
        }
        $scope.currentUser = response.data;
    };
    
    $scope.register = function () {
        var data = angular.toJson($scope.request);

        $http.post(baseUrl + 'users/register', data).then($scope.registerCallback);
    };
	
	$scope.registerCallback = function(response) {
		if(response.status==200){
			var data = angular.toJson(
				$scope.request
			);
			$http.post(baseUrl + 'users/login', data).then($scope.loginCallback);
		}
	};
	
	$scope.login = function(){
	     var data = angular.toJson($scope.request);
	     
	     $http.post(baseUrl + 'users/login', data).then($scope.loginCallback);
	};
	
	$scope.loginCallback = function(response) {
		if(response.status==200){
			$scope.getUser();
			setTimeout(() => {
				window.location.href = "./myboxes";
				$scope.getUser();
			}, 100);
		}
	};
	
	$scope.logout = function(){
	     var data = angular.toJson($scope.request);
	     
	     $http.get(baseUrl + 'users/logout', data).then($scope.logoutCallback);
	};
	
	$scope.logoutCallback = function(response) {
		if(response.status==200){
			$scope.currentUser = null;
		}
	};
	
	
     $scope.deleteOwnersBox = function(boxid){
    	var delurl = 'http://localhost:8080/flashcards/api/v1/users/me/boxes/' + boxid;
    	console.log(delurl);
    	 $http({
    	    method: 'DELETE',
    	    url: delurl
    	          
    	}).then(function(){
    	    $scope.getAllBoxes();
    	}); 
    	    
     };

     $scope.deleteCard = function(fcid){
    	var delurl = 'http://localhost:8080/flashcards/boxes/'+ boxId +'/' + fcid;
    	console.log(delurl);
    	 $http({
    	    method: 'DELETE',
    	    url: delurl
    	          
    	}).then(function(){
    	    $scope.getAllBoxes();
    	}); 
    	    
     };

    $scope.deleteUser = function (delid) {
        var delurl = 'http://localhost:8080/flashcards/api/v1/users/' + delid;
        console.log(delurl);
        $http({
            method: 'DELETE',
            url: 'http://localhost:8080/flashcards/api/v1/users/' + delid

        }).then(function () {
            //$scope.getAllUsers();
        });

    };

    $scope.getCategories = function () {
        $http.get(baseUrl + 'boxes/categories').then(function successCallback(response) {
            $scope.categories = response.data;
        }, function errorCallback(response) {
        });
    };

    $scope.addBox = function () {
        var data = angular.toJson($scope.request);
        $http.post(baseUrl + 'users/me/boxes', data).then(function successCallback(response) {
            setTimeout(function () {
                window.location.href = "./myboxes";
                $scope.getUser();
            }, 100);
        }, function errorCallback(response) {
            //alert()
        });
    };

    $scope.getMyBoxes = function () {
        $http.get(baseUrl + 'users/me/boxes').then(function successCallback(response) {
            $scope.myboxes = response.data;
        }, function errorCallback(response) {
            //$scope.getMyBoxes();
        });
    };

    $scope.getAllBoxes = function () {
        $http.get(baseUrl + 'boxes').then(function successCallback(response) {
            $scope.boxes = response.data;
        }, function errorCallback(response) {
            //$scope.getAllBoxes();
        });
    };

    $scope.setCurrentBoxID = function (nummer) {
        $scope.currentboxid = nummer;
        setTimeout(function () {
            window.location.href = "./editbox";
        }, 100);
    };

    $scope.getBoxContent = function () {
        $http.get(baseUrl + 'boxes/' + boxId).then(function successCallback(response) {
            $scope.boxcontent = response.data;
            $http.get(baseUrl + 'boxes/' + boxId + '/flashcards').then(
                function successCallback(response) {
                    $scope.flashcards = response.data;
                }, function errorCallback(response) {
                });
        }, function errorCallback(response) {
        });
    };

    /**
     * No saving of session implemented yet,
     * just fetch 20 cards from server
     */
    $scope.getCurrentSession = function () {
        $http.post(baseUrl + 'users/me/boxes/' + boxId + '/service/nextCard')
            .then(function (response) {//Success
                $scope.flashcards = response.data;
                $scope.currentCard = $scope.flashcards[0];
            }, function (response) { //Error

            });
    };

    $scope.getNextCard = function () {
        $scope.currentIndex++;
        if ($scope.currentIndex > $scope.flashcards.length + 1) {
            $scope.currentCard = null;
        }
        $scope.currentCard = $scope.flashcards[$scope.currentIndex];
    };

    $scope.scoreCard = function (isCorrect) {
        var data = {
            'isCorrect': isCorrect
        };

        $http.post(baseUrl + 'users/me/boxes/' + boxId + '/service/scorecard/' + $scope.currentCard.id);

        $scope.getNextCard();
    };
});