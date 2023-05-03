<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Notifycation extends Model
{
    use HasFactory;

    protected $table = 'notifycations';
    protected $id = 'id';
    public $incrementing = true;
    public $keyType = 'integer';
    public $timestamps = true;

    public function user()
    {
        return $this->belongsTo(User::class)->get();
    }
}
