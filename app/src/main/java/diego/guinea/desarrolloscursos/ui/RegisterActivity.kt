package diego.guinea.desarrolloscursos.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import diego.guinea.desarrolloscursos.R
import kotlinx.android.synthetic.main.register_activity.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        setUp()
    }

    private fun setUp() {
        buttonRegistro.setOnClickListener {
            if (editTextTextEmailAddress.text.isNotEmpty() &&
                    editTextPhone.text.isNotEmpty() &&
                    editTextTextPassword2.text.isNotEmpty() &&
                    editTextTextPassword3.text.isNotEmpty() &&
                    editTextTextPersonName.text.isNotEmpty() &&
                    editTextTextPassword2.text == editTextTextPassword3.text){

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(editTextTextEmailAddress.text.toString(),
                        editTextTextPassword2.text.toString()).addOnCompleteListener {
                            if (it.isSuccessful){showHome(it.result?.user?.email ?: "", ProviderType.BASIC)}
                            else{showAlert()}
                    }
            }else{
                showAlert()
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se a producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showHome(email: String, provider: ProviderType){
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(intent)
    }

}