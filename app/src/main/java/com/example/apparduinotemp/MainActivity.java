package com.example.apparduinotemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference myDb;//Objeto DatabaseReference que representa la referencia a la ubicacion de la base de datos firebase
    TextView Temperatura, Humedad;//Objeto TextView uque se utilizará para mostrar los datos al usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("FirebaseData","OnCreated Called");// Mensaje de registro LOG que se imprime
        Temperatura = findViewById(R.id.txtTemp);
        Humedad = findViewById(R.id.txtHum);
        myDb = FirebaseDatabase.getInstance().getReference().child("Sensor");
        try {
            myDb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("FirebaseData","OnDataChange called");
                    if (Temperatura == null || Humedad== null){
                        Log.e("FirebaseData","Textview no inicializado");
                        return;
                    }
                    if (dataSnapshot.exists()){
                        if (dataSnapshot.child("Temp").exists() && dataSnapshot.child("Hum").exists()){
                            String temData = dataSnapshot.child("Temp").getValue(String.class);
                            String humData = dataSnapshot.child("Hum").getValue(String.class);
                            if (temData != null && humData != null){
                                Temperatura.setText(temData);
                                Humedad.setText(humData);
                            }else {//manejar el caso en que los datos sean nulos
                            }
                            }else {//Manejar el caso en que 'temp' o 'hum' no existan
                        }
                    }else {
                        Log.d("FirebaseData","DataSnapShot no existe");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("FirebaseData","onCancelled"+error.getMessage());
                }
            });
        }catch (Exception e){
            Log.e("FirebaseData","Error"+e.getMessage());
        }

    }
}