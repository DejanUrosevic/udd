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
		lic.checkRoles = CheckRoles;
		lic.checkCategory = CheckCategory;
		
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
			$http.post('http://localhost:8080/user/login',{username: $scope.user.username, 
														   password: $scope.user.password})
			.then(function(data){
				$http.defaults.headers.common.Authorization = 'Bearer ' + data.data.token;
				
				if (localStorage) {
					localStorage.setItem('key', data.data.token);
				}
				
				lic.checkRoles();
			});
			
		}
		
		function CheckRoles(){
			if(localStorage.getItem("key") === null || localStorage.getItem("key") === undefined){
				localStorage.clear();
				$state.go('login');
			}
			
			$http.get('http://localhost:8080/api/role')
			.then(function(data){
				localStorage.setItem('rola', data.data.role);
				lic.checkCategory();
			});
		}
		
		function CheckCategory(){
			if(localStorage.getItem("key") === null || localStorage.getItem("key") === undefined){
				localStorage.clear();
				$state.go('login');
			}
			
			$http.get('http://localhost:8080/api/category')
			.then(function(data){
				if(data.data == null && data.data == undefined){
					localStorage.setItem('category', '');
				}else{
					localStorage.setItem('category', JSON.stringify(data.data));
				}
				$state.go('home');
			});
		}
	};
})()