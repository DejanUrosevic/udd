(function(){
	'use strict';

	angular
		.module('upp-ebook.search')
		.controller('SearchController', SearchController);
	
	SearchController.$inject = ['$scope', '$http', '$state'];
	function SearchController($scope, $http, $state) {
		
		var sec = this;
		sec.search = Search;
		sec.category = Category;
		sec.profil = Profil;
		sec.users = Users;
		sec.singOut = SingOut;
		
		sec.rola = localStorage.getItem('rola');
		
		if(localStorage.getItem('key') === null) {
			$state.go('login');
		}

		$http.defaults.headers.common.Authorization = 'Bearer '	+ localStorage.getItem('key');
		sec.token = localStorage.getItem('key');
		
		function Search(){
			$state.go('home');
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
			sec.token = null;
			$http.defaults.headers.common.Authorization = '';
			localStorage.clear();
			$state.go('login');
		}
	}
})();