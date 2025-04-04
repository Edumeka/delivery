<?php

use App\Http\Controllers\CarritoController;
use App\Http\Controllers\EmpresasController;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\LoginController;
use App\Http\Controllers\InicioController;
use App\Http\Controllers\ProductoController;

Route::get('/', function () {
    return view('welcome');
})->name('bienvenida');

Route::get('/registrarse', [LoginController::class, 'formularioRegistro'])->name('register');

Route::get('/login', [LoginController::class, 'mostrarFormularioDeLogin'])->name('login');

Route::get('/inicio', [InicioController::class, 'inicio'])->name('inicio');

Route::post('/empresasCercanasAlCliente', [EmpresasController::class, 'empresasCercanasAlCliente'])->name('empresasCercanasAlCliente');

Route::get('/productos/{idEmpresa}', [ProductoController::class, 'productos'])->name('productos');

Route::get('/carrito', [CarritoController::class, 'verCarrito'])->name('carrito');