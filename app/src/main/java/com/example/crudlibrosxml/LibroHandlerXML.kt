package com.example.crudlibrosxml

import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler

class LibroHandlerXML : DefaultHandler() {

    private val cadena = StringBuilder() //Concatena Cadenas
    private var libro: Libro? = null
    var libros: MutableList<Libro> = mutableListOf()

    @Throws(SAXException::class)
    override fun startDocument() {
        cadena.clear()
        libros = mutableListOf()
        println("startDocument")
    }

    @Throws(SAXException::class)
    override fun startElement(uri: String, nombreLocal: String, nombre: String, attributes: Attributes) {
        cadena.setLength(0)
        if (nombre == "libro") {
            libro = Libro()
            //Poner todos los atributos de la entidad del XML
            libro?.nombre = attributes.getValue("nombre")
            libro?.autor = attributes.getValue("autor")
        }

        println("startElement: $nombreLocal $nombre")
    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        cadena.append(ch, start, length)
        println("dato final: $cadena")
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, nombreLocal: String, nombre: String) {
        when (nombre) {
            "isbn" -> libro?.isbn = cadena.toString()
            "paginas" -> libro?.paginas = cadena.toString().toInt()
            "leido" -> libro?.leido = cadena.toString().toBoolean()
        }

        println("endElement: $nombreLocal $nombre")
    }

    @Throws(SAXException::class)
    override fun endDocument() {
        println("endDocument")
    }

}