<?php

namespace App\Http\Controllers;

use App\Http\Resources\OrderResource;
use App\Models\Cart;
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
                    $resource = new OrderResource(Order::all()->sortDesc());
                    $result = json_decode($resource->toJson(), true);
                    if ($result != null) {
                        return response($result, 200);
                    }
                    return response($result, 400);
                } else {
                    $resource = new OrderResource(Order::where('status', $request->query('status'))->get()->sortDesc());
                    $result = json_decode($resource->toJson(), true);
                    if ($result != null) {
                        return response($result, 200);
                    }
                    return response($result, 400);
                }
            } else {
                $user = User::find($request->query('shipperId'));
                if ($user != null) {
                    $resource = new OrderResource($user->ordersByShipper()->sortDesc());
                    $result = json_decode($resource->toJson(), true);
                    if ($result != null) {
                        return response($result, 200);
                    }
                }
                return response($result, 400);
            }
        } else {
            $user = User::find($request->query('userId'));
            if ($user != null) {
                $resource = new OrderResource($user->ordersByUser()->sortDesc());
                $result = json_decode($resource->toJson(), true);
                if ($result != null) {
                    return response($result, 200);
                }
            }
            return response($result, 400);
        }
    }

    public function updateOrderAPI(Request $request)
    {
        $result = '';
        if (isset($request->id)) {
            $order = Order::where('id', $request->id)->first();
            if ($order != null) {
                //update shipper
                if (isset($request->shipper_id)) {
                    $shipper = User::find($request->shipper_id);
                    if ($shipper != null) {
                        $order->shipper_id = $request->shipper_id;
                    } else {
                        return response([
                            "msg" => "Khong tim thay thong tin shipper"
                        ], 400);
                    }
                }

                //update status
                if (isset($request->status)) {
                    $order->status = $request->status;
                }

                $order->save();
                $resource = new OrderResource($order);
                $result = json_decode($resource->toJson(), true);
                return response($result[0], 200);
            } else {
                return response([
                    "msg" => "Khong ton tai order"
                ], 400);
            }
        } else {
            return response([
                "msg" => "Ban co the sai key hoac khong ton tai shipper hoac status"
            ], 400);
        }
    }

    public function addOrderAPI(Request $request)
    {
        $result = '';
        $result = WidgetController::checkValidateDataOrder($request);
        if ($result != null && isset($result)) {
            $order = new Order();
            WidgetController::setDataToOrder($result, $order);
            if ($order->total == 0) {
                $order->delete();
                Cart::where('user_id', '=', $order->user_id)->delete();
                return response([
                    "msg" => "gio hang trong"
                ], 400);
            }
            $order->save();
            WidgetController::attachToOrderProductTable($order);
            $resource = new OrderResource(Order::where('id', $order->id)->get());
            $result = json_decode($resource->toJson(), true);

            Cart::where('user_id', '=', $order->user_id)->delete();
            return response($resource, 201);
        }
    }

    public function deleteOrderAPI(Request $request)
    {
        $result = null;
        if (isset($request->id)) {
            $order = Order::find($request->id);
            if ($order != null) {
                $result = $order;
                $order->delete();
                return response($result, 200);
            } else {
                return response($result, 400);
            }
        }
        return response($result, 400);
    }
}
