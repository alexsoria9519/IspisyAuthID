package com.example.testapiipidymethods.IDS

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class Consumo {

    constructor()


    fun getDataFoto(data: String, bitImage: Bitmap, context: Context){
        val util = Utils(context)
        Log.e("Data Desde Clase Kotlin", data)
        util.saveDataPhoto(data)


        // TODO check this implementation for save in the file system
        val file_path = Environment.getExternalStorageDirectory().absolutePath +
                "/PhysicsSketchpad"
        val dir = File(file_path)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, "scan" + ".png")
        val fOut = FileOutputStream(file)
        bitImage.compress(Bitmap.CompressFormat.PNG, 85, fOut)
        fOut.flush()
        fOut.close()

    }




}