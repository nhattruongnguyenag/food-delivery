<?php

namespace App\Models;

class MyRespone
{
    public static function returnResponeSuccess($data, $msg)
    {
        return response()->json([
            'data' => $data,
            'message' => $msg,
            'status' => 200
        ], 200);
    }

    public static function returnResponeCreatedSuccess($data, $msg)
    {
        return response()->json([
            'data' => $data,
            'message' => $msg,
            'status' => 201
        ], 201);
    }

    public static function returnResponeBadRequest($data, $msg)
    {
        return response()->json([
            'data' => $data,
            'message' => $msg,
            'status' => 400
        ], 400);
    }

    public static function returnResponeError($data, $msg)
    {
        return response()->json([
            'data' => $data,
            'message' => $msg,
            'status' => 401
        ], 401);
    }

    public static function returnResponeNotFound($data, $msg)
    {
        return response()->json([
            'data' => $data,
            'message' => $msg,
            'status' => 404
        ], 404);
    }

    public static function returnResponeErrorServer($data, $msg)
    {
        return response()->json([
            'data' => $data,
            'message' => $msg,
            'status' => 500
        ], 500);
    }
}
