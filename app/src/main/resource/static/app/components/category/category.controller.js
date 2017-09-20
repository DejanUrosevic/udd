(function(){
	angular
	.module('upp-ebook.category')
	.controller('CategoryController', CategoryController);

	CategoryController.$inject = ['$scope', '$http', '$state', '$stateParams'];
	function CategoryController($scope, $http, $state, $stateParams) {
		
		var cac = this;
		
		if(localStorage.getItem('key') === null) {
			$state.go('login');
		}

		$http.defaults.headers.common.Authorization = 'Bearer '	+ localStorage.getItem('key');
		cac.token = localStorage.getItem('key');
		
		cac.rola = localStorage.getItem('rola');
		boc.category = JSON.parse(localStorage.getItem('category'));
		
		cac.addCategory = AddCategory;
		cac.books = Books;
		cac.update = Update;
		cac.saveCategory = SaveCategory;
		cac.updateCategory = UpdateCategory;
		cac.updateBook = UpdateBook;
		cac.download = Download;
		
		if(!angular.equals({}, $stateParams)){
			if($stateParams.id != null && $stateParams.id != undefined){
				var id = $stateParams.id;
				$http.get('http://localhost:8080/category/'+id)
				.then(function(data){
					cac.category = data.data;
				});
			}
			
			if($stateParams.categoryId != null && $stateParams.categoryId != undefined){
				var categoryId = $stateParams.categoryId;
				$http.get('http://localhost:8080/book/search/'+categoryId+'/books')
				.then(function(data){
					cac.categoryBooks = data.data;
				});
			}
			
		}
		
		if(localStorage.getItem('addBool') !== null){
			cac.addBool = localStorage.getItem('addBool');
		}
		
		if(localStorage.getItem('updateBool') !== null){
			cac.updateBool = localStorage.getItem('updateBool');
		}
		
		$http.get('http://localhost:8080/category/')
		.then(function(data){
			cac.categories = data.data;
		});
		
		function AddCategory(){
			localStorage.setItem('addBool', true);
			localStorage.setItem('updateBool', false);
			$state.go('addCategory');
		}
		
		function Books(id){
			$state.go('categoryBooks', {categoryId:id});
		}
		
		function Update(id){
			localStorage.setItem('addBool', false);
			localStorage.setItem('updateBool', true);
			$state.go('updateCategory', {id:id});
		}
		
		function SaveCategory(){
			$http.post('http://localhost:8080/category/save', {name: cac.category.name})
			.then(function(data){
				cac.categories = data.data;
			});
			
			$state.go('category');
		}
		
		function UpdateCategory(){
			$http.post('http://localhost:8080/category/update', {id:cac.category.id,
																 name: cac.category.name})
			.then(function(data){
				cac.categories = data.data;
			});
			
			$state.go('category');
		}
		
		function UpdateBook(id){
			localStorage.setItem('addBool', false);
			localStorage.setItem('updateBool', true);
			$state.go('updateBook', {id:id});
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
})()