@extends('layouts.default')

@push('styles')
<link rel="stylesheet" type="text/css" href="/css/style3.css">
@endpush

@section('sidebar')
    @include('boxes.sidebar')
@endsection

@section('content')
    <script>
        var boxId = {{$boxId}};
    </script>
    <input type="hidden" name="boxId" id="boxId" ng-model="boxId"/>
    <input type="hidden" name="boxId" id="flashcardId" ng-model="flashcardId"/>
    <div id='flash' class='row' ng-show="currentCard">
        <div class='col-sm-6 col-sm-offset-3'>
            <div class="hover panel">
                <div id="frontcard" class="front">
                    <div class="pad">
                        <div class="flashhead">Frage:</div>
                        <div class="flashbody">
                            <div class="text">@{{currentCard.frontpage.content}}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="back">
                    <div class="pad">
                        <div class="flashhead">Antwort:</div>
                        <div class="flashbody">
                            <div class="text">
                                @{{currentCard.backpage.content}}
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <div id="button" class="buttons">
                <h3>Haben Sie die Antwort gewusst?</h3>
                <button type="button" class="btn btn-success btn-circle btn-xl" ng-click="scoreCard(true)">
                    <i class="glyphicon glyphicon-ok"></i>
                </button>
                <button type="button" class="btn btn-danger btn-circle btn-xl" ng-click="scoreCard(false)">
                    <i class="glyphicon glyphicon-remove"></i>
                </button>
            </div>

        </div>
    </div>
    <div class="container-fluid" ng-hide="currentCard">
        <div class="row">
            <div class="loginbox col-lg-4 col-lg-offset-3">
                <h1>Keine Karten mehr für heute</h1>
                <hr/>
                <p>
                    Du hast bereits alle Karten gelernt. Schaue morgen wieder vorbei.
                </p>
            </div>
        </div>
    </div>
@endsection

@section('content-init')
    ng-init="getCurrentSession()"
@endsection

@push('footerScripts')
<script src="/js/flashcard.js"></script>
@endpush