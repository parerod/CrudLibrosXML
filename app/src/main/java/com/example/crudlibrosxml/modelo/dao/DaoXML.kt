package com.example.crudlibrosxml.modelo.dao

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.crudlibrosxml.Libro
import com.example.crudlibrosxml.LibroHandlerXML
import com.example.crudlibrosxml.Libros
import com.example.crudlibrosxml.ServiceModelView
import org.simpleframework.xml.core.Persister
import java.io.*
import javax.xml.parsers.SAXParserFactory

class DaoXML(private val context: Context, private val svm : ServiceModelView) {

    fun procesarArchivoXMLSAX() {
        try {
            val factory = SAXParserFactory.newInstance()
            val parser = factory.newSAXParser()
            val handler = LibroHandlerXML()

            val inputStream = context.assets.open("libros.xml")
            parser.parse(inputStream, handler)

            // Accede a la lista de profesores desde handler.profesores
            handler.libros.forEach {
                Log.d("SAX", "Libros: ${it.nombre}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun procesarArchivoAssetsXML() {
        val serializer = Persister()
        var inputStream: InputStream? = null
        var reader: InputStreamReader? = null

        try {
            inputStream = context.assets.open("libros.xml")
            reader = InputStreamReader(inputStream)
            val librosListType = serializer.read(Libros::class.java, reader, false)
            svm.libros.addAll(librosListType.libro)
        } catch (e: Exception) {
            // Manejo de errores
            e.printStackTrace()
        } finally {
            // Cerrar inputStream y reader
            try {
                reader?.close()
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

    fun addLibro(libro: Libro) {
        try {
            val serializer = Persister()
            svm.libros.add(libro)
            val librosList = Libros(svm.libros)
            val outputStream = context.openFileOutput("libros.xml", AppCompatActivity.MODE_PRIVATE)
            serializer.write(librosList, outputStream)
            outputStream.close() // Asegúrate de cerrar el outputStream después de escribir
        } catch (e: Exception) {
            e.printStackTrace() // Manejo de errores adecuado
        }
    }
    fun copiarArchivoDesdeAssets() {
        val nombreArchivo = "libros.xml"
        val archivoEnAssets = context.assets.open(nombreArchivo)
        val archivoInterno = context.openFileOutput(nombreArchivo, AppCompatActivity.MODE_PRIVATE)

        archivoEnAssets.copyTo(archivoInterno)
        archivoEnAssets.close()
        archivoInterno.close()
    }

    fun ProcesarArchivoXMLInterno() {
        val nombreArchivo = "libros.xml"
        val serializer = Persister()

        try {
            // Abrir el archivo para lectura
            val file = File(context.filesDir, nombreArchivo)
            val inputStream = FileInputStream(file)
            val librosList = serializer.read(Libros::class.java, inputStream)
            svm.libros.addAll(librosList.libro)
            inputStream.close()
        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

}