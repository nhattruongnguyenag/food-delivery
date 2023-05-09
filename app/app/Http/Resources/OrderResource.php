<?php

namespace App\Http\Resources;

use App\Models\Order;
use App\Models\User;
use Illuminate\Http\Resources\Json\JsonResource;

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
        foreach($this->all() as $item){
            array_push($resource,[
                "id" => $item["id"],
                "user"=> User::select('id','full_name','image','email')->where('id', $item['user_id'])->first(),
                "total"=> $item['total'],
                "shipper"=> User::select('id','full_name','image','email')->where('id', $item['shipper_id'])->first(),
                "customer_phone"=>$item["customer_phone"],
                "delivery_address"=>$item["delivery_address"],
                "status"=>$item["status"],
                "created_at"=>$item["created_at"],
                "updated_at"=>$item["updated_at"],
                "products" => User::find($item['user_id'])->cartItems()
            ]);
        }
        return $resource;
    }
}
