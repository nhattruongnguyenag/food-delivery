<?php

namespace App\Http\Controllers;

use App\Http\Resources\CartResource;
use App\Models\Cart;
use App\Models\CartCloudModel;
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
                $resource = new CartResource($user);
                $result = json_decode($resource->toJson(), true);
                if ($result != null) {
                    return response($result, 200);
                }
            }
        }
        return $result;
    }

    public function addOrEditCartAPI(Request $request)
    {
        $result = '';
        $result = WidgetController::checkValidateDataCartItem($request);
        if ($result != null && isset($result)) {
            $cartItem = Cart::where('user_id', '=', $request->user_id)->where('product_id', '=', $request->product_id)->get()->first();
            if ($cartItem == '') {
                //neu cart chua ton tai
                $quantity = $result['quantity'];
                DB::table('product_user')->insert([
                    'user_id' => $result['user_id'],
                    'product_id' => $result['product_id'],
                    'quantity' => $quantity
                ]);
                $cartItem = Cart::where('user_id', '=', $result['user_id'])->where('product_id', '=', $result['product_id'])->get()->first();
                return response($cartItem, 201);
            } else {
                //neu cart da ton tai
                $quantity = $cartItem->quantity + $result['quantity'];
                DB::table('product_user')->where('id', $cartItem->id)->update(array('quantity' => $quantity));
                $cartItem = Cart::where('user_id', '=', $request->user_id)->where('product_id', '=', $request->product_id)->get()->first();
                return response($cartItem, 201);
            }
        } else {
            return response([
                'msg' => "sai dinh dang du lieu dau vao"
            ], 400);
        }
    }

    public function deleteCartAPI(Request $request)
    {
        $result = '';
        if ($request->query("deleteAll") == true) {
            Cart::where('user_id', '=', $request->user_id)->delete();
            return response([
                "msg" => "Xoa tat ca cart"
            ], 200);
        } else {
            $result = Cart::where('user_id', '=', $request->user_id)->where('product_id', '=', $request->product_id)->get()->first();
            if ($result != null && isset($result)) {
                Cart::find($result->id)->delete();
                return response($result, 200);
            } else {
                return response([
                    'msg' => "khong tim thay thong tin cart"
                ], 400);
            }
        }
    }
}
