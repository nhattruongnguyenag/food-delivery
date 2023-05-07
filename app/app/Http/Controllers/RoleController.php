<?php

namespace App\Http\Controllers;

use App\Models\Role;
use App\Models\User;
use Illuminate\Http\Request;

class RoleController extends Controller
{
    public function getRolesAPI(Request $request)
    {
        $result = '';
        if ($request->query('userRole') == null) {
            $result = Role::all();
            if ($result != null) {
                return response($result, 200);
            }
            return response($result, 400);
        }else{
            $user = User::find($request->query('userRole'));
            if ($user != null) {
                $result = $user->roles();
                if ($result != null) {
                    return response($result, 200);
                }
            }
            return response($result, 400);
        }
    }

    public function getRoleByIdAPI(Request $request)
    {
        $result = '';
        $result = Role::find($request->id);
        if ($result != null) {
            return response($result, 200);
        }
        return response($result, 400);
    }

    public function deleteRoleAPI(Request $request)
    {
        $result = null;
        if (isset($request->id)) {
            $role = Role::find($request->id);
            if ($role != null) {
                $result = $role;
                $role->delete();
                return response($result, 200);
            } else {
                return response($result, 400);
            }
        }
        return response($result, 400);
    }

    public function addOrEditRoleAPI(Request $request)
    {
        $result = '';
        $result = WidgetController::checkValidateDataRole($request);
        if ($result != null && isset($result)) {
            if (isset($request->id)) {
                $role = Role::find($request->id);
                WidgetController::setDataToRole($result, $role);
                $role->save();
                return response($role, 201);
            } else {
                $role = new Role();
                WidgetController::setDataToRole($result, $role);
                $role->save();
                return response($role, 201);
            }
        }
        return response($result, 400);
    }

}
