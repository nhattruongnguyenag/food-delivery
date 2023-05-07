<?php

namespace App\Http\Controllers;

use App\Models\Category;
use App\Models\Product;
use Illuminate\Http\Request;
use PhpParser\Node\Stmt\Else_;

use function PHPUnit\Framework\isEmpty;

class ProductController extends Controller
{
    /*
    * Main methods
    */

    public function addOrEditProductAPI(Request $request)
    {
        $result = '';
        $result = WidgetController::checkValidateDataProduct($request);
        if ($result != null && isset($result)) {
            if (isset($request->id)) {
                if (isset($request->categoryIds)) {
                    $product = Product::find($request->id);
                    WidgetController::setDataToProduct($result, $product);
                    $product->save();
                    WidgetController::attachToCategoryProductTable($request->categoryIds, $product);
                    return response($product, 201);
                }
            } else {
                if (isset($request->categoryIds)) {
                    $product = new Product;
                    WidgetController::setDataToProduct($result, $product);
                    $product->save();
                    WidgetController::attachToCategoryProductTable($request->categoryIds, $product);
                    return response($product, 201);
                }
            }
        }
        return response($result, 400);
    }

    public function getProductsAPI(Request $request)
    {
        $result = '';
        if ($request->query('categoryIds') == null) {
            $result = Product::all();
            foreach ($result as $item){
                $categoryIds = [];
                foreach ($item->categories() as $i){
                    array_push($categoryIds , $i->id);
                }
                $item->categoryIds = $categoryIds;
            }
            if ($result != null) {
                return response($result, 200);
            }
            return response($result, 400);
        } else {
            $category = Category::find($request->query('categoryIds'));
            if ($category != null) {
                $result = $category->products();
                if ($result != null) {
                    return response($result, 200);
                }
            }
            return response($result, 400);
        }
    }

    public function getProductByIdAPI(Request $request)
    {
        $result = '';
        $result = Product::find($request->id);
        $categoryIds = [];
        foreach($result->categories() as $i){
            array_push($categoryIds , $i->id);
        }
        $result->categoryIds = $categoryIds;
        if ($result != null) {
            return response($result, 200);
        }
        return response($result, 400);
    }

    public function deleteProductAPI(Request $request)
    {
        $result = null;
        if (isset($request->id)) {
            $product = Product::find($request->id);
            if ($product != null) {
                $result = $product;
                $product->delete();
                return response($result, 200);
            } else {
                return response($result, 400);
            }
        }
        return response($result, 400);
    }
}
