@verbatim
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<img src="img/Logo.png" height="50px" /> <a class="navbar-brand" href="#">FlashCards</a>
		</div>
		<ul class="nav navbar-nav navbar-right" ng-show="currentUser">
			<li class="active"><a href="#">Home</a></li>
			<li><a href="myboxes.html">Boxen</a></li>
			<li><a href="#">Gruppen</a></li>
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> {{currentUser.firstName}}
					{{currentUser.lastName}} <icon class='glyphicon glyphicon-user'></icon>
					<span class="caret"></span>
			</a>
				<ul class="dropdown-menu">
					<li><a href="#">Details</a></li>
					<li><a href="#" ng-click="logout()">Log-Out</a></li>
				</ul></li>
		</ul>
	</div>
</nav>
@endverbatim