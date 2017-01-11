(function(){
	"use strict";
	
	angular
	.module('upp-ebook.category')
	.config(config);
	
	config.$inject = ['$urlRouterProvider', '$stateProvider'];
	function config($urlRouterProvider, $stateProvider){
		$stateProvider
		.state("category", {
			url: '/category',
			views:{
				navbar: {
					templateUrl: "app/components/search/navbar.html",
					controller : 'SearchController',
					controllerAs : 'sec'
				},
				content: {
					templateUrl: "app/components/category/content.html",
					controller : 'CategoryController',
					controllerAs : 'cac'
				},
				footer: {
				}
			}
		})
		.state("addCategory",{
			url: '/category/new',
			views:{
				navbar: {
					templateUrl: "app/components/search/navbar.html",
					controller : 'SearchController',
					controllerAs : 'sec'
				},
				content: {
					templateUrl: "app/components/category/new-category.html",
					controller : 'CategoryController',
					controllerAs : 'cac'
				},
				footer: {
				}
			}
		})
		.state("categoryBooks",{
			url: '/category/:id/books',
			views:{
				navbar: {
					templateUrl: "app/components/search/navbar.html",
					controller : 'SearchController',
					controllerAs : 'sec'
				},
				content: {
					templateUrl: "app/components/category/content.html",
					controller : 'CategoryController',
					controllerAs : 'cac'
				},
				footer: {
				}
			}
		})
		.state("updateCategory", {
			url: '/category/:id',
			views:{
				navbar: {
					templateUrl: "app/components/search/navbar.html",
					controller : 'SearchController',
					controllerAs : 'sec'
				},
				content: {
					templateUrl: "app/components/category/new-category.html",
					controller : 'CategoryController',
					controllerAs : 'cac'
				},
				footer: {
				}
			}
		});
	};
})()