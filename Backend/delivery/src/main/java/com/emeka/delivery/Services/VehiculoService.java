package com.emeka.delivery.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeka.delivery.Repositories.VehiculoRepository;
import com.emeka.delivery.models.Usuario;
import com.emeka.delivery.models.Vehiculo;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public String guardarVehiculo(Usuario repartidor) {
        // Crear listas de opciones
        List<String> marcas = new ArrayList<>();
        marcas.add("Toyota");
        marcas.add("Ford");
        marcas.add("Honda");
        marcas.add("Chevrolet");
        marcas.add("BMW");

        List<String> modelos = new ArrayList<>();
        modelos.add("Corolla");
        modelos.add("Mustang");
        modelos.add("Civic");
        modelos.add("Camaro");
        modelos.add("X5");

        List<String> vehiculos = new ArrayList<>();
        vehiculos.add("Sedán");
        vehiculos.add("SUV");
        vehiculos.add("Deportivo");
        vehiculos.add("Pickup");
        vehiculos.add("Hatchback");

        List<Integer> velocidades = new ArrayList<>();
        velocidades.add(100);
        velocidades.add(150);
        velocidades.add(200);
        velocidades.add(250);
        velocidades.add(300);
        Vehiculo vehiculo = new Vehiculo();

        // Asignar valores aleatorios a los atributos del vehículo
        Random random = new Random();

        vehiculo.setMarca(marcas.get(random.nextInt(marcas.size()))); // Elegir aleatoriamente de la lista
        vehiculo.setModelo(modelos.get(random.nextInt(modelos.size())));
        vehiculo.setVehiculo(vehiculos.get(random.nextInt(vehiculos.size())));
        vehiculo.setVelocidad(velocidades.get(random.nextInt(velocidades.size())));

        vehiculo.setRepartidor(repartidor);

        vehiculoRepository.save(vehiculo);

        return "Vehiculo guardado al repartidor";

    }

}
