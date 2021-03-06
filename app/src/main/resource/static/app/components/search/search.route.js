(function(){
	"use strict";
	
	angular
	.module('upp-ebook.search')
	.config(config);
	
	config.$inject = ['$urlRouterProvider', '$stateProvider'];
	function config($urlRouterProvider, $stateProvider){
		$stateProvider
		.state("search", {
			url: '/search',
			views:{
				navbar: {
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
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