<?php

namespace App\Http\Controllers;

use App\Models\Category;
use App\Models\CategoryProduct;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class WidgetController extends Controller
{
    private $FILE_PATH_SRC = "D:/wamp64/www/photo";
    private $ARRAY_FILE_EXTENSION = ['jpeg', 'png', 'jpg'];
    private static $IS_ACTIVE = 1;
    private static $IS_BLOCK = 0;
    private static $IS_MAINTAINCE = 2;

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

        $originOfFile = explode("/", $imageFile->getClientMimeType());
        if ($originOfFile[0] != "image") {
            return null;
        } else {
            if (!in_array($originOfFile[1], $this->ARRAY_FILE_EXTENSION, false)) {
                return null;
            }
        }
        return true;
    }


    /*
    * Widget Product methods 
    */
    public static function checkValidateDataProduct($request)
    {
        if (!isset($request->name) || !isset($request->image) || !isset($request->quantity) || !isset($request->price) || !isset($request->description) || !isset($request->type) || !isset($request->categories)) {
            return null;
        }

        if ($request->quantity < 1 || $request->price < 1 || count($request->categories) <1) {
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
        $product->image = $request['image'];
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
    * Widget CategoryProduct methods 
    */
    public static function attachToCategoryProductTable($array , $product)
    {
        $canAttach = $array;
        for($i = 0; $i < count($array); $i++) {
            $temp = Category::find($array[$i])->products();
            for($z = 0; $z < count($temp); $z++) {
                if($product->id == $temp[$z]['id']){
                    unset($canAttach[$i]);
                    break;
                }   
            }
        }
        
        foreach($canAttach as $categoryCanAttach){
            DB::table('category_product')->insert([
                'category_id' => $categoryCanAttach,
                'product_id' => $product->id
            ]);
        }
    }



    /*
    * Widget User methods 
    */
    public static function checkValidateDataUser($request)
    {
        if (!isset($request->full_name) || !isset($request->image) || !isset($request->email) || !isset($request->password)) {
            return null;
        }
        return $request->all();
    }

    public static function setDataToUser($request, $user)
    {
        $user->full_name = $request['full_name'];
        $user->image = $request['image'];
        $user->email = $request['email'];
        $user->password = Hash::make($request['password']);
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
}
