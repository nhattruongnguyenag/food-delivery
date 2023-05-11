<?php

namespace App\Http\Resources;

use App\Models\CartItem;
use App\Models\User;
use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Support\Facades\DB;

class CartResource extends JsonResource
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
        $count = $this->cartItems()->count();
        for ($i = 0; $i < $count; $i++) {
            array_push($resource, [
                'cart_id' => DB::table('product_user')->where('user_id', '=', $this->id)->where('product_id', '=', $this->cartItems()[$i]['id'])->get('id')->first()->id,
                'product' => $this->cartItems()[$i],
                'user' => User::find($this->id),
                'quantity' => DB::table('product_user')->where('user_id', '=', $this->id)->where('product_id', '=', $this->cartItems()[$i]['id'])->get('quantity')->first()->quantity,
            ]);
        }
        return $resource;
    }
}
