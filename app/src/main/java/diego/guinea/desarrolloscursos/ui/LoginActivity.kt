package diego.guinea.desarrolloscursos.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import diego.guinea.desarrolloscursos.R
import kotlinx.android.synthetic.main.loguin_activity.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loguin_activity)
        setUp()
    }

    private fun setUp() {
        val sp = getSharedPreferences("user", Context.MODE_PRIVATE)
        if (sp.getString("active", "") == "true"){
            firebaseAuth(sp.getString("email","").toString(),sp.getString("password","").toString())
        }else{
            buttonsClick(sp)
        }
    }

    private fun buttonsClick(sp: SharedPreferences) {
        buttonEntrar.setOnClickListener {

            if (editTextEmail.text.isNotEmpty() &&
                editTextTextPassword.text.isNotEmpty()
            ) {
                rememberUser(sp)
                firebaseAuth(editTextEmail.text.toString(), editTextTextPassword.text.toString())
            } else {
                showAlert()
            }
        }

        buttonRegistrar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun firebaseAuth(email: String, password: String) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(
                email,password
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                } else {
                    showAlert()
                }
            }
    }

    private fun rememberUser(sp: SharedPreferences) {
        val email = editTextEmail.text.toString()
        val password = editTextTextPassword.text.toString()

        with(sp.edit()){
            putString("email", email)
            putString("password", password)
            putString("active", "true")

            apply()
        }
        showHome(email, ProviderType.BASIC)
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
        finish()
    }

}