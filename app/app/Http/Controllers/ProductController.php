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
                if (isset($request->categories)) {
                    $product = Product::find($request->id);
                    WidgetController::setDataToProduct($result, $product);
                    $product->save();
                    WidgetController::attachToCategoryProductTable($request->categories, $product);
                    return response($product, 201);
                }
            } else {
                if (isset($request->categories)) {
                    $product = new Product;
                    WidgetController::setDataToProduct($result, $product);
                    $product->save();
                    WidgetController::attachToCategoryProductTable($request->categories, $product);
                    return response($product, 201);
                }
            }
        }
        return response($result, 400);
    }

    public function getProductsAPI(Request $request)
    {
        $result = '';
        if ($request->query('categoryId') == null) {
            $result = Product::all();
            if ($result != null) {
                return response($result, 200);
            }
            return response($result, 400);
        } else {
            $category = Category::find($request->query('categoryId'));
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