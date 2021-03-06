package diego.guinea.desarrolloscursos

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.core.graphics.drawable.toDrawable

fun Context.showRegisterDialog(): Dialog {
    val progressDialog = Dialog(this)
    progressDialog.let {
        it.show()
        it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        it.setContentView(R.layout.progress_dialog)
        it.setCancelable(false)
        it.setCanceledOnTouchOutside(false)
        return it
    }
}