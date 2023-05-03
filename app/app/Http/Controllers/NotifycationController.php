<?php

namespace App\Http\Controllers;

use App\Models\Notifycation;
use App\Models\User;
use Illuminate\Http\Request;

class NotifycationController extends Controller
{
    public function getNotifycationsAPI(Request $request)
    {
        $result = '';
        if ($request->query('userId') == null) {
            $result = Notifycation::all();
            if ($result != null) {
                return response($result, 200);
            }
            return response($result, 400);
        }else{
            $user = User::find($request->query('userId'));
            if ($user != null) {
                $result = $user->notifycations();
                if ($result != null) {
                    return response($result, 200);
                }
            }
            return response($result, 400);
        }
    }

    public function getNotifycationByIdAPI(Request $request)
    {
        $result = '';
        $result = Notifycation::find($request->id);
        if ($result != null) {
            return response($result, 200);
        }
        return response($result, 400);
    }

    public function deleteNotifycationAPI(Request $request)
    {
        $result = null;
        if (isset($request->id)) {
            $notifycation = Notifycation::find($request->id);
            if ($notifycation != null) {
                $result = $notifycation;
                $notifycation->delete();
                return response($result, 200);
            } else {
                return response($result, 400);
            }
        }
        return response($result, 400);
    }

    public function addOrEditNotifycationAPI(Request $request)
    {
        $result = '';
        $result = WidgetController::checkValidateDataNotifycation($request);
        if ($result != null && isset($result)) {
            if (isset($request->id)) {
                $notifycation  = Notifycation::find($request->id);
                WidgetController::setDataToNotifycation($request, $notifycation);
                $notifycation->save();
                return response($notifycation, 201);
            } else {
                $notifycation  = new Notifycation();
                WidgetController::setDataToNotifycation($request, $notifycation);
                $notifycation->save();
                return response($notifycation, 201);
            }
        }
        return response($result, 400);
    }
}
