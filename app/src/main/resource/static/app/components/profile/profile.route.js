(function(){
	"use strict";
	
	angular
	.module('upp-ebook.profile')
	.config(config);
	
	config.$inject = ['$urlRouterProvider', '$stateProvider'];
	function config($urlRouterProvider, $stateProvider){
		$stateProvider
		.state("profile", {
			url: '/profile',
			views:{
				navbar: {
					templateUrl: "app/components/search/navbar.html",
					controller : 'SearchController',
					controllerAs : 'sec'
				},
				content: {
					templateUrl: "app/components/profile/content.html",
					controller : 'ProfileController',
					controllerAs : 'prc'
				},
				footer: {
				}
			}
		});
	};
})()