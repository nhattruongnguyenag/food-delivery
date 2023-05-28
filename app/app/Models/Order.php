<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Order extends Model
{
    use HasFactory;

    protected $table = 'orders';
    protected $id = 'id';
    public $incrementing = true;
    public $keyType = 'integer';
    public $timestamps = true;

    public function products(){
        return $this->belongsToMany(Product::class , 'order_product' , 'order_id' , 'product_id');
    }
}
