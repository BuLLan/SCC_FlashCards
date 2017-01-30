@extends('layouts.default') 

@section('sidebar')
	@include('boxes.sidebar')
@endsection

@section('content')
@verbatim
	 <div class="container-fluid">
              <h1>Meine Boxen:</h1>
                <div class='filter'>
                  <label>Filtern: <input ng-model="searchText"></label>
                </div>
                </div>
                <br/>
                 <div class="row">
                    <div ng-repeat="box in myboxes | filter:searchText" class="loginbox col-lg-3 kachel">
                        <h3>{{box.title}}</h3>
                        <hr/>
                        <ul>
                          <li ng-repeat="tag in box.tags">{{tag}}</li>
                        </ul>
                        <p>{{box.description}}</p>
                        <div class='row box-config'>
                          <div class='col-md-4'><a href="start.html"><icon class='glyphicon glyphicon-play'></icon><br/>starten</a></div>
                          <div class='col-md-4'><a href="/editbox/{{box.id}}"><icon class='glyphicon glyphicon-pencil'></icon><br/>bearbeiten</a></div>
                          <div class='col-md-4'><a><icon class='glyphicon glyphicon-remove'></icon><br/>löschen</a></div>
                      </div>
                    </div>
                </div>

            </div>
@endverbatim
@endsection

@section('body-data')
ng-init="getMyBoxes()"
@endsection