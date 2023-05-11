<?php

namespace App\Http\Controllers;

use App\Http\Resources\CartResource;
use App\Http\Resources\UserResource;
use App\Models\Notifycation;
use App\Models\Role;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Crypt;
use Illuminate\Support\Facades\Hash;
use \Illuminate\Support\Str;

use function PHPUnit\Framework\isEmpty;

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
                    WidgetController::setDataToUser($result, $user, false);
                    $checkEmail = User::whereEmail($user->email)->first();
                    if ($checkEmail == null) {
                        $user->save();
                        WidgetController::attachToRoleUserTable($request->roles, $user);
                        return response($user, 201);
                    }else {
                        return response([
                            'msg' => 'email da ton tai , khong the cap nhat'
                        ], 400);
                    }
                }
            } else {
                if (isset($request->roles)) {
                    $user = new User();
                    WidgetController::setDataToUser($result, $user, true);
                    $checkEmail = User::whereEmail($request->email)->first();
                    if ($checkEmail == null) {
                        $user->save();
                        WidgetController::attachToRoleUserTable($request->roles, $user);
                        return response($user, 201);
                    } else {
                        return response([
                            'msg' => 'email da ton tai , khong the them'
                        ], 400);
                    }
                }
            }
        }
        return response($result, 400);
    }

    public function getUsersAPI(Request $request)
    {
        $result = '';
        //get user by notification
        if ($request->query('notifycationId') != null) {
            $notifycation = Notifycation::find($request->query('notifycationId'));
            if ($notifycation != null) {
                $result = $notifycation->user();
                if ($result != null) {
                    $resource = new UserResource($result);
                    $result = json_decode($resource->toJson(), true);
                    return response($result, 200);
                }
            }
            return response($result, 400);
        }

        if ($request->query('roleCode') == null) {
            //get all user
            $result = User::all();
            if ($result != null) {
                $resource = new UserResource($result);
                $result = json_decode($resource->toJson(), true);
                return response($result, 200);
            }
            return response($result, 400);
        } else {
            //get users by role 
            $role = Role::where('code', $request->query('roleCode'))->first();
            if ($role != null) {
                $result = $role->users();
                if ($result->count() != 0) {
                    $resource = new UserResource($result);
                    $result = json_decode($resource->toJson(), true);
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
            $resource = new UserResource($result);
            $result = json_decode($resource->toJson(), true);
            return response($result[0], 200);
        }
        return response($result, 400);
    }

    public function deleteUserAPI(Request $request)
    {
        $result = null;
        if (isset($request->id)) {
            $user = User::find($request->id);
            if ($user != null) {
                $resource = new UserResource($result);
                $result = json_decode($resource->toJson(), true);
                $user->delete();
                return response($result[0], 200);
            } else {
                return response($result, 400);
            }
        }
        return response($result, 400);
    }

    public function changePasswordAPI(Request $request)
    {
        if (isset($request->user_id) && isset($request->new_password)) {
            $user = User::find($request->user_id);
            if ($user != null) {
                $user->password = Crypt::encrypt($request->new_password);
                return response([
                    "change" => true
                ], 200);
            }
            return response([
                "msg" => "Khong tim thay user"
            ], 400);
        }
        return response([
            "msg" => "Vui long kiem tra lai tham so truyen vao"
        ], 400);
    }

    public function loginAPI(Request $request)
    {
        $result = '';
        $user = User::whereEmail($request->email)->first();
        if($user != null){
            if(isset($request->password) && $request->password != '') {
                $check = strcmp(Crypt::decrypt($user->password), $request->password) == 0;
                if($check){
                    return response([
                        'login' => $check
                    ],200);
                }
                return response([
                    'login' => $check,
                    'msg' => "sai passsword"
                ],200);
            }
            else{
                return response([
                    "msg" => "Thong tin password co the ko tim thay"
                ], 400);
            }
        }
        return response([
            "msg" => "Khong tim thay user"
        ], 400);
    }
}
