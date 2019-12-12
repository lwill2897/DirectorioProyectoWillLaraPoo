package info.camposha.kotlinsqlite

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

/**

 * Comencemos creando nuestra clase de ayuda CRUD de base de datos
 * basado en SQLiteHelper.
 */
class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    /**
     * Nuestro método onCreate ().
     * Se llama cuando se crea la base de datos por primera vez. Esto es
     * donde la creación de tablas y la población inicial de las tablas
     * debería suceder.
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,NAME TEXT,DESCRIPTION TEXT,SPECIALIZATION TEXT)")
    }

    /**
     * Creemos nuestro método onUpgrade
     * Llamado cuando la base de datos necesita ser actualizada. La implementación debería
     * use este método para descartar tablas, agregar tablas o hacer cualquier otra cosa que necesite
     * para actualizar a la nueva versión del esquema.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    /**
     * Creemos nuestro método insertData ().
     * Insertará datos a la base de datos SQLe.
     */
    fun insertData(name: String, surname: String, marks: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, surname)
        contentValues.put(COL_4, marks)
        db.insert(TABLE_NAME, null, contentValues)
    }

    /**
     * Creemos un método para actualizar una fila con nuevos valores de campo.
     */
    fun updateData(id: String, name: String, surname: String, marks: String):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, id)
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, surname)
        contentValues.put(COL_4, marks)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return true
    }

    /**
     * Creemos una función para eliminar una fila dada basada en la identificación.
     */
    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME,"ID = ?", arrayOf(id))
    }

    /**
     * La siguiente propiedad getter devolverá un cursor que contiene nuestro conjunto de datos.
     */
    val allData : Cursor
        get() {
            val db = this.writableDatabase
            return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        }

    /**
     * Creemos un objeto complementario para contener nuestros campos estáticos.
     * Un objeto complementario es un objeto que es común a todas las instancias de un determinado
     * clase.
     */
    companion object {
        const val DATABASE_NAME = "stars.db"
        const val TABLE_NAME = "star_table"
        const val COL_1 = "ID"
        const val COL_2 = "NAME"
        const val COL_3 = "GALAXY"
        const val COL_4 = "TYPE"
    }
}
//end