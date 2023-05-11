<?php

namespace App\Http\Resources;

use App\Models\User;
use Illuminate\Http\Resources\Json\JsonResource;

class UserResource extends JsonResource
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
        $count = $this->count();
        if($this->resource[0] != null){
            for ($i = 0; $i < $count; $i++) {
                array_push($resource, [
                    "id" => $this[$i]->id,
                    "full_name" => $this[$i]->full_name,
                    "image" => $this[$i]->image,
                    "email" => $this[$i]->email,
                    "status" => $this[$i]->status,
                    "created_at" => $this[$i]->created_at,
                    "updated_at" => $this[$i]->updated_at,
                    "roles"=>$this->roles($this[$i]->id)
                ]);
            }
        }
        else{
            array_push($resource, [
                "id" => $this->id,
                "full_name" => $this->full_name,
                "image" => $this->image,
                "email" => $this->email,
                "status" => $this->status,
                "created_at" => $this->created_at,
                "updated_at" => $this->updated_at,
                "roles"=> $this->roles($this->id)
            ]);
        }
        return $resource;
    }
    public function roles($id)
    {
        $result = [];
        $roles = User::find($id)->roles();
        foreach($roles as $role){
            array_push($result, $role->id);
        }
        return $result;
    }
}
