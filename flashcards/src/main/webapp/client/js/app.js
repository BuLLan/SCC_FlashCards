var baseUrl = "http://localhost:8080/flashcards/api/v1/";

var app = angular.module('flashcardApp', []);

app.controller('flashCardsCtrl', function($scope, $http, $sce) {
	$scope.currentUser;
	$scope.showLogin = false;
	
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
	
	//Try to get user on init
	$scope.getUser();
});