(function(){
	'use strict';

	angular
		.module('upp-ebook.users')
		.controller('UsersController', UsersController);
	
	UsersController.$inject = ['$scope', '$http', '$state', '$stateParams'];
	function UsersController($scope, $http, $state, $stateParams) {
		
		var usc = this;
		
		usc.rola = localStorage.getItem('rola');
		usc.user = null;
		
		if(localStorage.getItem('key') === null) {
			$state.go('login');
		}
		
		if(!angular.equals({}, $stateParams)){
			var id = $stateParams.id;
			$http.get('http://localhost:8080/user/'+id)
			.then(function(data){
				usc.user = data.data;
			});
		}
		
		$http.get('http://localhost:8080/user/all')
		.then(function(data){
			usc.users = data.data;
		});
		
		$http.get('http://localhost:8080/category/')
		.then(function(data){
			usc.categories = data.data;
		});
		
		$http.get('http://localhost:8080/user/types')
		.then(function(data){
			usc.types = data.data;
		});
		
		if(localStorage.getItem('addUserBool') !== null && localStorage.getItem('updateUserBool') !== null){
			usc.addUserBool = localStorage.getItem('addUserBool');
			usc.updateUserBool = localStorage.getItem('updateUserBool');
		}

		$http.defaults.headers.common.Authorization = 'Bearer '	+ localStorage.getItem('key');
		usc.token = localStorage.getItem('key');
		
		usc.changeUser = ChangeUser;
		usc.addUser = AddUser;
		usc.saveUser = SaveUser;
		usc.updateUser = UpdateUser;
		
		function ChangeUser(id){
			localStorage.setItem('addUserBool', false);
			localStorage.setItem('updateUserBool', true);
			$state.go('updateUser', {id:id});
		};
		
		function AddUser(){
			localStorage.setItem('addUserBool', true);
			localStorage.setItem('updateUserBool', false);
			$state.go('addUser');
		};
		
		function SaveUser(){
			if(usc.user != null && usc.user.username != "" && usc.user.username != null && usc.user.username != undefined 
					&& usc.user.type.id != "" && usc.user.type.id != null && usc.user.type.id != undefined 
					&& usc.user.password != "" && usc.user.password != null && usc.user.password != undefined ){
				$http.post('http://localhost:8080/user/save', {firstname: usc.user.firstname,
															   lastname: usc.user.lastname,
															   username: usc.user.username,
															   type: usc.user.type.id,
															   category: usc.user.category.id,
															   password: usc.user.password});

				$state.go('users');
			}else{
				alert("Korisničko ime, tip i šifra su obavezni!!!")
			}
			
		}
		
		function UpdateUser(){
			if(usc.user != null && usc.user.username != "" && usc.user.username != null && usc.user.username != undefined 
					&& usc.user.type.id != "" && usc.user.type.id != null && usc.user.type.id != undefined 
					&& usc.user.password != "" && usc.user.password != null && usc.user.password != undefined ){
				$http.post('http://localhost:8080/user/update', {id: usc.user.id,
					   firstname: usc.user.firstname,
					   lastname: usc.user.lastname,
					   username: usc.user.username,
					   type: usc.user.type.id,
					   category: usc.user.category.id,
					   password: usc.user.password});

				$state.go('users');
			}else{
				alert("Korisničko ime, tip i šifra su obavezni!!!")
			}
			
		}
	}
})();