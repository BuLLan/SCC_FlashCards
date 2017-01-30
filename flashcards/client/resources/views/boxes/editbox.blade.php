@extends('layouts.default') 

@section('sidebar')
@include('boxes.sidebar') 
@endsection 

@section('content')
<script>
	var boxId = {{ $boxId }};
</script>
@verbatim
<div class="container-fluid" ng-init="getBoxContent()">
	<div class="row">
		<div class="loginbox col-lg-10 kachel">
			<h1>{{boxcontent.title}}</h1>
			<hr />
			<h3>Beschreibung:</h3>
			<p>{{boxcontent.description}}</p>
			<h3>Tags:</h3>
			<ul>
				<li ng-repeat='tag in boxcontent.tags'>{{tag}}</li>
			</ul>
		</div>
		<div class="loginbox col-lg-10 kachel">
			<div class="col-lg-4 karte" ng-repeat="card in flashcards">
				<div class="karteninfo2">
					<p>#{{$index + 1}}</p>
					<h5>{{card.frontpage.content}}</h5>
					<div class='row box-config aktionen'>
						<div class='col-xs-6'>
							<a class="aktionen" href="editcard"><icon class='glyphicon glyphicon-pencil'></icon>
								<br/>bearbeiten</a>
						</div>
						<div class='col-xs-6'>
							<a class="aktionen" ><icon class='glyphicon glyphicon-remove'></icon><br/>löschen</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
@endverbatim 
@endsection
