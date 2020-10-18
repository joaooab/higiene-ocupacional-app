package br.com.joaoov.data

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

/*
    https://medium.com/@shahadat.shaki/create-an-excel-file-programmatically-in-android-d989f00e809f
 */

object ReportUtil {

    var workbook: Workbook = XSSFWorkbook()
    var sheet: Sheet = workbook.createSheet("Users")

}