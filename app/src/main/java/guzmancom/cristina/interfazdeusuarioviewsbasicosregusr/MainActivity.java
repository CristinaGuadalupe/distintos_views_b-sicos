package guzmancom.cristina.interfazdeusuarioviewsbasicosregusr;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static TextView txtHoraNacimiento,txtFechaNacimiento;
    public TextView txtUserName,txtPassword,txtNombrePila;
    public CheckBox chkJava,chkDotNet,chkPyton;
    public RadioButton rdbMasculino,rdbFemenino;
    public ToggleButton tbPublicidad;
    public Switch swNotificaciones;
    public Spinner sporigen;
    public ImageButton btnHoraNacimiento,btnFechaNacimiento;
    public Button btnGuardar;
    static int year,month,date,hrs,min;

    public void showDialog(List<usuario> usuarios) {
        ArrayList<String> displayValues = new ArrayList<>();
        for (usuario entity : usuarios) {
            displayValues.add(entity.getUsername());
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        displayValues);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Usuarios recuperados");
        builder.setAdapter(adapter, null);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUserName = (EditText) findViewById(R.id.et_username);
        txtPassword = (EditText) findViewById(R.id.et_password);
        txtNombrePila = (EditText) findViewById(R.id.et_nombrePila);
        chkDotNet = (CheckBox) findViewById(R.id.cb_dotnet);
        chkJava = (CheckBox) findViewById(R.id.cb_java);
        chkPyton = (CheckBox) findViewById(R.id.cb_phyton);
        rdbFemenino = (RadioButton) findViewById(R.id.rd_femenino);
        rdbMasculino = (RadioButton) findViewById(R.id.rd_masculino);
        swNotificaciones = (Switch) findViewById(R.id.sw_notificaciones);
        tbPublicidad = (ToggleButton) findViewById(R.id.tg_publicidad);
        sporigen = (Spinner) findViewById(R.id.sp_origen);

        btnHoraNacimiento =
                (ImageButton)findViewById(R.id.ib_horaFechaNacimiento);
        btnFechaNacimiento =
                (ImageButton)findViewById(R.id.ib_calenFechaNacimiento);

        txtHoraNacimiento =
                (TextView) findViewById(R.id.tv_horaNacimiento);
        txtFechaNacimiento =
                (TextView) findViewById(R.id.tv_fechaNacimiento);

        btnGuardar =
                (Button) findViewById(R.id.btn_guardar);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Bajar todos los valores de los GUI Widgets a un pojo
                usuario objUsr = new usuario();
                objUsr.setUsername(txtUserName.getText().toString());
                objUsr.setPassword(txtPassword.getText().toString());
                objUsr.setNombrePila(txtNombrePila.getText().toString());

                String tecnologias = "";
                tecnologias += (chkDotNet.isChecked())? ".NET;" : "";
                tecnologias += (chkJava.isChecked())? "JAVA;" : "";
                tecnologias += (chkPyton.isChecked())? "PHYTON;":"";
                objUsr.setTecnologias(tecnologias );

                objUsr.setGenero((rdbFemenino.isChecked())?"FEMENINO":"MASCULINO");
                objUsr.setNotificaciones(swNotificaciones.isChecked());
                objUsr.setPublicidad(tbPublicidad.isChecked());
                objUsr.setIes_origen(sporigen.getSelectedItem().toString() );
                objUsr.setFechaHoraNacimiento(new Date(year, month, date, hrs, min));

         /*
                BaseDatosDbHelper dbHelper = new BaseDatosDbHelper(getApplicationContext());

                try {
                    SQLiteDatabase bd = dbHelper.getWritableDatabase();
                    ContentValues valores = new ContentValues();
                    valores.put(BaseDatosContract.TablaUsuario.COLUMN_NAME_USERNAME,
                                objUsr.getUsername());
                    valores.put(BaseDatosContract.TablaUsuario.COLUMN_NAME_PASSWORD,
                                objUsr.getPassword());
                    valores.put(BaseDatosContract.TablaUsuario.COLUMN_NAME_NOMBRE_PILA,
                            objUsr.getNombrePila());
                    valores.put(BaseDatosContract.TablaUsuario.COLUMN_NAME_FECHA_NACIMIENTO,
                            objUsr.getFechaHoraNacimiento().getTime()); //Unix Epoch
                    valores.put(BaseDatosContract.TablaUsuario.COLUMN_NAME_GENERO,
                            objUsr.getGenero());
                    valores.put(BaseDatosContract.TablaUsuario.COLUMN_NAME_INSTITUCION,
                            objUsr.getIes_origen());
                    valores.put(BaseDatosContract.TablaUsuario.COLUMN_NAME_TECNOLOGIAS,
                            objUsr.getTecnologias());
                    valores.put(BaseDatosContract.TablaUsuario.COLUMN_NAME_PUBLICIDAD,
                            objUsr.isPublicidad());
                    valores.put(BaseDatosContract.TablaUsuario.COLUMN_NAME_NOTIFICACIONES,
                            objUsr.isNotificaciones());
                    //...
                    bd.insert(BaseDatosContract.TablaUsuario.TABLE_NAME, null, valores);
                    bd.close();
                    Toast.makeText(MainActivity.this,
                            "El usuario se ha almacenado en BD correctamente.",
                            Toast.LENGTH_SHORT).show();

                }catch (Exception ex){
                    Toast.makeText(MainActivity.this, "Hubo un problema al guardar en BD.", Toast.LENGTH_SHORT).show();
                }

                try{
                    SQLiteDatabase db = dbHelper.getReadableDatabase();

                    String[] projection = {
                            BaseDatosContract.TablaUsuario._ID,
                            BaseDatosContract.TablaUsuario.COLUMN_NAME_USERNAME,
                            BaseDatosContract.TablaUsuario.COLUMN_NAME_PASSWORD,
                            BaseDatosContract.TablaUsuario.COLUMN_NAME_NOMBRE_PILA
                    };

                    Cursor resultado = db.query(
                             BaseDatosContract.TablaUsuario.TABLE_NAME,
                             projection,
                             null,null,null,null,null);

                    List usuarios = new ArrayList<Usuario>();
                    Usuario objRecuperado;
                    while(resultado.moveToNext()) {
                        objRecuperado = new Usuario();
                        //Recuperar Username y asignarlo al pojo
                        String username = resultado.getString(
                                resultado.getColumnIndexOrThrow(
                                        BaseDatosContract.TablaUsuario.COLUMN_NAME_USERNAME));
                        objRecuperado.setUsername(username);

                        //Recuperar Password y asignarlo al pojo
                        String password = resultado.getString(
                                resultado.getColumnIndexOrThrow(
                                        BaseDatosContract.TablaUsuario.COLUMN_NAME_PASSWORD));
                        objRecuperado.setPassword(password);

                        usuarios.add(objRecuperado);
                    }
                    resultado.close();

                    //Mostrar el listado de datos recuperados
                    showDialog(usuarios);

                    //Toast.makeText(MainActivity.this, "Recuperados: " + usuarios.toString() , Toast.LENGTH_SHORT).show();

                }catch (Exception ex){
                    Toast.makeText(MainActivity.this, "Hubo problemas al leer la base de datos", Toast.LENGTH_SHORT).show();
                }
                */

                String FILENAME = "archivo.txt";
                String datosAEscribir = objUsr.toString();
                try{
                    FileOutputStream fos = openFileOutput(FILENAME , Context.MODE_PRIVATE);
                    PrintWriter writer = new PrintWriter(fos);
                    writer.println(datosAEscribir);
                    writer.close();
                    fos.close();
                    Toast.makeText(MainActivity.this, "Datos Escritos correctamente", Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this, "Error al escribir los datos en archivo.", Toast.LENGTH_SHORT).show();
                }

                try{
                    FileInputStream fis = openFileInput(FILENAME);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader reader = new BufferedReader(isr);
                    String datosLeidos = reader.readLine();
                    reader.close();
                    fis.close();
                    Toast.makeText(MainActivity.this, "Datos recuperados: " + datosLeidos, Toast.LENGTH_LONG).show();

                }catch (Exception ex){
                    Toast.makeText(MainActivity.this, "Error al leer los datos desde archivo.", Toast.LENGTH_SHORT).show();
                }


                Toast.makeText(MainActivity.this, "Se guardó el usuario: "
                        + objUsr.toString(), Toast.LENGTH_LONG).show();
            }
        });


        btnHoraNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });
        btnFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

    }

    public static class TimePickerFragment extends android.app.DialogFragment
            implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final java.util.Calendar c = java.util.Calendar.getInstance();
            int hour = c.get(java.util.Calendar.HOUR_OF_DAY);
            int minute = c.get(java.util.Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //Programar nuestro código en respuesta a que se seleccionó hora
            hrs = hourOfDay;
            min = minute;
            txtHoraNacimiento.setText("Hora:" + hourOfDay + " Minuto:" + minute);
        }
    }

    public static class DatePickerFragment extends android.app.DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final java.util.Calendar c = java.util.Calendar.getInstance();
            int year = c.get(java.util.Calendar.YEAR);
            int month = c.get(java.util.Calendar.MONTH);
            int day = c.get(java.util.Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);

        }

        public void onDateSet(DatePicker view, int y, int m, int d) {
            // Do something with the date chosen by the user
            year = y;
            month = m+1;
            date = d;
            txtFechaNacimiento.setText("Día:" + date +
                    " Mes:" + month +
                    " Año:" + year);

        }
    }

}