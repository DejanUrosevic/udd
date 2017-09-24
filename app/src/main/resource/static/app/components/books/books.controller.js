(function(){
	'use strict';

	angular
		.module('upp-ebook.books')
		.controller('BooksController', BooksController);
	
	BooksController.$inject = ['$scope', '$http', '$state', 'fileUpload', '$stateParams'];
	function BooksController($scope, $http, $state, fileUpload, $stateParams) {
		
		var boc = this;
		
		if(localStorage.getItem('key') === null) {
			$state.go('login');
		}
		
		$http.defaults.headers.common.Authorization = 'Bearer '	+ localStorage.getItem('key');
		boc.token = localStorage.getItem('key');
		boc.rola = localStorage.getItem('rola');
		boc.category = JSON.parse(localStorage.getItem('category'));
		
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
		boc.update = Update;
		boc.bookUpdate = UpdateBook;
		boc.download = Download;
		
		if(!angular.equals({}, $stateParams)){
			var id = $stateParams.id;
			$http.get('http://localhost:8080/book/'+id)
			.then(function(data){
				boc.newBook = data.data;
			});
		}
				
		$http.get('http://localhost:8080/book/all')
		.then(function(data){
			boc.books = data.data;
		});
		
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
	        	alert("Morate upload-ovati fajl knjige (mora biti pdf), popuniti naslov, kategoriju i jezik.")
	        });
			console.log(boc.newBook);
		}
	
		function Update(id){
			localStorage.setItem('addBool', false);
			localStorage.setItem('updateBool', true);
			$state.go('updateBook', {id:id});
		}
		
		function UpdateBook(){
			var uploadUrl = "http://localhost:8080/book/update";
			$http.post(uploadUrl, boc.newBook)
			.then(function(data){
				$state.go('books');
			})
			.catch(function(){
	        	alert("Morate popuniti naslov, kategoriju i jezik.")
	        });;
		}
		
		function Download(id, filename){
			var downloadUrl = "http://localhost:8080/book/download/"+id;
			$http.get(downloadUrl)
			.then(function(data){
				var anchor = angular.element('<a/>');
		        anchor.attr({
		            href: 'data:application/octet-stream;base64,' + data.data,
		            target: '_self',
		            download: filename       
		            });

		        angular.element(document.body).append(anchor);
		        anchor[0].click();
			});
		}
	};
})();