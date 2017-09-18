(function(){
	'use strict';

	angular
		.module('upp-ebook.home')
		.controller('HomeController', HomeController);
	
	HomeController.$inject = ['$scope', '$http', '$state'];
	function HomeController($scope, $http, $state) {
		var hoc = this;
		hoc.search = Search;
		hoc.category = Category;
		hoc.profil = Profil;
		hoc.users = Users;
		hoc.singOut = SingOut;
		hoc.books = Books;
		
		hoc.rola = localStorage.getItem('rola');
		
		if(localStorage.getItem('key') === null) {
			$state.go('login');
		}

		$http.defaults.headers.common.Authorization = 'Bearer '	+ localStorage.getItem('key');
		hoc.token = localStorage.getItem('key');
		
		function Search(){
			$state.go('search');
		}
		
		function Category(){
			$state.go('category');
		}
		
		function Profil(){
			$state.go('profile');
		}
		
		function Users(){
			$state.go('users');
		}
		
		function Books() {
			$state.go('books');
		}
		
		function SingOut(){
			hoc.token = null;
			$http.defaults.headers.common.Authorization = '';
			localStorage.clear();
			$state.go('login');
		}
	}
})();