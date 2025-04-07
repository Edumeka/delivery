<?php

use App\Http\Controllers\CarritoController;
use App\Http\Controllers\EmpresasController;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\LoginController;
use App\Http\Controllers\InicioController;
use App\Http\Controllers\PedidoController;
use App\Http\Controllers\ProductoController;
use App\Http\Controllers\AdministradorController;

Route::get('/', function () {
    return view('welcome');
})->name('bienvenida');

Route::get('/registrarse', [LoginController::class, 'formularioRegistro'])->name('register');

Route::get('/login', [LoginController::class, 'mostrarFormularioDeLogin'])->name('login');

Route::get('/inicio', [InicioController::class, 'inicio'])->name('inicio');

Route::post('/empresasCercanasAlCliente', [EmpresasController::class, 'empresasCercanasAlCliente'])->name('empresasCercanasAlCliente');

Route::get('/productos/{idEmpresa}', [ProductoController::class, 'productos'])->name('productos');

Route::get('/carrito', [CarritoController::class, 'verCarrito'])->name('carrito');
Route::get('/pedido', [PedidoController::class, 'pedido'])->name('pedido');
Route::get('/admin', [AdministradorController::class, 'inicio'])->name('admin');
Route::get('/admin/pedidos', [AdministradorController::class, 'pedidos'])->name('admin.pedidos');
Route::get('/admin/usuarios', [AdministradorController::class, 'usuarios'])->name('admin.usuarios');
Route::get('/admin/productosMasVendidos', [AdministradorController::class, 'productosMasVendidos'])->name('admin.productosMasVendidos');
Route::get('/admin/usuarios/historial', [AdministradorController::class, 'historialUsuario'])->name('admin.historialUsuario');
Route::get('/admin/ganancias', [AdministradorController::class, 'ganancias'])->name('admin.ganancias');
Route::get('/admin/empresasMasSolicitadas', [AdministradorController::class, 'empresasMasSolicitadas'])->name('admin.empresasMasSolicitadas');
Route::get('/admin/empresas', [AdministradorController::class, 'empresas'])->name('admin.empresas');
Route::get('/admin/repartidores', [AdministradorController::class, 'repartidores'])->name('admin.repartidores');
Route::get('/admin/clientes', [AdministradorController::class, 'clientes'])->name('admin.clientes');
