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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHoraNacimiento = (ImageButton) findViewById(R.id.ib_horaFechaNacimiento);
        btnFechaNacimiento = (ImageButton) findViewById(R.id.ib_calenFechaNacimiento);
        txtFechaNacimiento = (TextView) findViewById(R.id.tv_fechaNacimiento);
        txtHoraNacimiento = (TextView) findViewById(R.id.tv_horaNacimiento);
        btnGuardar = (Button) findViewById(R.id.btn_guardar);
        txtUserName = (TextView) findViewById(R.id.tv_nombrePila);
        txtPassword = (TextView) findViewById(R.id.tv_password);
        txtNombrePila = (TextView) findViewById(R.id.tv_nombrePila);
        chkJava = (CheckBox) findViewById(R.id.cb_java);
        chkDotNet = (CheckBox) findViewById(R.id.cb_dotnet);
        chkPyton = (CheckBox) findViewById(R.id.cb_phyton);
        rdbMasculino = (RadioButton) findViewById(R.id.rd_masculino);
        rdbFemenino = (RadioButton) findViewById(R.id.rd_femenino);
        swNotificaciones = (Switch) findViewById(R.id.sw_notificaciones);
        tbPublicidad = (ToggleButton) findViewById(R.id.tg_publicidad);
        sporigen = (Spinner) findViewById(R.id.sp_origen);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo();
            }
        });
    }

            public void dialogo() {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Guardar");
                builder.setMessage("Esta Seguro?");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                usuario objUsr=new usuario();
                objUsr.setUsername(txtUserName.getText().toString());
                objUsr.setPassword(txtPassword.getText().toString());
                objUsr.setNombrePila(txtNombrePila.getText().toString());

                String tecnologias="";
                tecnologias +=(chkDotNet.isChecked()? ".NET" : "");
                tecnologias +=(chkDotNet.isChecked()? ".JAVA" : "");
                tecnologias +=(chkDotNet.isChecked()? ".PHYTON" : "");
                objUsr.setTecnologias(tecnologias);

                objUsr.setGenero((rdbFemenino.isChecked())?"Femenino":"Masculino");
                objUsr.setNotificaciones(swNotificaciones.isActivated());
                objUsr.setPublicidad(tbPublicidad.isChecked());
                objUsr.setIes_origen(sporigen.getSelectedItem().toString());
                objUsr.setFechaHoraNacimiento(new Date(year,month,date,hrs,min));

                        BaseDatosDeHelper dbHelper = new BaseDatosDeHelper(getApplicationContext());
                try {
                    SQLiteDatabase bd = dbHelper.getWritableDatabase();
                    ContentValues valores = new ContentValues();
                    valores.put(BaseDatosContract.usuarioTable.COLUMN_NAME_USERNAME, objUsr.getUsername());
                    valores.put(BaseDatosContract.usuarioTable.COLUMN_NAME_PASSWORD, objUsr.getPassword());
                    valores.put(BaseDatosContract.usuarioTable.COLUMN_NAME_GENERO, objUsr.getGenero());
                    valores.put(BaseDatosContract.usuarioTable.COLUMN_NAME_INSTITUCION, objUsr.getPassword());
                    valores.put(BaseDatosContract.usuarioTable.COLUMN_NAME_NOMBRE_PILA, objUsr.getNombrePila());
                    valores.put(BaseDatosContract.usuarioTable.COLUMN_NAME_NOTIFICACIONES, objUsr.getPassword());
                    valores.put(BaseDatosContract.usuarioTable.COLUMN_NAME_PUBLICIDAD, objUsr.getPassword());
                    valores.put(BaseDatosContract.usuarioTable.COLUMN_NAME_TECNOLOGIAS, objUsr.getTecnologias());


                    bd.insert(BaseDatosContract.usuarioTable.TABLE_NAME, null, valores);
                    bd.close();
                    Toast.makeText(MainActivity.this, "El usuario se registro",Toast.LENGTH_LONG).show();
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this,"Hubo un problema en la Base ",Toast.LENGTH_LONG).show();
                }
                try{
                    SQLiteDatabase db=dbHelper.getReadableDatabase();

                    String[] projection ={
                            BaseDatosContract.usuarioTable._ID,
                            BaseDatosContract.usuarioTable.COLUMN_NAME_USERNAME,
                            BaseDatosContract.usuarioTable.COLUMN_NAME_PASSWORD,
                            BaseDatosContract.usuarioTable.COLUMN_NAME_NOMBRE_PILA,

                    };
                    Cursor resultado=db.query(BaseDatosContract.usuarioTable.TABLE_NAME,projection,null,null,null,null,null);

                    List usuarios=new ArrayList<usuario>();
                    usuario objRecuperado;
                    if(resultado.moveToNext()){
                        objRecuperado=new usuario();
                        String username=resultado.getString(){
                            resultado.getColumnIndexOrThrow(
                                    BaseDatosContract.usuarioTable.COLUMN_NAME_USERNAME));
                objRecuperado.setUsername(username);

                //Recuperar password y asignarlo al pojo
                            )
                        }

                    }
                }catch(Exception ex){

                }
                    }
                /*

                        String FILENAME = "hello_file";
                        String datos=objUsr.toString();
                        try{
                            FileOutputStream fod=openFileOutput(FILENAME, Context.MODE_PRIVATE);
                            OutputStreamWriter writer=new OutputStreamWriter(fod);
                            BufferedWriter bw=new BufferedWriter(writer);
                            bw.write(datos);
                            fod.close();
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        try{
                            FileInputStream fis=openFileInput(FILENAME);
                            InputStreamReader reader=new InputStreamReader(fis);
                            BufferedReader br=new BufferedReader(reader);
                            String datosfile=br.readLine();
                            fis.close();
                            Toast.makeText(MainActivity.this,datosfile,Toast.LENGTH_LONG).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(MainActivity.this,"Se guardo el usuario: "+objUsr.toString(),Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"Ha elegido no guardar el registro",Toast.LENGTH_LONG).show();
            }
        });
                Dialog dialog=builder.create();
                dialog.show();


        ImageButton btnHoraNacimiento = (ImageButton)findViewById(R.id.ib_horaFechaNacimiento);

        btnHoraNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment=new TimerPickerFragment();
                newFragment.show(getSupportFragmentManager(),"timePicker");
            }
        });
    }


    public static class TimerPickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //Programar nuestro codigo
           // txtHoraNacimiento=String.format("%02d:%02d",hourOfDay,minute);
            hrs=hourOfDay;
            min=minute;
            txtHoraNacimiento.setText("Hora:" +hourOfDay + ":" + minute);
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);

        }

        @Override
        public void onDateSet(DatePicker view, int y, int m, int d) {
            year=y;
            month=m;
            date=d;
            txtFechaNacimiento.setText("Dia:" + d +
                    "Mes:" + m + "Año:" + y);
        }
    }
}
