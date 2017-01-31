<?php

/*
 * |--------------------------------------------------------------------------
 * | Web Routes
 * |--------------------------------------------------------------------------
 * |
 * | Here is where you can register web routes for your application. These
 * | routes are loaded by the RouteServiceProvider within a group which
 * | contains the "web" middleware group. Now create something great!
 * |
 */
Route::get ( '/', function () {
	return view ( 'index' );
} );


Route::get ( '/register', function () {
	return view ( 'user.register' );
} );

Route::get ( '/myboxes', function () {
	return view ( 'boxes.myboxes' );
} );

Route::get ( '/newbox', function () {
	return view ( 'boxes.newbox' );
} );

Route::get('/newcard/{boxid}', function($boxid)
{
    //return view ('boxes.newcard' );
    //return View::make('boxes.newcard', array('boxId' => $boxid));
    return view('boxes.newcard', ['b_id' => $boxid]);

});

Route::get ( '/editbox/{boxid}', ['uses' =>'Controller@editBox']);

Route::get ( '/allboxes', function () {
  return view ( 'boxes.allboxes' );
} );