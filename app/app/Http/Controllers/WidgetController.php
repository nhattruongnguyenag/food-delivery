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
}
