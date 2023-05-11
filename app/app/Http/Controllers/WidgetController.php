<?php

namespace App\Http\Controllers;

use App\Http\Resources\CartResource;
use App\Models\Category;
use App\Models\Order;
use App\Models\Product;
use App\Models\Role;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Crypt;
use Illuminate\Support\Facades\DB;

class WidgetController extends Controller
{
    private $FILE_PATH_SRC = "D:/wamp64/www/photo";
    private $ARRAY_FILE_EXTENSION = ['jpeg', 'png', 'jpg'];
    private static $IS_ACTIVE = 1;
    private static $IS_BLOCK = 0;
    private static $IS_MAINTAINCE = 2;
    private static $ORDER_FAILED = 0;
    private static $ORDER_PROCESSING = 1;
    private static $ORDER_SHIPPING = 2;
    private static $ORDER_SUCCESS = 3;

    public function uploadFileAPI(Request $request)
    {
        $result = '';
        if ($this->checkValidateDataImage($request->file('image'))) {
            $imageName = time() . '.' . $request->image->extension();
            $result = $imageName;
            $request->image->move($this->FILE_PATH_SRC, $imageName);
            return response()->json([
                'image' => $result,
            ], 200);
        } else {
            return response($result, 400);
        }
    }

    private function checkValidateDataImage($imageFile)
    {
        if (!isset($imageFile)) {
            return null;
        }

        // $originOfFile = explode("/", $imageFile->getClientMimeType());
        // if ($originOfFile[0] != "image") {
        //     return null;
        // } else {
        //     if (!in_array($originOfFile[1], $this->ARRAY_FILE_EXTENSION, false)) {
        //         return null;
        //     }
        // }
        return true;
    }


    /*
    * Widget Product methods 
    */
    public static function checkValidateDataProduct($request)
    {
        if (!isset($request->name) || !isset($request->quantity) || !isset($request->price) || !isset($request->description) || !isset($request->type) || !isset($request->categoryIds)) {
            return null;
        }

        if ($request->quantity < 1 || $request->price < 1 || count($request->categoryIds) < 1) {
            return null;
        }
        return $request->all();
    }

    public static function setDataToProduct($request, $product)
    {
        $product->name = $request['name'];
        $product->description = $request['description'];
        $product->price = $request['price'];
        $product->quantity = $request['quantity'];
        $product->image = $request['image'] == null ? null : $request['image'];
        $product->type = $request['type'];
    }


    /*
    * Widget Category methods 
    */
    public static function checkValidateDataCategory($request)
    {
        if (!isset($request->name) || !isset($request->image)) {
            return null;
        }
        return $request->all();
    }

    public static function setDataToCategory($request, $category)
    {
        $category->name = $request['name'];
        $category->image = $request['image'];
    }


    /*
    * Widget User methods 
    */
    public static function checkValidateDataUser($request)
    {
        if (!isset($request->full_name) || !isset($request->image) || !isset($request->email) || !isset($request->roles)) {
            return null;
        }
        return $request->all();
    }

    public static function setDataToUser($request, $user , $setPassword)
    {
        $user->full_name = $request['full_name'];
        $user->image = $request['image'];
        $user->email = $request['email'];
        if($setPassword == true){
            $user->password = Crypt::encrypt($request['password']);
        }
        if (isset($request->status)) {
            switch ($request->status) {
                case 0:
                    $user->status = self::$IS_BLOCK;
                    break;
                case 1:
                    $user->status = self::$IS_ACTIVE;
                    break;
                case 2:
                    $user->status = self::$IS_MAINTAINCE;
                    break;
            }
        } else {
            $user->status = self::$IS_ACTIVE;
        }
    }


    /*
    * Widget User methods 
    */
    public static function checkValidateDataRole($request)
    {
        if (!isset($request->code) || !isset($request->name)) {
            return null;
        }

        return $request->all();
    }

    public static function setDataToRole($request, $role)
    {
        $role->code = $request['code'];
        $role->name = $request['name'];
    }


    /*
    * Widget User methods 
    */
    public static function checkValidateDataNotifycation($request)
    {
        if (!isset($request->image) || !isset($request->content) || !isset($request->user_id)) {
            return null;
        }

        return $request->all();
    }

    public static function setDataToNotifycation($request, $notifycation)
    {
        $notifycation->image = $request['image'];
        $notifycation->content = $request['content'];
        $notifycation->user_id = $request['user_id'];
    }


    /*
    * Widget User methods 
    */
    public static function checkValidateDataCartItem($request)
    {
        if (!isset($request->user_id) || !isset($request->product_id) || !isset($request->process)) {
            return null;
        }

        if (User::find($request->user_id) == null) {
            return null;
        }

        if (Product::find($request->product_id) == null) {
            return null;
        }

        return $request->all();
    }

    public static function setDataToCartItem($request, $cartItem)
    {
        $cartItem->user_id = $request['user_id'];
        $cartItem->product_id = $request['product_id'];
        $cartItem->quantity = $request['quantity'];
    }


    /*
    * Widget Order methods 
    */

    public static function checkValidateDataOrder($request)
    {
        if (!isset($request->user_id) || !isset($request->delivery_address) || !isset($request->customer_phone)) {
            return null;
        }

        if (User::find($request->user_id) == null) {
            return null;
        }

        return $request->all();
    }

    public static function setDataToOrder($request, $order)
    {
        $order->user_id = $request['user_id'];
        $order->delivery_address = $request['delivery_address'];
        $order->customer_phone = $request['customer_phone'];
        $order->total = self::getTotalOfOrder($request['user_id']);
        if (isset($request['shipper_id'])) {
            if (User::find($request->shipper_id) == null) {
                return null;
            }
            $order->shipper_id = $request['shipper_id'];
        }
        $order->status = self::$ORDER_PROCESSING;
    }

    public static function getTotalOfOrder($user_id)
    {
        $total = 0;
        $resource = new CartResource(User::find($user_id));
        $array = json_decode($resource->toJson(), true);
        foreach ($array as $item) {
            $product = Product::find($item['product']['id']);
            if ($product != null) {
                $total += ($product->price * $item['quantity']);
            }
        }
        return $total;
    }


    /*
    * Widget OrderProduct methods 
    */
    public static function attachToOrderProductTable($order)
    {
        $resource = new CartResource(User::find($order->user_id));
        $array = json_decode($resource->toJson(), true);
        foreach ($array as $item) {
            DB::table('order_product')->insert([
                'order_id' => $order->id,
                'product_id' => $item['product']['id'],
                'price' => $item['product']['price'],
                'quantity' => $item['quantity']
            ]);
        }
    }


    /*
    * Widget CategoryProduct methods 
    */
    public static function attachToCategoryProductTable($array, $product)
    {
        DB::table('category_product')->where('product_id', $product->id)->delete();
        foreach ($array as $categoryCanAttach) {
            DB::table('category_product')->insert([
                'category_id' => $categoryCanAttach,
                'product_id' => $product->id
            ]);
        }
    }


    /*
    * Widget RoleUser methods 
    */
    public static function attachToRoleUserTable($array, $user)
    {
        DB::table('role_user')->where('user_id', $user->id)->delete();
        foreach ($array as $roleCanAttach) {
            DB::table('role_user')->insert([
                'role_id' => $roleCanAttach,
                'user_id' => $user->id
            ]);
        }
    }
}
