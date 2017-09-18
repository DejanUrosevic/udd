(function(){
	"use strict";
	
	angular
	.module('upp-ebook.users')
	.config(config);
	
	config.$inject = ['$urlRouterProvider', '$stateProvider'];
	function config($urlRouterProvider, $stateProvider){
		$stateProvider
		.state("users", {
			url: '/users',
			views:{
				navbar: {
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
				},
				content: {
					templateUrl: "app/components/users/content.html",
					controller : 'UsersController',
					controllerAs : 'usc'
				},
				footer: {
				}
			}
		})
		.state("addUser",{
			url: '/users/new',
			views:{
				navbar: {
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
				},
				content: {
					templateUrl: "app/components/users/new-user.html",
					controller : 'UsersController',
					controllerAs : 'usc'
				},
				footer: {
				}
			}
		})
		.state("updateUser", {
			url: '/users/:id',
			views:{
				navbar: {
					templateUrl: "app/components/home/navbar.html",
					controller : 'HomeController',
					controllerAs : 'hoc'
				},
				content: {
					templateUrl: "app/components/users/new-user.html",
					controller : 'UsersController',
					controllerAs : 'usc'
				},
				footer: {
				}
			}
		});
	};
})()