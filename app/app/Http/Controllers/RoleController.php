<?php

namespace App\Http\Controllers;

use App\Models\Role;
use Illuminate\Http\Request;

class RoleController extends Controller
{
    public function getRolesAPI(Request $request)
    {
        $result = '';
        $result = Role::all();
        if ($result != null) {
            return response($result, 200);
        }
        return response($result, 400);
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

    

}
