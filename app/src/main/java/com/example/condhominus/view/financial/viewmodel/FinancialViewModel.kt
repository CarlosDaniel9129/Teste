package com.example.condhominus.view.financial.viewmodel

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.example.condhominus.repository.FinancialRepository
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FinancialViewModel : ViewModel() {

    private val repository = FinancialRepository()

    fun getBillet(context: Context): File {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "boleto.pdf")
        try {
            val fos = FileOutputStream(file)
            fos.write(repository.getBillet())
            fos.close()
            openPdfInBrowser(context, file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    private fun openPdfInBrowser(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Nenhum aplicativo disponível para visualizar PDFs", Toast.LENGTH_SHORT).show()
        }
    }

}
