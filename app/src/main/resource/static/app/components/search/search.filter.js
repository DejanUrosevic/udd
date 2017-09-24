(function(){
	"use strict";
	
	angular
	.module('upp-ebook.search')
	.filter('trustAsHTML', ['$sce', function($sce){
		return function(text) {
			return $sce.trustAsHtml(text);
		};
	}]);
})()