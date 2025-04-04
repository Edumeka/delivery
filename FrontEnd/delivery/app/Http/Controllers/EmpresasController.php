<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class EmpresasController extends Controller

{

  public function empresasCercanasAlCliente(Request $request)
  {
 // Recuperar el idDireccion del request
 $idDireccion = $request->idDireccion;
    // Retornar una vista con las empresas cercanas
    return view('empresasCercanasAlCliente', compact('idDireccion'));
  }
  
}
