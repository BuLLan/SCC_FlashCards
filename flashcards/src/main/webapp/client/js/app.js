var app = angular.module('myApp', []);
app.controller('flashCardsCtrl', function($scope, $http, $sce) {
	$scope.getAllUsers = function(){
		$http({
			  method: 'GET',
			  url: 'http://localhost:8080/flashcards/api/v1/users'

		}).then(function successCallback(response) {
			$scope.users = response.data;
			console.log(response.data);

		}, function errorCallback(response) {
		});
	};
	
	$scope.addNewUser = function(){
		console.log('XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX');
   	 	console.log($scope.firstname + ',' + $scope.lastname); 
		
	   	 var fd = new FormData();
	     fd.append('firstname', $scope.firstname);
	     fd.append('lastname', $scope.lastname);
	     fd.append('email', $scope.email);
	     fd.append('password', $scope.password);
	     console.log(fd);
	     

	     var dat = $.param({
	         firstname: $scope.firstname,
	         lastname: $scope.lastname,
	         email: $scope.email,
	         password: $scope.password
	     });

	     
	     $.post('http://localhost:8080/flashcards/api/v1/users'  ,dat).then(function () {
	        $scope.getAllUsers();
	        $('#newUserForm input').val('');
	      }, function errorCallback(response) {
	    	console.log(response.data);
		});
		
	}
	
	$scope.login = function(){
	     
	     var dat = $.param({
	         email: $scope.email,
	         password: $scope.password
	     });

	     $.post('http://localhost:8080/flashcards/api/v1/users'  ,dat);

		
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
	
	
	angular.element(document.getElementById('ctrl')).scope().getAllUsers();
});