package com.example.crudlibrosxml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.crudlibrosxml.modelo.dao.DaoXML
import java.io.*

class MainActivity : AppCompatActivity() {

    private val svm : ServiceModelView = ServiceModelView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var daoXML = DaoXML(applicationContext, svm)

        daoXML.copiarArchivoDesdeAssets()
        daoXML.procesarArchivoAssetsXML()
        Log.d("prueba2", "probando procesado con Simple XML Framework")
        svm.libros.forEach {
            Log.d("prueba2", it.toString())
        }

        val libro=Libro("El principito","Antoine de Saint-Exupéry","9788478887200",120,false)
        daoXML.addLibro(libro)
        daoXML.ProcesarArchivoXMLInterno()
        svm.libros.forEach {
            Log.d("prueba2", it.toString())
        }

        daoXML.procesarArchivoXMLSAX()
    }


}