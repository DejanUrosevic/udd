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
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
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
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
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
		.state("updateCategory", {
			url: '/category/:id',
			views:{
				navbar: {
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
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
		.state("categoryBooks", {
			url: '/category/:categoryId/books',
			views:{
				navbar: {
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
				},
				content: {
					templateUrl: "app/components/category/category-books.html",
					controller : 'CategoryController',
					controllerAs : 'cac'
				},
				footer: {
				}
			}
		});
	};
})()