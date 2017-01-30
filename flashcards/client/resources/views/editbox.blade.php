@extends('layouts.default') 

@section('sidebar')
<div ng-show="currentUser">
	<div id="sidebar-wrapper">
		<ul class="sidebar-nav nav">
			<li class="activ"><a href="myboxes.html">Meine Boxen</a></li>
			<li><a href="allboxes.html">Alle Boxen</a></li>
			<li><a href="newbox.html">Box anlegen</a></li>
		</ul>
	</div>
	<button id='menu-toggle' type="button" class="btn btn-circle">
		<i id='side' class="glyphicon glyphicon-chevron-left"></i>
	</button>
</div>
@endsection

@section('content')
@verbatim
	<div class="container-fluid">
                <div class="row">
                    <div class="loginbox col-lg-5 kachel">
                        <h1>{{boxcontent.title}}</h1>
                        <hr/>
                        <h3>Beschreibung:</h3>
                        <p>{{boxcontent.description}}</p>
                        <h3>Tags:</h3>
						<p>{{boxcontent.tags}}</p>
                        
                        <icon class='glyphicon glyphicon-user'></icon><icon class='glyphicon glyphicon-user'></icon><icon class='glyphicon glyphicon-user'></icon>
                    </div>
                    <div class="loginbox col-lg-5 kachel">
                        <div class="col-lg-6 karte">
                           	<div class="karteninfo" ng-repeat="card in flashcards">
                                <h3>{{$index + 1}}</h3>
                                <hr/>
                                <p>{{card.frontpage.content}}</p>
                                <div class='row box-config'>
                                  <div class='col-xs-6'><a href="editcard.html"><icon class='glyphicon glyphicon-user'></icon> bearbeiten</a></div>
                                  <div class='col-xs-6'><a><icon class='glyphicon glyphicon-user'></icon> löschen</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
@endverbatim
@endsection