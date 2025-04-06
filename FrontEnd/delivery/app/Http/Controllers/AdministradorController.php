<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class AdministradorController extends Controller
{
    public function inicio()
    {
        return view('administrador');
    }


    public function pedidos()
    {
        return view('adminpedidos');
    }
    public function usuarios()
    {
        return view('adminusuarios');
    }
    public function empresas()
    {
        return view('adminempresas');
    }
    public function productosMasVendidos()
    {
        return view('AdminPedidosMasVendidos');
    }
}
