(function(){
	'use strict';

	angular
		.module('upp-ebook.search')
		.controller('SearchController', SearchController);
	
	SearchController.$inject = ['$scope', '$http', '$state'];
	function SearchController($scope, $http, $state) {
		
		var sec = this;
		sec.books = null;
		
		sec.rola = localStorage.getItem('rola');
		
		if(localStorage.getItem('key') === null) {
			$state.go('login');
		}

		$http.defaults.headers.common.Authorization = 'Bearer '	+ localStorage.getItem('key');
		sec.token = localStorage.getItem('key');
		sec.category = JSON.parse(localStorage.getItem('category'));
		
		sec.search = Search;
		sec.download = Download;
		
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
			} else if(((sec.titleSearch != "" && sec.titleSearch != null && sec.titleSearch != undefined) && (sec.titleOperator == "" || sec.titleOperator == null || sec.titleOperator != undefined)) 
					|| ((sec.titleSearch == "" && sec.titleSearch == null && sec.titleSearch == undefined) && (sec.titleOperator != "" && sec.titleOperator != null && sec.titleOperator != undefined))) {
				alert("Niste dobro popunili polje naslov, vrednost i operator moraju biti popunjeni")
			}
			
			if(sec.authorSearch != "" && sec.authorSearch != null && sec.authorSearch != undefined && sec.authorOperator != "" && sec.authorOperator != null && sec.authorOperator != undefined){
				var authorSearchDto = {
						"field":"author",
						"value":sec.authorSearch,
						"operator":sec.authorOperator
				}
				SearchDto.push(authorSearchDto);
			} else if(((sec.authorSearch != "" && sec.authorSearch != null && sec.authorSearch != undefined) && (sec.authorOperator == "" || sec.authorOperator == null || sec.authorOperator != undefined)) 
					|| ((sec.authorSearch == "" && sec.authorSearch == null && sec.authorSearch == undefined) && (sec.authorOperator != "" && sec.authorOperator != null && sec.authorOperator != undefined))) {
				alert("Niste dobro popunili polje autor, vrednost i operator moraju biti popunjeni")
			}
			
			if(sec.keywordsSearch != "" && sec.keywordsSearch != null && sec.keywordsSearch != undefined && sec.keywordsOperator != "" && sec.keywordsOperator != null && sec.keywordsOperator != undefined){
				var keywordsSearchDto = {
						"field":"keywords",
						"value":sec.keywordsSearch,
						"operator":sec.keywordsOperator
				}
				SearchDto.push(keywordsSearchDto);
			} else if(((sec.keywordsSearch != "" && sec.keywordsSearch != null && sec.keywordsSearch != undefined) && (sec.keywordsOperator == "" || sec.keywordsOperator == null || sec.keywordsOperator != undefined)) 
					|| ((sec.keywordsSearch == "" && sec.keywordsSearch == null && sec.keywordsSearch == undefined) && (sec.keywordsSearch != undefined && sec.keywordsOperator != "" && sec.keywordsOperator != null && sec.keywordsOperator != undefined))) {
				alert("Niste dobro popunili polje ključne reči, vrednost i operator moraju biti popunjeni")
			}
			
			if(sec.languageSearch != "" && sec.languageSearch != null && sec.languageSearch != undefined && sec.languageOperator != "" && sec.languageOperator != null && sec.languageOperator != undefined){
				var languageSearchDto = {
						"field":"language",
						"value":sec.languageSearch,
						"operator":sec.languageOperator
				}
				SearchDto.push(languageSearchDto);
			} else if(((sec.languageSearch != "" && sec.languageSearch != null && sec.languageSearch != undefined) && (sec.languageOperator == "" || sec.languageOperator == null || sec.languageOperator != undefined)) 
					|| ((sec.languageSearch == "" && sec.languageSearch == null && sec.languageSearch == undefined) && (sec.languageOperator != "" && sec.languageOperator != null && sec.languageOperator != undefined))) {
				alert("Niste dobro popunili polje jezik, vrednost i operator moraju biti popunjeni")
			}
			
			if(sec.contentSearch != "" && sec.contentSearch != null && sec.contentSearch != undefined && sec.contentOperator != "" && sec.contentOperator != null && sec.contentOperator != undefined){
				var contentSearchDto = {
						"field":"content",
						"value":sec.contentSearch,
						"operator":sec.contentOperator
				}
				SearchDto.push(contentSearchDto);
			} else if(((sec.contentSearch != "" && sec.contentSearch != null && sec.contentSearch != undefined) && (sec.contentOperator == "" || sec.contentOperator == null || sec.contentOperator != undefined)) 
					|| ((sec.contentSearch == "" && sec.contentSearch == null && sec.contentSearch == undefined) && (sec.contentOperator != "" && sec.contentOperator != null && sec.contentOperator != undefined))) {
				alert("Niste dobro popunili polje sadržaj, vrednost i operator moraju biti popunjeni")
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
	}
})();