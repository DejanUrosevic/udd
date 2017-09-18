(function(){
	"use strict";
	
	angular
	.module('upp-ebook.books')
	.config(config);
	
	config.$inject = ['$urlRouterProvider', '$stateProvider'];
	function config($urlRouterProvider, $stateProvider){
		$stateProvider
		.state("books", {
			url: '/books',
			views:{
				navbar: {
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
				},
				content: {
					templateUrl: "app/components/books/content.html",
					controller : 'BooksController',
					controllerAs : 'boc'
				},
				footer: {
				}
			}
		})
		.state("addBook",{
			url: '/books/new',
			views:{
				navbar: {
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
				},
				content: {
					templateUrl: "app/components/books/new-book.html",
					controller : 'BooksController',
					controllerAs : 'boc'
				},
				footer: {
				}
			}
		})
		.state("updateBook", {
			url: '/book/:id',
			views:{
				navbar: {
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
				},
				content: {
					templateUrl: "app/components/books/update-book.html",
					controller : 'BooksController',
					controllerAs : 'boc'
				},
				footer: {
				}
			}
		});
	};
})()