(function(){
	"use strict";
	
	angular
	.module('upp-ebook.search')
	.config(config);
	
	config.$inject = ['$urlRouterProvider', '$stateProvider'];
	function config($urlRouterProvider, $stateProvider){
		$stateProvider
		.state("home", {
			url: '/home',
			views:{
				navbar: {
					templateUrl: "app/components/search/navbar.html"
				},
				content: {
					templateUrl: "app/components/search/content.html",
					controller : 'SearchController',
					controllerAs : 'sec'
				},
				footer: {
				}
			}
		});
	};
})()