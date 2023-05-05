<?php

namespace App\Http\Controllers;

use App\Models\Category;
use App\Models\Product;
use Illuminate\Http\Request;

class CategoryController extends Controller
{
    public function getCategoriesAPI(Request $request)
    {
        $result = '';
        if ($request->query('productId') == null) {
            $result = Category::select("*")->orderBy('categories.id', 'desc')->get();
            if ($result != null) {
                foreach ($result as $i){
                    $category = Category::find($i->id);
                    $i->numberOfProduct = $category->products()->count();
                }
                return response($result, 200);
            }
            return response($result, 400);
        }else{
            $product = Product::find($request->query('productId'));
            if($product != null) {
                $result = $product->categories();
                if ($result != null) {
                    return response($result, 200);
                }
            }
            return response($result, 400);
        }
    }

    public function getCategoryByIdAPI(Request $request)
    {
        $result = '';
        $result = Category::find($request->id);
        if ($result != null) {
            return response($result, 200);
        }
        return response($result, 400);
    }


    public function addOrEditCategoryAPI(Request $request)
    {
        
        $result = '';
        $result = WidgetController::checkValidateDataCategory($request);

        if ($result != null && isset($result)) {
            if (isset($request->id)) {
                $category = Category::find($request->id);
                WidgetController::setDataToCategory($result, $category);
                $category->save();
                return response($category, 201);
            } else {
                $category = new Category();
                WidgetController::setDataToCategory($result, $category);
                $category->save();
                return response($category, 201);
            }
        }
        return response($result, 400);
    }

    public function deleteCategoryAPI(Request $request)
    {
        $result = null;
        if (isset($request->id)) {
            $category = Category::find($request->id);
            if ($category != null) {
                $result = $category;
                $category->delete();
                return response($result, 200);
            }
            else{
                return response($result, 400);
            }
        }
        return response($result, 400);
    }

}
