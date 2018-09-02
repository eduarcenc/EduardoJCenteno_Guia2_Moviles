package org.dev4u.eduardo.guia2_moviles;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtURL, txtName;
    private RadioButton rdbName, rdbNoName;
    private ProgressBar prbProgress;
    private TextView lblEstado;
    private Button btnDescargar;

    //private static final String TAG = "LogActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializando
        txtURL       = (EditText) findViewById(R.id.txtURL);
        txtName = (EditText) findViewById(R.id.txtName);
        rdbName = (RadioButton) findViewById(R.id.rdbName);
        rdbNoName = (RadioButton) findViewById(R.id.rdbNoName);
        prbProgress = (ProgressBar) findViewById(R.id.prbProgress);
        lblEstado    = (TextView) findViewById(R.id.lblEstado);
        btnDescargar = (Button)   findViewById(R.id.btnDescargar);


        //evento onClick

        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Validar si se digito el nombre en el EditText cuando esta checkeada la opcion
                //Log.d(TAG, "Checked: " + rdbName.isChecked());
                boolean download = true;
                if (rdbName.isChecked()) {
                    if (txtName.getText().length() > 0)
                    {
                        //Se digito el nombre del archivo
                        //Se guardar el archivo con el nombre que viene
                        download = true;
                    } else {
                        //No se ingreso el nombre del archivo
                        Toast.makeText(MainActivity.this, "Por favor escriba un nombre al archivo !", Toast.LENGTH_LONG).show();
                        download = false;
                    }
                } else {
                    txtName.setText("");
                    download = true;
                }

                // Si no hubo ningun error descargar el archivo
                if (download) {
                    // Se guardar el archivo con el nombre que viene
                    new Descargar(
                            MainActivity.this,
                            lblEstado,
                            btnDescargar,
                            prbProgress
                    ).execute(txtURL.getText().toString(), txtName.getText().toString());
                }


            }
        });

        verifyStoragePermissions(this);
    }

    // Cuando se clickea un RadioButton
    public void onRadioButtonClicked(View view) {
        // Que RadioButton es el checkeado
        boolean checked = ((RadioButton) view).isChecked();

        // Determinar accion para el RadioButton checkeado
        switch (view.getId()) {
            case R.id.rdbName:
                if (checked) {
                    txtName.setVisibility(View.VISIBLE);
                    txtName.requestFocus();
                }
                break;

            case R.id.rdbNoName:
                if (checked)
                    txtName.setVisibility(View.INVISIBLE);
                break;
        }
    }

    //esto es para activar perimiso de escritura y lectura en versiones de android 6 en adelante
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
