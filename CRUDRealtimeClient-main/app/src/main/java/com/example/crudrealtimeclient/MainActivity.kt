package com.example.crudrealtimeclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.crudrealtimeclient.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el ListView con los textos modificados
        val items = listOf(
            "¿A qué se refiere con nombre?",
            "¿Cuántas marcas hemos trabajado?",
            "¿Qué es la matrícula?"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        binding.membersListView.adapter = adapter

        // Manejar el click en los elementos del ListView
        binding.membersListView.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> Toast.makeText(this, "La persona que entregó el auto", Toast.LENGTH_SHORT).show()
                1 -> Toast.makeText(this, "Más de 100 marcas han contado con nuestro apoyo", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(this, "El número que se encuentra detrás del auto", Toast.LENGTH_SHORT).show()
            }
        }

        binding.searchButton.setOnClickListener {
            val searchVehicleNumber: String = binding.searchVehicleNumber.text.toString()
            if (searchVehicleNumber.isNotEmpty()) {
                readData(searchVehicleNumber)
            } else {
                Toast.makeText(this, "Por favor ingresa el número de serie", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(vehicleNumber: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        databaseReference.child(vehicleNumber).get().addOnSuccessListener {
            if (it.exists()) {
                val ownerName = it.child("ownerName").value
                val vehicleBrand = it.child("vehicleBrand").value
                val vehicleRTO = it.child("vehicleRTO").value
                Toast.makeText(this, "Resultados encontrados", Toast.LENGTH_SHORT).show()
                binding.searchVehicleNumber.text.clear()
                binding.readOwnerName.text = ownerName.toString()
                binding.readVehicleBrand.text = vehicleBrand.toString()
                binding.readVehicleRTO.text = vehicleRTO.toString()
            } else {
                Toast.makeText(this, "Ese número de serie no existe", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Algo salió mal", Toast.LENGTH_SHORT).show()
        }
    }
}
