var baseUrl = "http://localhost:8080/flashcards/api/v1/";

var app = angular.module('flashcardApp', []);

app.controller('flashCardsCtrl', function($scope, $http, $sce) {
	$scope.currentUser;
	$scope.showLogin = false;
	$scope.categories;
	$scope.myboxes;
	$scope.flashcards;
	$scope.currentboxid;
	
	$scope.getUser = function () {
		$http.get(baseUrl + 'users/me', {withCredentials: true}).then($scope.getUserCallback);
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

	     $http.post(baseUrl + 'users/register', data, {withCredentials: true}).then($scope.registerCallback);
	}
	
	$scope.registerCallback = function(response) {
		if(response.status==200){
			var data = angular.toJson(
				$scope.request
			);
			$http.post(baseUrl + 'users/login', data, {withCredentials: true}).then($scope.loginCallback);
		}
	}
	
	$scope.login = function(){
	     var data = angular.toJson($scope.request);
	     
	     $http.post(baseUrl + 'users/login', data, {withCredentials: true}).then($scope.loginCallback);
	}
	
	$scope.loginCallback = function(response) {
		if(response.status==200){
			$scope.getUser();
			setTimeout(() => {
				window.location.href = "./myboxes";
				$scope.getUser();
			}, 100);
		}
	}
	
	$scope.logout = function(){
	     var data = angular.toJson($scope.request);
	     
	     $http.get(baseUrl + 'users/logout', {withCredentials: true}).then($scope.logoutCallback);
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
    		 withCredentials: true,
    	    method: 'DELETE',
    	    url: 'http://localhost:8080/flashcards/api/v1/users/'+delid
    	          
    	}).then(function(){
    	    $scope.getAllUsers();
    	}); 
    	    
     }
	
	
	$scope.getCategories = function(){
		$http.get(baseUrl + 'boxes/categories', {withCredentials: true} ).then(function successCallback(response) {
			$scope.categories = response.data;
		}, function errorCallback(response) {
		});
	}
	
	$scope.addBox = function(){
		var data = angular.toJson($scope.request);
		$http.post(baseUrl + 'users/me/boxes', data, {withCredentials: true}).then(function successCallback(response) {
			setTimeout(() => {
				window.location.href = "./myboxes";
				$scope.getUser();
			}, 100);
		}, function errorCallback(response) {
			alert()
		});
	}
	
	$scope.getMyBoxes = function(){
		$http.get(baseUrl + 'users/me/boxes', {withCredentials: true}).then(function successCallback(response) {
			$scope.myboxes = response.data;
		}, function errorCallback(response) {
			$scope.getMyBoxes();
		});
		
		console.log('XXXXXXXXXX');
	}
	
	$scope.getCards = function(){
		$http.get(baseUrl + '/{box_id}/cards', {withCredentials: true}).then(function successCallback(response) {
			$scope.categories = response.data;
		}, function errorCallback(response) {
		});
	}
	
	$scope.setCurrentBoxID = function(id){
		$scope.currentboxid = id;
		setTimeout(() => {
			window.location.href = "./editbox";
		}, 100);
	}
	
	//Try to get user on init
	$scope.getUser();
});