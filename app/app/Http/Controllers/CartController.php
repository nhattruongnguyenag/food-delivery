<?php

namespace App\Http\Controllers;

use App\Http\Resources\CartResource;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class CartController extends Controller
{
    /*
    * Cart methods
    */
    public function getCartItemAPI(Request $request)
    {
        $result = '';
        if ($request->query('userId') != null) {
            $user = User::find($request->query('userId'));
            if ($user != null) {
                $result = new CartResource($user);
                if ($result != null) {
                    return response($result, 200);
                }
            }
        }
        return $result;
    }

    public function addToCartAPI(Request $request)
    {
        $result = '';
        $result = WidgetController::checkValidateDataCartItem($request);
        if ($result != null && isset($result)) {
            if ($request->query('updateQuantity') != null) {
                $cartItem = DB::table('product_user')->where('user_id','=',$request->user_id)->where('product_id','=',$request->product_id);
                return response($cartItem, 201);
            } else {
                
                
                // return response($product, 201);
            }
        }
    }
}
