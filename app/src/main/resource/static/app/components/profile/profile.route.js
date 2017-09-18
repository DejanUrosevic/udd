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
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
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