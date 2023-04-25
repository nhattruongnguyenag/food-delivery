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

    public function addProductAPI(Request $request){
        $result = '';
        /**
         * $data get request parameters
         * if validation successful => $data get $request->all()
         * else return error message
         */
        $result = $this->checkValidateDataProduct($request);
        
        if ($result != null && isset($result)) {
            $product = new Product;
            $product->name = $result['name'];
            $product->description = $result['description'];
            $product->price = $result['price'];
            $product->quantity = $result['quantity'];
            $product->image = $result['image'];
            $product->save();
            return MyRespone::returnResponeCreatedSuccess($product, "Add Success");
        }
        return MyRespone::returnResponeBadRequest($result, "Bad request");
    } 

    public function getProductsAPI(){
        $result = '';
        $result = Product::all();
        if ($result != null) {
            return MyRespone::returnResponeSuccess($result, "Success");
        }
        return MyRespone::returnResponeBadRequest($result, "Bad request");
    }

    public function getProductByIdAPI(Request $request){
        $result = '';
        $result = Product::find($request->id);
        if ($result != null) {
            return MyRespone::returnResponeSuccess($result, "Success");
        }
        return MyRespone::returnResponeBadRequest($result, "Bad request");
    }

    public function editProductAPI(Request $request){

    }

    public function deleteProductAPI(Request $request){

    }

    /*
    * Widget methods
    */

    private function checkValidateDataProduct($request){
        if(!isset($request->name) || !isset($request->image) || !isset($request->quantity) || !isset($request->price) || !isset($request->description)){
           return null;
        }

        if($request->quantity < 1 || $request->price < 1){
           return null;
        }
        return $request->all();
    }
}
