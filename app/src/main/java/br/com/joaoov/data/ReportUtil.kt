package br.com.joaoov.data

import org.docx4j.openpackaging.exceptions.Docx4JException
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.ContentAccessor
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import javax.xml.bind.JAXBElement

//https://www.javacodegeeks.com/2012/07/java-word-docx-documents-with-docx4j.html

object ReportUtil {

    @Throws(Docx4JException::class, FileNotFoundException::class)
    private fun getTemplate(name: String): WordprocessingMLPackage? {
        return WordprocessingMLPackage.load(FileInputStream(File(name)))
    }

    private fun getAllElementFromObject(
        obj: Any,
        toSearch: Class<*>
    ): List<Any>? {
        var obj = obj
        val result: MutableList<Any> = ArrayList()
        if (obj is JAXBElement<*>) obj = obj.value
        if (obj.javaClass == toSearch) result.add(obj) else if (obj is ContentAccessor) {
            val children = obj.content
            for (child in children) {
                result.addAll(getAllElementFromObject(child, toSearch)!!)
            }
        }
        return result
    }
}