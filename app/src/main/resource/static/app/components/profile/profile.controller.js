(function(){
	'use strict';

	angular
		.module('upp-ebook.profile')
		.controller('ProfileController', ProfileController);
	
	ProfileController.$inject = ['$scope', '$http', '$state'];
	function ProfileController($scope, $http, $state) {
		
		var prc = this;
		prc.changeData = ChangeData;
		
		if(localStorage.getItem('key') === null) {
			$state.go('login');
		}
		
		$http.defaults.headers.common.Authorization = 'Bearer '	+ localStorage.getItem('key');
		prc.token = localStorage.getItem('key');
		
		$http.get('http://localhost:8080/user/')
		.then(function(data){
			prc.user = data.data;
		})
		
		function ChangeData(){
			if(prc.user.newPass === null || prc.user.newPass === '' || prc.user.newPass === undefined ||
					prc.user.repeatPass === null || prc.user.repeatPass === '' || prc.user.repeatPass === undefined){
				$http.put('http://localhost:8080/user/update', {username: prc.user.username,
																firstname: prc.user.firstname,
																lastname: prc.user.lastname,
																newPass: '',
																repeatPass: ''})
				.then(function(data){
					prc.user = data.data;
				});
			}
			
			if((prc.user.newPass !== null || prc.user.newPass !== '' || prc.user.newPass !== undefined ||
					prc.user.repeatPass !== null || prc.user.repeatPass !== '' || prc.user.repeatPass !== undefined) 
					&& (prc.user.newPass === prc.user.repeatPass)){
				$http.put('http://localhost:8080/user/update', {username: prc.user.username,
																firstname: prc.user.firstname,
																lastname: prc.user.lastname,
																newPass: prc.user.newPass,
																repeatPass: prc.user.repeatPass})
					.then(function(data){
						prc.user = data.data;
					});
			}else{
				alert("Šifre vam se ne podudaraju");
			}
			
			
		};
	};
})();