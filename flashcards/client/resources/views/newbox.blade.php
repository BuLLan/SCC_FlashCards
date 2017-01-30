@extends('layouts.default') 

@section('content')
@verbatim
<div class="container-fluid">
	<div class="row">
		<div class="loginbox col-lg-10 kachel">
			<h1>Box erstellen</h1>
			<hr />
			<form class="login" ng-submit="addBox()">
				<div class="col-lg-6 karte">
					<div class="karteninfo">
						<h3>Name</h3>
						<input ng-model="request.title" type="text" placeholder="Name" />
						<h3>Beschreibung</h3>
						<textarea ng-model="request.description"></textarea>
						<h3>Öffentlich</h3>
						<input type="checkbox" ng-model="request.public">
						<button type="submit">erstellen</button>
					</div>
				</div>
				<div class="col-lg-6 karte">
					<div class="karteninfo">
						<h3>Kategorie</h3>
						<select ng-model="request.categoryId"
							ng-options="category.id as category.title for category in categories"></select>
						<h3>Tags</h3>
						<input ng-model="request.tags" id="tags" type="text" class="tags"
							value="Tag" />
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
@endverbatim
@endsection
