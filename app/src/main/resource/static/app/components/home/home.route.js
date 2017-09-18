(function(){
		
	"use strict";
	
	angular
	.module('upp-ebook.home')
	.config(config);
		
	config.$inject = ['$urlRouterProvider', '$stateProvider'];
	function config($urlRouterProvider, $stateProvider){
		$stateProvider
		.state("home", {
			url: '/home',
			views:{
				navbar: {
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
				},
				content: {
					templateUrl: "app/components/home/content.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
				},
				footer: {
				}
			}
		});
	};
})();