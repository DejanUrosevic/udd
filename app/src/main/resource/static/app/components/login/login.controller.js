(function(){
	'use strict';

	angular
		.module('upp-ebook.login')
		.controller('LoginController', LoginController);

	LoginController.$inject = ['$scope', '$http', '$state'];
	function LoginController($scope, $http, $state) {
	
		var lic = this;
		
		lic.singup = SingUp;
		lic.login = LogIn;
		
		function SingUp(){
			$http.post('http://localhost:8080/user/singup',{firstname: $scope.user.username,
															lastname: $scope.user.lastname,
															username: $scope.user.username, 
															password: $scope.user.password,
															email: $scope.user.email})
			.then(function(data){
				if(data.status == 200){
					console.log('sve je ok');
				} else {
					console.log('Korisnik sa tim username-om vec postoji u baze');
				}
			})
			.error(function(data, status) {
				console.error('Repos error', status, data);
			});
		}
		
		function LogIn(){
			alert("user " + $scope.user.username + " pass " + $scope.user.password);
			$http.post('http://localhost:8080/user/login',{username: $scope.user.username, 
														   password: $scope.user.password})
			.then(function(data){
				if(data.status == 200){
					$state.go('home');
				} else {
					console.log('Korisnik ne postoji');
				}
			})
			.error(function(data, status) {
				console.error('Repos error', status, data);
			});
		}
	};
})()