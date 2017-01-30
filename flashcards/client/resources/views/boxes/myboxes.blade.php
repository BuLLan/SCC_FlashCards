@extends('layouts.default') 

@section('sidebar')
	@include('boxes.sidebar')
@endsection

@section('content')
@verbatim
	 <div class="container-fluid">
              <h1>Meine Boxen:</h1>
                <div class='filter'>
                  <p>Filtern nach Kategorien:</p>
                  <select name="langOpt[]" multiple id="langOpt">
                    <option value="1">Cat1</option>
                    <option value="2">Cat2</option>
                    <option value="3">Cat3</option>
                    <option value="4">Cat4</option>
                    <option value="5">Cat5</option>
                  </select>
                    <script src="/js/jquery.multiselect.js"></script>
                    <script>
                    $('#langOpt').multiselect({
                        columns: 1,
                        placeholder: 'Wähle Kategorie aus',
                        search: true,
                        selectAll: true
                    });
                    </script>
                     <p>Nach Tags suchen:</p> 
                      <button class='btn btn-success' data-toggle="collapse" data-target="#tar">Suchen</button>
                  <div id='tar' class='collapse'>
                  <p><input id="tags_1" type="text" class="tags" value="" /></p>
                </div>
                </div>
                <br/>
                 <div class="row">
                    <div ng-repeat="box in myboxes" class="loginbox col-lg-3 kachel">
                        <h3>{{box.title}}</h3>
                        <hr/>
                        <ul>
                          <li ng-repeat="tag in box.tags">Informatik</li>
                        </ul>
                        <p>{{box.description}}</p>
                        <div class='row box-config'>
                          <div class='col-md-4'><a href="start.html"><icon class='glyphicon glyphicon-user'></icon> starten</a></div>
                          <div class='col-md-4'><a href="/editbox/{{box.id}}"><icon class='glyphicon glyphicon-user'></icon> bearbeiten</a></div>
                          <div class='col-md-4'><a><icon class='glyphicon glyphicon-user'></icon> löschen</a></div>
                      </div>
                    </div>
                </div>

            </div>
@endverbatim
@endsection

@section('body-data')
ng-init="getMyBoxes()"
@endsection