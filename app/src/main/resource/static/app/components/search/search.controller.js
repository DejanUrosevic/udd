(function(){
	'use strict';

	angular
		.module('upp-ebook.search')
		.controller('SearchController', SearchController);
	
	SearchController.$inject = ['$scope', '$http', '$state', '$sce'];
	function SearchController($scope, $http, $state, $sce) {
		
		var sec = this;
		sec.books = null;
		
		sec.rola = localStorage.getItem('rola');
		
		if(localStorage.getItem('key') === null) {
			$state.go('login');
		}

		$http.defaults.headers.common.Authorization = 'Bearer '	+ localStorage.getItem('key');
		sec.token = localStorage.getItem('key');
		
		sec.search = Search;
		
		function Search(){
			var searchUrl = "http://localhost:8080/book/search";
			var SearchDto = [];
			
			if(sec.titleSearch != "" && sec.titleSearch != null && sec.titleSearch != undefined && sec.titleOperator != "" && sec.titleOperator != null && sec.titleOperator != undefined){
				var titleSearchDto = {
						"field":"title",
						"value":sec.titleSearch,
						"operator":sec.titleOperator
				}
				SearchDto.push(titleSearchDto);
			}
			
			if(sec.authorSearch != "" && sec.authorSearch != null && sec.authorSearch != undefined && sec.authorOperator != "" && sec.authorOperator != null && sec.authorOperator != undefined){
				var authorSearchDto = {
						"field":"author",
						"value":sec.authorSearch,
						"operator":sec.authorOperator
				}
				SearchDto.push(authorSearchDto);
			}
			
			if(sec.keywordsSearch != "" && sec.keywordsSearch != null && sec.keywordsSearch != undefined && sec.keywordsOperator != "" && sec.keywordsOperator != null && sec.keywordsOperator != undefined){
				var keywordsSearchDto = {
						"field":"keywords",
						"value":sec.keywordsSearch,
						"operator":sec.keywordsOperator
				}
				SearchDto.push(keywordsSearchDto);
			}
			
			if(sec.languageSearch != "" && sec.languageSearch != null && sec.languageSearch != undefined && sec.languageOperator != "" && sec.languageOperator != null && sec.languageOperator != undefined){
				var languageSearchDto = {
						"field":"language",
						"value":sec.languageSearch,
						"operator":sec.languageOperator
				}
				SearchDto.push(languageSearchDto);
			}
			
			if(sec.contentSearch != "" && sec.contentSearch != null && sec.contentSearch != undefined && sec.contentOperator != "" && sec.contentOperator != null && sec.contentOperator != undefined){
				var contentSearchDto = {
						"field":"content",
						"value":sec.contentSearch,
						"operator":sec.contentOperator
				}
				SearchDto.push(contentSearchDto);
			}
			
			$http.post(searchUrl, SearchDto)
	        .then(function(data){
	        	sec.books = data.data;
	        	for(var i =0; i < sec.books.length; i++){
	        		sec.books[i].highlight = $sec.trustAsHtml(sec.books[i].highlight);
	        	}
	        })
	        .catch(function(){
	        
	        });
		}
	}
})();