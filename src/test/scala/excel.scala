import java.io.FileOutputStream

import org.apache.poi.hssf.usermodel.{HSSFCellStyle, HSSFWorkbook}
import org.apache.poi.hssf.util.HSSFColor

/**
 * Project Name:ISAPURL
 * Package Name:
 * Date:15-1-28 上午11:24
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object excel extends App{




  def createWorkBook(): Unit ={
    //创建excel工作簿
    val wb = new HSSFWorkbook()

    // 创建字体样式
    val font = wb.createFont();
    font.setColor(HSSFColor.RED.index);


    // 创建单元格样式
    val style = wb.createCellStyle()
    style.setFillForegroundColor(HSSFColor.RED.index)
    style.setFillBackgroundColor(HSSFColor.RED.index)
    style.setFont(font)


    //创建第一个sheet（页），命名为 new sheet
    val sheet = wb.createSheet("new sheet")
    //Row 行
    //Cell 方格
    // Row 和 Cell 都是从0开始计数的

    // 创建一行，在页sheet上
    val row = sheet.createRow(0);
    // 在row行上创建一个方格
    val cell = row.createCell(0);
    //设置方格的显示
    cell.setCellValue(2);
    cell.setCellStyle(style)

    // Or do it on one line.
    row.createCell(1).setCellValue(1.2)
    row.createCell(2).setCellValue("This is a string 速度反馈链接")
    row.createCell(3).setCellValue(true)

    //创建一个文件 命名为workbook.xls
    val fileOut = new FileOutputStream("workbook.xls")
    // 把上面创建的工作簿输出到文件中
    wb.write(fileOut)
    //关闭输出流
    fileOut.close()
  }

  createWorkBook()

}
