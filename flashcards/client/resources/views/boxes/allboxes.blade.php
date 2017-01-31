@extends('layouts.default')

@section('sidebar')
    @include('boxes.sidebar')
@endsection

@section('content')
    <div class="container-fluid" ng-init='getAllBoxes()'>
        <h1>Alle Boxen:</h1>
        <div class='filter'>
            <label>Filtern: <input ng-model="searchText"></label>
        </div>
        <br/>
        <div class="row">
            <div class="kachel col-lg-3" ng-repeat="box in boxes | filter:searchText">
                @include('boxes.box-element' , ['box' => 'box', 'allow_edit' => false])
            </div>
        </div>

    </div>
@endsection
