<?php

use App\Http\Controllers\EmpresasController;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\LoginController;
use App\Http\Controllers\InicioController;

Route::get('/', function () {
    return view('welcome');
})->name('bienvenida');

Route::get('/registrarse', [LoginController::class, 'formularioRegistro'])->name('register');

Route::get('/login', [LoginController::class, 'mostrarFormularioDeLogin'])->name('login');

Route::get('/inicio', [InicioController::class, 'inicio'])->name('inicio');

Route::post('/empresasCercanasAlCliente', [EmpresasController::class, 'empresasCercanasAlCliente'])->name('empresasCercanasAlCliente');


