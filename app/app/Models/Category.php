<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Category extends Model
{
    use HasFactory;
    protected $table = 'categories';
    protected $id = 'id';
    public $incrementing = true;
    public $keyType = 'integer';
    public $timestamps = true;

    public function products()
    {
        return $this->belongsToMany(Product::class)->get();
    }
}
