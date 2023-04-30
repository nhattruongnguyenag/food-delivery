<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Test\Constraint\RequestAttributeValueSame;

class UserController extends Controller
{
    public function addOrEditUserAPI(Request $request)
    {
        $result = '';
        $result = WidgetController::checkValidateDataUser($request);
        if ($result != null && isset($result)) {
            if (isset($request->id)) {
                $user = User::find($request->id);
                WidgetController::setDataToUser($result, $user);
                $user->save();
                return response($user, 201);
            } else {
                $user = new User();
                WidgetController::setDataToUser($result, $user);
                $user->save();
                return response($user, 201);
            }
        }
        return response($result, 400);
    }

    public function getUserAPI(Request $request)
    {
        $result = '';
        $result = User::all();
        if ($result != null) {
            return response($result, 200);
        }
        return response($result, 400);
    }

    
}
