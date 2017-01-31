<!doctype html>
<html ng-app="flashcardApp">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=100%"/>
    <title>FlashCards Dashboard</title>

    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style2.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    @stack('styles')

    @section('jsHeader')
        <script
                src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script
                src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        <script
                src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular.min.js"></script>
    @show
</head>

<body ng-controller="flashCardsCtrl as appController" @section('body-init')ng-init="getUser()"@show>
@include('shared.navbar')
<div id="wrapper">

    @section('sidebar')
        <div ng-show="currentUser">
            <div id="sidebar-wrapper">
                <ul class="sidebar-nav nav">
                </ul>
            </div>
            <button id='menu-toggle' type="button" class="btn btn-circle">
                <i id='side' class="glyphicon glyphicon-chevron-left"></i>
            </button>
        </div>
    @show

    <div id="page-content-wrapper" @yield('content-init')>
        @section('content')
            Kein Inhalt
        @show
    </div>
    <!-- /#page-content-wrapper -->

</div>
@verbatim
<script>
    $("#menu-toggle").click(function (e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");

        if ($('#side').hasClass('glyphicon-chevron-left')) {
            $('#side').removeClass('glyphicon-chevron-left');
            $('#side').addClass('glyphicon-chevron-right');

        }
        else {
            $('#side').removeClass('glyphicon-chevron-right');
            $('#side').addClass('glyphicon-chevron-left');
        }
        ;
    });
</script>
@endverbatim
</body>
@section('jsFooter')
    <script src="/js/flashcardapp.js"></script>
    @stack('footerScripts')
@show
</html>
