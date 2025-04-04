<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class LoginController extends Controller
{
    public function mostrarFormularioDeLogin()
    {
        return view('login');
    }

    public function formularioRegistro()
    {
        return view('register');
    }
}
