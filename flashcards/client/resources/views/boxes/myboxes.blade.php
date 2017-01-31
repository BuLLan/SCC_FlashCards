@extends('layouts.default')

@section('sidebar')
    @include('boxes.sidebar')
@endsection

@section('content')
    <div class="container-fluid">
        <h1>Meine Boxen:</h1>
        <div class='filter'>
            <label>Filtern: <input ng-model="searchText"></label>
        </div>
    </div>
    <br/>
    <div class="row">
        <div class="kachel col-lg-3" ng-repeat="box in myboxes | filter:searchText">
            @include('boxes.box-element' , ['box' => 'box'])
        </div>
    </div>
@endsection

@section('content-init')
    ng-init="getMyBoxes()"
@endsection