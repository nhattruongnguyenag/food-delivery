<?php

namespace App\Http\Resources;

use App\Models\Order;
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
        $count = $user->cartItems()->count();
        for ($i = 0; $i < $count; $i++) {
            $quantity = DB::table('product_user')->where('user_id', '=', $user->id)->where('product_id', '=', $user->cartItems()[$i]['id'])->get('quantity')->first()->quantity;
            $price = DB::table('order_product')->where('order_id', '=', $order_id)->where('product_id', '=', $user->cartItems()[$i]['id'])->get('price')->first()->price;
            array_push($resource, [
                'cart_id' => DB::table('product_user')->where('user_id', '=', $user->id)->where('product_id', '=', $user->cartItems()[$i]['id'])->get('id')->first()->id,
                'product' => $user->cartItems()[$i],
                'quantity' => $quantity,
                'price' => $price,
                'sub_total' => $quantity * $price
            ]);
        }
        return $resource;
    }
}
