var app = angular.module('myApp', []);
app.controller('flashCardsCtrl', function($scope, $http, $sce) {
	$scope.getAllUsers = function(){
		$http({
			  method: 'GET',
			  url: 'http://localhost:8080/flashcards/api/users'
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
	     
	     var data = $.param({
	    	 firstname: $scope.firstname,
	    	 lastname: $scope.lastname,
	    	 email: $scope.email,
	    	 password: $scope.password
         });
	     
	    $http.post({
	    	url     : 'http://localhost:8080/flashcards/api/users?firstname='+$scope.firstname+'&lastname='+$scope.lastname+'&email='+$scope.email+'&password='+$scope.password,
	    	headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	    }).then(function () {
	    	   // ...
	    	alert("Alarm");
	    }, function errorCallback(response) {
	    	console.log(response.data);
		});
	    
	    
	    
	    
//   	 	$http({
//	          method  : 'POST',
//	          url     : 'http://localhost:8080/flashcards/api/users',
//	          headers : {'Content-Type': 'application/x-www-form-urlencoded'}, 
//	          
////	          transformRequest: function(data) {
////	              var fd = new FormData();
////	              fd.append('firstname', $scope.firstname);
////	     	      fd.append('lastname', $scope.lastname);
////	     	      fd.append('email', $scope.email);
////	     	      fd.append('password', $scope.password);
////	              return fd;//NOTICE THIS RETURN WHICH WAS MISSING
////	          },
////	          
//	          data    : fd
//	          
////	          {'firstname': $scope.firstname, 'lastname': $scope.lastname, 'email': $scope.email, 'password': $scope.email} //forms user object
//	          
//	          
//	         })
//	         .success(function(data) {
//	        	 
//	        	 
//	            if (data.errors) {
////	              // Showing errors.
////	              $scope.errorFirstName = data.errors.firstName;
////	              $scope.errorLastName = data.errors.lastName;
////	              $scope.errorLogin = data.errors.login;
////	              $scope.errorPassword = data.errors.password;
//	            } else {
//	              alert("Alarm");
//	         }
//	      });
		
		
	}
	
	
	
	angular.element(document.getElementById('ctrl')).scope().getAllUsers();
});