var baseUrl = "http://localhost:8080/flashcards/api/v1/";

var app = angular.module('flashcardApp', []);

app.controller('flashCardsCtrl', function($scope, $http, $sce) {
	$scope.currentUser;
	$scope.showLogin = false;
	$scope.categories;
	$scope.boxes;
	$scope.myboxes;
	$scope.currentboxid;
	$scope.boxcontent;
	$scope.flashcards;
	
	
	$scope.getUser = function () {
		$http.get(baseUrl + 'users/me').then($scope.getUserCallback);
	}
	
	$scope.getUserCallback = function (response) {
		if(response.status!=200 || !response.data.id){
			$scope.showLogin = true;
			return;
		}
		$scope.currentUser = response.data;
		
	}
	
	$scope.register = function(){	     
	     var data = angular.toJson($scope.request);

	     $http.post(baseUrl + 'users/register', data).then($scope.registerCallback);
	}
	
	$scope.registerCallback = function(response) {
		if(response.status==200){
			var data = angular.toJson(
				$scope.request
			);
			$http.post(baseUrl + 'users/login', data).then($scope.loginCallback);
		}
	}
	
	$scope.login = function(){
	     var data = angular.toJson($scope.request);
	     
	     $http.post(baseUrl + 'users/login', data).then($scope.loginCallback);
	}
	
	$scope.loginCallback = function(response) {
		if(response.status==200){
			$scope.getUser();
			setTimeout(() => {
				window.location.href = "./myboxes.html";
				$scope.getUser();
			}, 100);
		}
	}
	
	$scope.logout = function(){
	     var data = angular.toJson($scope.request);
	     
	     $http.get(baseUrl + 'users/logout', data).then($scope.logoutCallback);
	}
	
	$scope.logoutCallback = function(response) {
		if(response.status==200){
			$scope.currentUser = null;
		}
	}
	
	$scope.deleteUser = function(delid){
    	var delurl = 'http://localhost:8080/flashcards/api/v1/users/' + delid;
    	console.log(delurl);
    	 $http({
    	    method: 'DELETE',
    	    url: 'http://localhost:8080/flashcards/api/v1/users/'+delid
    	          
    	}).then(function(){
    	    $scope.getAllUsers();
    	}); 
    	    
     }
	
	
	$scope.getCategories = function(){
		$http.get(baseUrl + 'boxes/categories').then(function successCallback(response) {
			$scope.categories = response.data;
		}, function errorCallback(response) {
		});
	}
	
	$scope.getAllBoxes = function (){
	    $http.get(baseUrl + 'boxes').then(function successCallback(response) {
	      $scope.boxes = response.data;
	      console.log(response.data);
	    }, function errorCallback(response) {
	      $scope.getAllBoxes();
	    });
	  }
	
	$scope.addBox = function(){
		var data = angular.toJson($scope.request);
		$http.post(baseUrl + 'users/me/boxes', data).then(function successCallback(response) {
			setTimeout(() => {
				window.location.href = "./myboxes.html";
				$scope.getUser();
			}, 100);
		}, function errorCallback(response) {
			alert()
		});
	}
	
	$scope.getMyBoxes = function(){
		$http.get(baseUrl + 'users/me/boxes').then(function successCallback(response) {
			$scope.myboxes = response.data;
		}, function errorCallback(response) {
			$scope.getMyBoxes();
		});
		
		console.log('XXXXXXXXXX');
	}
	
	$scope.setCurrentBoxID = function(nummer){
		console.log(nummer);
		$scope.currentboxid = nummer;
		setTimeout(() => {
			window.location.href = "./editbox.html";
		}, 100);
	}
	
	$scope.getBoxContent = function(){
		console.log('XXXXXXXXXX' + $scope.currentboxid)
		$http.get(baseUrl + '/boxes/' + $scope.currentboxid + '/flashcards').then(function successCallback(response) {
			$scope.flashcards = response.data;
		}, function errorCallback(response) {
		});

		$http.get(baseUrl + '/boxes/' + $scope.currentboxid).then(function successCallback(response) {
			$scope.boxcontent = response.data;
		}, function errorCallback(response) {
		});

	}
	
	//Try to get user on init
	$scope.getUser();
});