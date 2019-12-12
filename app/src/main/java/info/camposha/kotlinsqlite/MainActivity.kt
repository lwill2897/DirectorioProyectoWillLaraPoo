package info.camposha.kotlinsqlite

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //In Kotlin `var` is used to declare a mutable variable. On the other hand
    //`internal` means a variable is visible within a given module.
    private var dbHelper = DatabaseHelper(this)

    /**
     * Creemos una función para mostrar el mensaje Toast
     */
    private fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    /**
     * Creemos una función para mostrar un diálogo de alerta con diálogo de datos
     */
    private fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    /**
     * Creemos un método para borrar nuestros textos de edición
     */
    private fun clearEditTexts(){
        nameTxt.setText("")
        DescriptionTxt.setText("")
        SpecializationTxt.setText("")
        idTxt.setText("")
    }

    /**
     * Anulemos nuestro método onCreate.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleInserts()
        handleUpdates()
        handleDeletes()
        handleViewing()
    }

    /**
     * Cuando se hace clic en nuestro botón handleInserts.
     */
    private fun handleInserts() {
        insertBtn.setOnClickListener {
            try {
                dbHelper.insertData(nameTxt.text.toString(),DescriptionTxt.text.toString(),
                        SpecializationTxt.text.toString())
                clearEditTexts()
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    /**
     * Cuando se hace clic en nuestro botón de datos de handleUpdates
     */
    private fun handleUpdates() {
        updateBtn.setOnClickListener {
            try {
                val isUpdate = dbHelper.updateData(idTxt.text.toString(),
                        nameTxt.text.toString(),
                        DescriptionTxt.text.toString(), SpecializationTxt.text.toString())
                if (isUpdate)
                    showToast("Data Updated Successfully")
                else
                    showToast("Data Not Updated")
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    /**
     * Cuando se hace clic en nuestro botón handleDeletes
     */
    private fun handleDeletes(){
        deleteBtn.setOnClickListener {
            try {
                dbHelper.deleteData(idTxt.text.toString())
                clearEditTexts()
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    /**
     * Cuando se hace clic en Ver todo
     */
    private fun handleViewing() {
        viewBtn.setOnClickListener(
                View.OnClickListener {
                    val res = dbHelper.allData
                    if (res.count == 0) {
                        showDialog("Error", "No Data Found")
                        return@OnClickListener
                    }

                    val buffer = StringBuffer()
                    while (res.moveToNext()) {
                        buffer.append("ID :" + res.getString(0) + "\n")
                        buffer.append("NAME :" + res.getString(1) + "\n")
                        buffer.append("DESCRIPTION :" + res.getString(2) + "\n")
                        buffer.append("SPECIALIZATION :" + res.getString(3) + "\n\n")
                    }
                    showDialog("Data Listing", buffer.toString())
                }
        )
    }
}
//end