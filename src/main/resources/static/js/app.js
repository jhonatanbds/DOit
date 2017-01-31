 function replacePage(file) {
       $('#toReplace').load('../templates/' + file + '.html');
    }



var app = angular.module('toDoList', []);
app.controller('ToDoController', function($scope) {

  $scope.toDos = [{
   name: "Conhecer o DOit.",
   completed: true
 }, {
   name: "Começar a usar.",
   completed: true
 }, {
   name: "Adicionar sua primeira tarefa.",
   completed: false
}];

  $scope.toDosCompleted = function() {
	var count = 0;
	for (i = $scope.toDos.length - 1; i >= 0; i--) {
		if ($scope.toDos[i].completed) {
			count++;
		}
	}
	return count;
  };

  $scope.completedPerCent = function() {
	  return (100 * ($scope.toDosCompleted()/$scope.toDos.length)).toFixed(1);
  };

  var containsToDo = function(toDo) {
    for (i = $scope.toDos.length - 1; i >= 0; i--) {
      if ($scope.toDos[i].name === toDo) {
        return true;
      }
      return false;
  }
};

  $scope.addToDo = function() {
	if (!$scope.newToDo || containsToDo($scope.newToDo))
		alert("Não é possível adicionar uma tarefa vazia ou já existente.");
	else
		$scope.toDos.push({name:$scope.newToDo, completed:false});
    $scope.newToDo = "";
  };

  $scope.clear = function() {
    $scope.toDos = [];
  };

  $scope.clearCompleted = function() {

  	for (i = $scope.toDos.length - 1; i >= 0; i--) {
  		if ($scope.toDos[i].completed) {
  			$scope.toDos.splice(i, 1);
  		}
  	}
  };

  $scope.removeToDo = function(index) {
	   $scope.toDos.splice(index, 1);
  };
});
