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
     * Let's create a function to show Toast message
     */
    private fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    /**
     * Let's create a function to show an alert dialog with data dialog.
     */
    private fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    /**
     * Let's create a method to clear our edittexts
     */
    private fun clearEditTexts(){
        nameTxt.setText("")
        DescriptionTxt.setText("")
        SpecializationTxt.setText("")
        idTxt.setText("")
    }

    /**
     * Let's override our onCreate method.
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
     * When our handleInserts button is clicked.
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
     * When our handleUpdates data button is clicked
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
     * When our handleDeletes button is clicked
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
     * When our View All is clicked
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