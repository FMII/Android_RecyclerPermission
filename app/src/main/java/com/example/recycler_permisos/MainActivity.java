package com.example.recycler_permisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.recycler_permisos.Adapter.PermisoAdapter;
import com.example.recycler_permisos.Models.Permiso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PermisoAdapter.OnPermissionChangeListener {

    private static final int REQUEST_PHONE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Permiso> ListaPermisos = new ArrayList<>();

        ListaPermisos.add(new Permiso(Manifest.permission.CALL_PHONE, "Llamar"));
        ListaPermisos.add(new Permiso(Manifest.permission.CAMERA, "Cámara"));
        ListaPermisos.add(new Permiso(Manifest.permission.ACCESS_FINE_LOCATION, "Localización"));

        if (checkPermissions()) {
            // Iniciar PantallaPrincipal
            Intent intent = new Intent(this, PantallaPrincipal.class);
            startActivity(intent);
            finish();
        } else {
            RecyclerView rvPermisos = findViewById(R.id.rvpermiso);
            rvPermisos.setLayoutManager(new LinearLayoutManager(this));
            rvPermisos.setHasFixedSize(true);
            PermisoAdapter adapter = new PermisoAdapter(ListaPermisos, this);
            rvPermisos.setAdapter(adapter);
        }
    }

    @Override
    public void onPermissionChanged(String descripcion, boolean isChecked) {
        switch (descripcion) {
            case "Llamar":
                if (isChecked) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 2000);
                    } else {
                        llamada();
                    }
                }
                break;

            case "Cámara":
                if (isChecked) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2001);
                    } else {
                        Camara();
                    }
                }
                break;

            case "Localización":
                if (isChecked) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2002);
                    } else {
                        Direccion();
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1987) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkPermissions()) {
                    // PantallaPrincipal
                    Intent intent = new Intent(this, PantallaPrincipal.class);
                    startActivity(intent);
                    finish();
                } else {
                    permisosOPlomo();
                }
            }
        } else if (requestCode == 1988) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkPermissions()) {
                    Intent intent = new Intent(this, PantallaPrincipal.class);
                    startActivity(intent);
                    finish();
                } else {
                    permisosOPlomo();
                }
            }
        } else if (requestCode == 1989) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkPermissions()) {
                    Intent intent = new Intent(this, PantallaPrincipal.class);
                    startActivity(intent);
                    finish();
                } else {
                    permisosOPlomo();
                       }
            }
        }
    }

    public void llamada() {
    }

    public void Camara() {
    }

    public void Direccion() {
    }

    public void permisosOPlomo() {
        Toast.makeText(this, "Se requieren todos los permisos para continuar", Toast.LENGTH_SHORT).show();
    }

    private boolean checkPermissions() {
        int llamarP = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int camaraP = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int localizacionP = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        return llamarP == PackageManager.PERMISSION_GRANTED &&
                camaraP == PackageManager.PERMISSION_GRANTED &&
                localizacionP == PackageManager.PERMISSION_GRANTED;
    }
}
