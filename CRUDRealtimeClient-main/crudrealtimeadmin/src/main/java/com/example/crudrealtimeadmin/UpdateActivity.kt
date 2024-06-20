package com.example.crudrealtimeadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.crudrealtimeadmin.databinding.ActivityMainBinding
import com.example.crudrealtimeadmin.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateButton.setOnClickListener{
            val referenceVehicleNumber = binding.referenceVehicleNumber.text.toString()
            val updateOwnerName = binding.updateOwnerName.text.toString()
            val updateVehicleBrand = binding.updateVehicleBrand.text.toString()
            val updateVehicleRTO = binding.updateVehicleRTO.text.toString()

            updateData(referenceVehicleNumber,updateOwnerName,updateVehicleBrand,updateVehicleRTO)
        }

    }

    private fun updateData(vehicleNumber: String, ownerName: String, vehicleBrand: String, vehicleRTO: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        val vehicleData = mapOf<String, String>("ownerName" to ownerName, "vehicleBrand" to vehicleBrand, "vehicleRTO" to vehicleRTO)
        databaseReference.child(vehicleNumber).updateChildren(vehicleData).addOnSuccessListener {
            binding.referenceVehicleNumber.text.clear()
            binding.updateOwnerName.text.clear()
            binding.updateVehicleBrand.text.clear()
            binding.updateVehicleRTO.text.clear()
            Toast.makeText(this,"Informacion actualizada",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"No se pudo actualizar",Toast.LENGTH_SHORT).show()
        }
    }

}