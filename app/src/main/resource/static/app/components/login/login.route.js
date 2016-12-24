(function() {
	"use strict";

	angular
		.module('upp-ebook.login')
		.config(config);

	config.$inject = ['$urlRouterProvider', '$stateProvider'];
	function config($urlRouterProvider, $stateProvider){
		$urlRouterProvider.otherwise("/login");

		$stateProvider
		.state("login", {
			url: '/login',
			views:{
				navbar: {
				},
				content: {
					templateUrl: "app/components/login/login.html",
					controller : 'LoginController',
					controllerAs : 'lic'
				},
				footer: {
				}
			}
		});
	}
})();
