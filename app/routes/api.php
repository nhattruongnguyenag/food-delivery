<?php

use App\Http\Controllers\CartController;
use App\Http\Controllers\CategoryController;
use App\Http\Controllers\NotifycationController;
use App\Http\Controllers\OrderController;
use App\Http\Controllers\ProductController;
use App\Http\Controllers\RoleController;
use App\Http\Controllers\UserController;
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
Route::get('products/{id}', [ProductController::class,'getProductByIdAPI']);
Route::post('products', [ProductController::class,'addOrEditProductAPI']);
Route::put('products', [ProductController::class,'addOrEditProductAPI']);
Route::delete('products', [ProductController::class,'deleteProductAPI']);


/*
*Categories API routes
*/
Route::get('categories', [CategoryController::class,'getCategoriesAPI']);
Route::get('categories/{id}', [CategoryController::class,'getCategoryByIdAPI']);
Route::post('categories', [CategoryController::class,'addOrEditCategoryAPI']);
Route::put('categories', [CategoryController::class,'addOrEditCategoryAPI']);
Route::delete('categories', [CategoryController::class,'deleteCategoryAPI']);


/*
*Users API routes 
*/
Route::get('users', [UserController::class,'getUsersAPI']);
Route::get('users/{id}', [UserController::class,'getUserByIdAPI']);
Route::post('users', [UserController::class,'addOrEditUserAPI']);
Route::put('users', [UserController::class,'addOrEditUserAPI']);
Route::delete('users', [UserController::class,'deleteUserAPI']);
Route::put('users/changePassword', [UserController::class,'changePasswordAPI']);
Route::post('users/login', [UserController::class,'loginAPI']);


/*
*Roles API routes 
*/
Route::get('roles', [RoleController::class,'getRolesAPI']);
Route::get('roles/{id}', [RoleController::class,'getRoleByIdAPI']);
Route::post('roles', [RoleController::class,'addOrEditRoleAPI']);
Route::put('roles', [RoleController::class,'addOrEditRoleAPI']);
Route::delete('roles', [RoleController::class,'deleteRoleAPI']);


/*
*Notifycations API routes 
*/
Route::get('notifycations', [NotifycationController::class,'getNotifycationsAPI']);
Route::get('notifycations/{id}', [NotifycationController::class,'getNotifycationByIdAPI']);
Route::post('notifycations', [NotifycationController::class,'addOrEditNotifycationAPI']);
Route::put('notifycations/changeStatus', [NotifycationController::class,'changeStatus']);
Route::put('notifycations', [NotifycationController::class,'addOrEditNotifycationAPI']);
Route::delete('notifycations', [NotifycationController::class,'deleteNotifycationAPI']);


/*
*Carts API routes 
*/
Route::get('carts', [CartController::class,'getCartItemAPI']);
Route::post('carts', [CartController::class,'addOrEditCartAPI']);
Route::delete('carts', [CartController::class,'deleteCartAPI']);


/*
*Orders API routes 
*/
Route::get('orders', [OrderController::class,'getOrdersAPI']);
Route::post('orders', [OrderController::class,'addOrderAPI']);
Route::put('orders', [OrderController::class,'updateOrderAPI']);
Route::delete('orders', [OrderController::class,'deleteOrderAPI']);

/*
*Widgets API routes
*/
Route::post('upload-image', [WidgetController::class,'uploadFileAPI']);