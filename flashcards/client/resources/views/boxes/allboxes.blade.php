@extends('layouts.default') 

@section('sidebar')
@include('boxes.sidebar') 
@endsection 

@section('content')
@verbatim
<div class="container-fluid" ng-init='getAllBoxes()'>
              <h1>Alle Boxen:</h1>
                <div class='filter'>
                <label>Filtern: <input ng-model="searchText"></label>
                </div>
                <br/>
                 <div class="row">
                    <div class="loginbox col-lg-3 kachel" ng-repeat="box in boxes | filter:searchText">
                        <h3>{{box.title}}</h3>
                        <hr/>
                        <ul>
                          <li ng-repeat="tag in box.tags">{{tag}}</li>
                        </ul>
                        <p>{{box.description}}</p>
                        <div class='row box-config'>
                          <div class='col-md-12'><a><icon class='glyphicon glyphicon-play'></icon> starten</a></div>
                      </div>
                    </div>
                    
                </div>

            </div>
@endverbatim 
@endsection
