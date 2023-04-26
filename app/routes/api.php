<?php

use App\Http\Controllers\ProductController;
use App\Http\Controllers\WidgetController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

/*
*Products API routes
*/
Route::get('products', [ProductController::class,'getProductsAPI']);
Route::post('products', [ProductController::class,'addOrEditProductAPI']);
Route::get('products/{id}', [ProductController::class,'getProductByIdAPI']);
Route::put('products', [ProductController::class,'addOrEditProductAPI']);
Route::delete('products', [ProductController::class,'deleteProductAPI']);


/*
*Categories API routes
*/

/*
*Widgets API routes
*/
Route::post('upload-image', [WidgetController::class,'uploadFileAPI']);