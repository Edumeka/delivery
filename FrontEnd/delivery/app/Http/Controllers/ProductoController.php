<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class ProductoController extends Controller
{
    public function productos($idEmpresa)
    {
        return view('productos', ['idEmpresa' => $idEmpresa]);
    }
}