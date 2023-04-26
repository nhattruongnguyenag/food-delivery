<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class WidgetController extends Controller
{
    private $FILE_PATH_SRC = "D:/wamp64/www/photo";
    private $ARRAY_FILE_EXTENSION = ['jpeg', 'png', 'jpg'];

    public function uploadFileAPI(Request $request)
    {
        $result = '';
        if ($this->checkValidateDataImage($request->file('image'))) {
            $imageName = time() . '.' . $request->image->extension();
            $result = $imageName;
            $request->image->move($this->FILE_PATH_SRC, $imageName);
            return response()->json([
                'image' => $result,
            ],200);
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
        if (!isset($request->name) || !isset($request->image) || !isset($request->quantity) || !isset($request->price) || !isset($request->description)) {
            return null;
        }

        if ($request->quantity < 1 || $request->price < 1) {
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
}
