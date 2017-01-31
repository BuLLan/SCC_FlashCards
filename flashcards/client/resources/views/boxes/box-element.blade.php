<div class="panel panel-default dark-box">
    <div class="panel-heading">
        <div class="panel-title">
            <h3 class="panel-title">@{{box.title}}</h3>
        </div>
    </div>
    <div class="panel-body">
        <div class="box-description">
            @{{box.description}}
            <ul>
                <li ng-repeat="tag in box.tags">@{{tag}}</li>
            </ul>
        </div>
        <hr>
        <div class='row box-config'>
            @if($allow_edit)
                <div class='col-md-4'><a href="/learn/@{{ box.id }}">
                        <icon class='glyphicon glyphicon-play'></icon>
                        <br/>starten</a></div>
                <div class='col-md-4'><a href="/editbox/@{{box.id}}">
                        <icon class='glyphicon glyphicon-pencil'></icon>
                        <br/>bearbeiten</a></div>
                <div class='col-md-4'><a>
                        <icon class='glyphicon glyphicon-remove'></icon>
                        <br/>löschen</a></div>
            @endif
            @unless($allow_edit)
                <div class='col-md-12'><a href="/learn/@{{ box.id }}">
                        <icon class='glyphicon glyphicon-play'></icon>
                        starten</a></div>
            @endunless
        </div>
    </div>
</div>