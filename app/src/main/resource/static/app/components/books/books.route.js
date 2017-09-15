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
					templateUrl: "app/components/search/navbar.html",
					controller : 'SearchController',
					controllerAs : 'sec'
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
					templateUrl: "app/components/search/navbar.html",
					controller : 'SearchController',
					controllerAs : 'sec'
				},
				content: {
					templateUrl: "app/components/books/new-book.html",
					controller : 'BooksController',
					controllerAs : 'boc'
				},
				footer: {
				}
			}
		});
	};
})()