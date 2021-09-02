package diego.guinea.desarrolloscursos.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import diego.guinea.desarrolloscursos.R
import kotlinx.android.synthetic.main.home_activity.*

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        setUp()
    }

    private fun setUp() {
        val email = intent.getStringExtra("email")
        val provider = intent.getStringExtra("provider")

        buttonLogOut.setOnClickListener {
            val sp = getSharedPreferences("user", Context.MODE_PRIVATE)
            with(sp.edit()){
                putString("active", "false")
                apply()
            }
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}