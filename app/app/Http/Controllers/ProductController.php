<?php

namespace App\Http\Controllers;

use App\Models\Product;
use Exception;
use Illuminate\Http\Request;
use App\Models\MyRespone;

use function PHPUnit\Framework\isEmpty;

class ProductController extends Controller
{
    /*
    * Main methods
    */

    public function addOrEditProductAPI(Request $request)
    {
        $result = '';
        /**
         * $data get request parameters
         * if validation successful => $data get $request->all()
         * else return error message
         */
        $result = $this->checkValidateDataProduct($request);

        if ($result != null && isset($result)) {
            if(isset($request->id)){
                $product = Product::find($request->id);
                $this->setDataToProduct($result,$product);
                return response($product, 201);
            }else{
                $product = new Product;
                $this->setDataToProduct($result,$product);
                return response($product, 201);
            }
        }
        return response($result, 400);
    }

    public function getProductsAPI()
    {
        $result = '';
        $result = Product::all();
        if ($result != null) {
            return response($result, 200);
        }
        return response($result, 400);
    }

    public function getProductByIdAPI(Request $request)
    {
        $result = '';
        $result = Product::find($request->id);
        if ($result != null) {
            return response($result, 200);
        }
        return response($result, 400);
    }

    public function deleteProductAPI(Request $request)
    {
    }

    /*
    * Widget methods
    */

    private function checkValidateDataProduct($request)
    {
        if (!isset($request->name) || !isset($request->image) || !isset($request->quantity) || !isset($request->price) || !isset($request->description)) {
            return null;
        }

        if ($request->quantity < 1 || $request->price < 1) {
            return null;
        }
        return $request->all();
    }

    private function setDataToProduct($request , $product)
    {
        $product->name = $request['name'];
        $product->description = $request['description'];
        $product->price = $request['price'];
        $product->quantity = $request['quantity'];
        $product->image = $request['image'];
        $product->save();
    }
}
