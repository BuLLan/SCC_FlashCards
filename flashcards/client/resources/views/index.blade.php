@extends('layouts.default')

@section('content')
<div class="container-fluid">
	<div class="row">
		<div class="loginbox col-lg-4 col-lg-offset-3" ng-hide="currentUser">
			<h1>Bitte logge dich ein</h1>
			<hr />
			<form class="login" ng-submit="login()">
				<input type="email" ng-model="request.email" required
					placeholder="Email" /> <input type="password"
					ng-model="request.password" required placeholder="Passwort" />
				<button type="submit">login</button>
			</form>
			<p>
				Noch keinen Account? Jetzt <a href="./register.html">registrieren!</a>
			</p>
		</div>
	</div>
</div>
@endsection
