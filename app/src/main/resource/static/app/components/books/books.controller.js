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
		
		boc.upload = Upload;
		
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
            })
            .catch(function(){
            });
		};
		
	};
})();