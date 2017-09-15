(function(){
	'use strict';

	angular
		.module('upp-ebook.books')
		.controller('BooksController', BooksController);
	
	BooksController.$inject = ['$scope', '$http', '$state', 'fileUpload'];
	function BooksController($scope, $http, $state, fileUpload) {
		
		var boc = this;
		
		if(localStorage.getItem('key') === null) {
			$state.go('login');
		}
		
		$http.defaults.headers.common.Authorization = 'Bearer '	+ localStorage.getItem('key');
		boc.token = localStorage.getItem('key');
		boc.rola = localStorage.getItem('rola');
		
		if(localStorage.getItem('addBool') !== null){
			boc.addBool = localStorage.getItem('addBool');
		}
		
		if(localStorage.getItem('updateBool') !== null){
			boc.updateBool = localStorage.getItem('updateBool');
		}
		
		boc.newBook = null;
		boc.upload = Upload;
		boc.addBook = AddBook;
		boc.saveBook = SaveBook;
		
		$http.get('http://localhost:8080/category/')
		.then(function(data){
			boc.categories = data.data;
		});
		
		$http.get('http://localhost:8080/languages/')
		.then(function(data){
			boc.languages = data.data;
		});
		
		function AddBook(){
			localStorage.setItem('addBool', true);
			localStorage.setItem('updateBool', false);
			$state.go('addBook');
		}
		
		function Upload(){
			var file = $scope.myFile;
            
            var uploadUrl = "http://localhost:8080/book/upload";
             
			var fd = new FormData();
            fd.append('file', file);
         
            $http.post(uploadUrl, fd, {
               transformRequest: angular.identity,
               headers: {'Content-Type': undefined}
            })
            .then(function(data){
            	boc.newBook = data.data;
            })
            .catch(function(){
            
            });
		};
		
		function SaveBook(){
			var uploadUrl = "http://localhost:8080/book/save";
			$http.post(uploadUrl, boc.newBook)
	        .then(function(data){
	        	$state.go('books');
	        })
	        .catch(function(){
	        
	        });
			console.log(boc.newBook);
		}
		
		
	};
})();