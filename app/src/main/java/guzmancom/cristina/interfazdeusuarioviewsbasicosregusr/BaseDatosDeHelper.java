package guzmancom.cristina.interfazdeusuarioviewsbasicosregusr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatosDeHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="BaseDatos.db";

    public BaseDatosDeHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_crear_tablas =
                "CREATE TABLE " + BaseDatosContract.usuarioTable.TABLE_NAME +
                        " (" + BaseDatosContract.usuarioTable._ID + " INTEGER PRIMARY KEY," +
                        BaseDatosContract.usuarioTable.COLUMN_NAME_USERNAME + " TEXT," +
                        BaseDatosContract.usuarioTable.COLUMN_NAME_PASSWORD + " TEXT," +
                        BaseDatosContract.usuarioTable.COLUMN_NAME_NOMBRE_PILA + " TEXT," +
                        BaseDatosContract.usuarioTable.COLUMN_NAME_GENERO + " TEXT," +
                        BaseDatosContract.usuarioTable.COLUMN_NAME_TECNOLOGIAS + " TEXT," +
                        BaseDatosContract.usuarioTable.COLUMN_NAME_INSTITUCION + " TEXT," +
                        BaseDatosContract.usuarioTable.COLUMN_NAME_NOTIFICACIONES + " INTEGER," +
                        BaseDatosContract.usuarioTable.COLUMN_NAME_PUBLICIDAD + " INTEGER," +
                        BaseDatosContract.usuarioTable.COLUMN_NAME_FECHA_NACIMIENTO + " INTEGER" +
                        " )";
        db.execSQL(query_crear_tablas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
