package com.nitrocanar.prueba10.controlador;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitrocanar.prueba10.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder grabacion;
    private String archivoSalida = null;
    private Button btnRecorder;

    private ImageButton btnTomarFoto, btnCerrar;
    private ImageView imgTomada;
    private EditText edTitulo;
    private TextView tvTexto;

    private Bitmap bitFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        referenciar();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA}, 111);

        }
    }

    private void referenciar() {

        btnRecorder = findViewById(R.id.btnRecorder);


        btnTomarFoto = findViewById(R.id.ibTomarFoto);
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto();
            }
        });

        btnCerrar = findViewById(R.id.ibCerrar);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edTitulo = findViewById(R.id.edtTitulo);

        tvTexto = findViewById(R.id.tvMensajes);

        imgTomada = findViewById(R.id.ivImagenTomada);

    }

    public void Record(View view) {

        if (edTitulo.getText().toString().isEmpty()) {
            edTitulo.setError("Escribe el titulo de tú recuerdo");
        } else {

            if (grabacion == null) {

                archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/grabacion" + edTitulo.getText().toString() + ".mp3";
                grabacion = new MediaRecorder();
                grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
                grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                grabacion.setOutputFile(archivoSalida);

                try {

                    grabacion.prepare();
                    grabacion.start();

                    btnRecorder.setText("Stop");
                    tvTexto.setText("Grabando...");

                } catch (IOException e) {
                }


            } else if (grabacion != null) {

                grabacion.stop();
                grabacion.release();
                grabacion = null;

                btnRecorder.setText("Grabar");
                tvTexto.setText(R.string.pulsaelboton);

            }

        }

    }

    private void tomarFoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle extras = data.getExtras();
        bitFoto = (Bitmap) extras.get("data");

        imgTomada.setImageBitmap(bitFoto);

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (grabacion != null) {

            grabacion.stop();
            grabacion.release();
            grabacion = null;
            tvTexto.setText(R.string.pulsaelboton);

        }

    }
}
