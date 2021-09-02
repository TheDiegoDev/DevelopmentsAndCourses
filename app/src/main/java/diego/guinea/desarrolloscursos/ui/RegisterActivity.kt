package diego.guinea.desarrolloscursos.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import diego.guinea.desarrolloscursos.R
import kotlinx.android.synthetic.main.register_activity.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        auth = Firebase.auth
        setUp()
    }

    private fun setUp() {
        buttonRegistro.setOnClickListener {
            if (editTextRegisterEmail.text.isNotEmpty() &&
                    editTextRegisterPhone.text.isNotEmpty() &&
                    editTextRegisterPassword.text.isNotEmpty() &&
                    editTextRegsiterPasswordRepeat.text.isNotEmpty() &&
                    editTextRegisterName.text.isNotEmpty() &&
                    editTextRegisterPassword.text.toString() == editTextRegsiterPasswordRepeat.text.toString()){

                        val email = editTextRegisterEmail.text.toString()
                        val passw = editTextRegisterPassword.text.toString()

                auth.createUserWithEmailAndPassword(email, passw)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            showHome(email, ProviderType.BASIC)
                        } else {
                            showAlert()
                        }
                    }

//                FirebaseAuth.getInstance()
//                    .createUserWithEmailAndPassword(editTextTextEmailAddress.text.toString(),
//                        editTextTextPassword2.text.toString()).addOnCompleteListener {
//                            if (it.isSuccessful){showHome(it.result?.user?.email ?: "", ProviderType.BASIC)}
//                            else{showAlert()}
//                    }
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