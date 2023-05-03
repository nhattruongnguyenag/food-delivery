<?php

namespace App\Http\Controllers;

use App\Models\Notifycation;
use App\Models\Role;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Crypt;
use Illuminate\Support\Facades\Hash;
use Symfony\Component\HttpFoundation\Test\Constraint\RequestAttributeValueSame;

class UserController extends Controller
{
    public function addOrEditUserAPI(Request $request)
    {
        $result = '';
        $result = WidgetController::checkValidateDataUser($request);
        if ($result != null && isset($result)) {
            if (isset($request->id)) {
                if (isset($request->roles)) {
                    $user = User::find($request->id);
                    WidgetController::setDataToUser($result, $user);
                    $user->save();
                    WidgetController::attachToRoleUserTable($request->roles, $user);
                    return response($user, 201);
                }
            } else {
                if (isset($request->roles)) {
                    $user = new User();
                    WidgetController::setDataToUser($result, $user);
                    $user->save();
                    WidgetController::attachToRoleUserTable($request->roles, $user);
                    return response($user, 201);
                }
            }
        }
        return response($result, 400);
    }

    public function getUsersAPI(Request $request)
    {
        $result = '';
        //get user by notification
        if($request->query('notifycationId') != null){
            $notifycation = Notifycation::find($request->query('notifycationId'));
            if ($notifycation != null) {
                $result = $notifycation->user();
                if ($result != null) {
                    return response($result, 200);
                }
            }
            return response($result, 400);
        }

        if ($request->query('roleId') == null) {
            //get all user
            $result = User::all();
            if ($result != null) {
                return response($result, 200);
            }
            return response($result, 400);
        } else {
            //get users by role 
            $role = Role::find($request->query('roleId'));
            if ($role != null) {
                $result = $role->users();
                if ($result != null) {
                    return response($result, 200);
                }
            }
            return response($result, 400);
        }
    }

    public function getUserByIdAPI(Request $request)
    {
        $result = '';
        $result = User::find($request->id);
        if ($result != null) {
            $result->passwordUnhide = Crypt::decrypt($result->password);
            return response($result, 200);
        }
        return response($result, 400);
    }

    public function deleteUserAPI(Request $request)
    {
        $result = null;
        if (isset($request->id)) {
            $user = User::find($request->id);
            if ($user != null) {
                $result = $user;
                $user->delete();
                return response($result, 200);
            } else {
                return response($result, 400);
            }
        }
        return response($result, 400);
    }
}
