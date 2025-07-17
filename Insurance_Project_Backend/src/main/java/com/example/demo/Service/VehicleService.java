package com.example.demo.Service;

import com.example.demo.Dto.VehicleDto;
import com.example.demo.Entity.User;
import com.example.demo.Entity.Vehicle;
import com.example.demo.Mapper.VehicleMapper;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private final VehicleRepository vehicleRepository;
    @Autowired
    private final VehicleMapper vehicleMapper;

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper,UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
        this.userRepository=userRepository;
    }

    public ResponseEntity<?> registerVehicle(Long userId, VehicleDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Vehicle vehicle = new Vehicle();
        vehicle.setUser(user);
        vehicle.setType(request.getType());
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        vehicle.setRegistrationNumber(request.getRegistrationNumber());
        vehicle.setCreatedAt(LocalDateTime.now());

        vehicleRepository.save(vehicle);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Vehicle registered successfully");
        response.put("vehicleId", vehicle.getId());
        return ResponseEntity.ok(response);
    }
    public List<VehicleDto> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<VehicleDto> getVehiclesByUser(Long userId) {
        List<Vehicle> vehicles = vehicleRepository.findByUserId(userId);
        return vehicles.stream().map(vehicleMapper::toDto).collect(Collectors.toList());
    }
}
