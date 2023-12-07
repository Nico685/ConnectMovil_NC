package com.example.connectmovil_nc;

// En tu actividad donde estás listando los usuarios
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListarContactos extends AppCompatActivity {

    private ListView listViewUsuarios;
    private ArrayAdapter<Usuario> adapter;
    private List<Usuario> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contactos);

        listViewUsuarios = findViewById(R.id.listViewUsuarios);

        listaUsuarios = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.list_item_custom, listaUsuarios);

        listViewUsuarios.setAdapter(adapter);

        ImageButton volver = findViewById(R.id.btnVolver2);

        obtenerUsuariosDeFirebase();

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void obtenerUsuariosDeFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Usuarios");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    if (usuario != null) {
                        // Establecer el ID del usuario
                        usuario.setId(snapshot.getKey());
                        listaUsuarios.add(usuario);
                    }
                }

                // Notificar al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error, si es necesario
            }
        });
    }
}
