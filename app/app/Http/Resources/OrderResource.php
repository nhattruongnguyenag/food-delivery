<?php

namespace App\Http\Resources;

use App\Models\Order;
use App\Models\Product;
use App\Models\User;
use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Support\Facades\DB;

class OrderResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array|\Illuminate\Contracts\Support\Arrayable|\JsonSerializable
     */
    public function toArray($request)
    {
        $resource = [];
        foreach ($this->all() as $item) {
            array_push($resource, [
                "id" => $item["id"],
                "user" => User::select('id', 'full_name', 'image', 'email')->where('id', $item['user_id'])->first(),
                "total" => $item['total'],
                "shipper" => User::select('id', 'full_name', 'image', 'email')->where('id', $item['shipper_id'])->first(),
                "customer_phone" => $item["customer_phone"],
                "delivery_address" => $item["delivery_address"],
                "status" => $item["status"],
                "created_at" => $item["created_at"],
                "updated_at" => $item["updated_at"],
                "items" => $this->items(User::find($item['user_id']),$item["id"])
            ]);
        }
        return $resource;
    }

    public function items($user, $order_id)
    {
        $resource = [];
        $order = Order::find($order_id);
        $orderListProduct = $order->products();
        for ($i = 0; $i < $orderListProduct->count(); $i++) {
            $quantity = DB::table('order_product')->where('order_id', '=', $order_id)->get('quantity')[$i]->quantity;
            $price = DB::table('order_product')->where('order_id', '=', $order_id)->get('price')[$i]->price;
            array_push($resource, [
                'id' => DB::table('order_product')->where('order_id', '=', $order_id)->get('id')[$i]->id,
                'product' => Product::find(DB::table('order_product')->where('order_id', '=', $order_id)->get('product_id')[$i]->product_id),
                'quantity' => $quantity,
                'price' => $price,
                'sub_total' => $quantity * $price
            ]);
        }
        return $resource;
    }
}
