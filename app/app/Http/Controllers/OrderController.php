<?php

namespace App\Http\Controllers;

use App\Http\Resources\OrderResource;
use App\Models\Order;
use App\Models\User;
use Illuminate\Http\Request;

class OrderController extends Controller
{
    public function getOrdersAPI(Request $request)
    {
        $result = '';
        if ($request->query('userId') == null) {
            if ($request->query('shipperId') == null) {
                if ($request->query('status') == null) {
                    $result = new OrderResource(Order::all());
                    if ($result != null) {
                        return response($result, 200);
                    }
                    return response($result, 400);
                } else {
                    $result = new OrderResource(Order::where('status', $request->query('status'))->get());
                    if ($result != null) {
                        return response($result, 200);
                    }
                    return response($result, 400);
                }
            } else {
                $user = User::find($request->query('shipperId'));
                if ($user != null) {
                    $result = new OrderResource($user->ordersByShipper());
                    if ($result != null) {
                        return response($result, 200);
                    }
                }
                return response($result, 400);
            }
        } else {
            $user = User::find($request->query('userId'));
            if ($user != null) {
                $result = new OrderResource($user->ordersByUser());
                if ($result != null) {
                    return response($result, 200);
                }
            }
            return response($result, 400);
        }
    }

    public function updateShipperAPI(Request $request)
    {
        $result = '';
        $result = WidgetController::checkValidateDataOrder($request);
        if($result != null) {
            $order = Order::where('id',$result['order_id'])->first();
            if($order != null){
                $order->shipper_id = $result['shipper_id'];
                $order->save();
                return response($order, 200);
            }
            return response([
                "msg" => "Khong ton tai order"
            ], 400);
        }
        return response([
            "msg" => "Ban co the sai key hoac khong ton tai shipper"
        ], 400);
    } 

    public function addOrderAPI(Request $request)
    {
        
    }
}
