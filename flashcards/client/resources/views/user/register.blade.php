@extends('layouts.default')

@section('content')
@verbatim
<!-- Page Content -->
<div id="page-content-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="loginbox col-lg-4 col-lg-offset-4">
				<h1>Benutzer erstellen</h1>
				<hr />
				<form class="login" ng-submit="register()">
					<input type="text" ng-model="request.firstName" required
						placeholder="Vorname" /> <input ng-model="request.lastName"
						type="text" required placeholder="Nachname" /> <input
						ng-model="request.email" type="email" required placeholder="Email" />
					<input ng-model="request.password" type="password" required
						placeholder="Passwort" />
					<button type="submit">senden</button>
				</form>
				<p>
					Schon registriert? <a href="./index">Login</a>
				</p>
			</div>
		</div>
	</div>
</div>
@endverbatim
@endsection
