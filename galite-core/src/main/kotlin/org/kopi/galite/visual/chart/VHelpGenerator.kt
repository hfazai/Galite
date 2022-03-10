/*
 * Copyright (c) 2013-2022 kopiLeft Services SARL, Tunis TN
 * Copyright (c) 1990-2022 kopiRight Managed Solutions GmbH, Wien AT
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.kopi.galite.visual.chart

import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.PrintWriter

import org.kopi.galite.visual.base.Utils
import org.kopi.galite.visual.visual.VCommand
import org.kopi.galite.visual.visual.VHelpGenerator

/**
 * This class implements a pretty printer
 */
class VHelpGenerator : VHelpGenerator() {
  // ----------------------------------------------------------------------
  // ACCESSORS
  // ----------------------------------------------------------------------
  /**
   * prints a compilation unit
   */
  fun helpOnChart(name: String,
                  commands: List<VCommand>?,
                  columns: Array<VColumn>,
                  help: String?): String? {
    return try {
      val file = Utils.getTempFile(name, "htm")

      printer = PrintWriter(BufferedWriter(OutputStreamWriter(FileOutputStream(file), "UTF-8")))
      printer.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//DE\">")
      printer.println("<!--Generated by kopi help generator-->")
      printer.println("<DIV CLASS=help>")
      printer.println("<TITLE>$name</TITLE>")
      printer.println("<META NAME=\"description\" CONTENT=\"$name\">")
      printer.println("<META NAME=\"keywords\" CONTENT=\"$name\">")
      printer.println("<META NAME=\"resource-type\" CONTENT=\"document\">")
      printer.println("<META NAME=\"distribution\" CONTENT=\"global\">")
      printer.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=utf-8\">")
      printer.println("</HEAD>")
      printer.println("<BODY BGCOLOR=#FFFFFF>")
      printer.println("<CENTER><H1>$name</H1></CENTER>")
      if (help != null) {
        printer.println("<P>$help</P>")
      }

      commands?.let { helpOnCommands(it) }

      printer.println("<TABLE border=\"0\" cellspacing=\"3\" cellpadding=\"2\">")
      printer.println("<TR>")
      printer.println("<TD><pre>    </pre>")
      printer.println("</TD>")
      printer.println("<TD>")
      printer.println("<DL>")
      columns.forEach { column ->
        column.helpOnColumn(this)
      }
      printer.println("</DL>")
      printer.println("</TD>")
      printer.println("</TR>")
      printer.println("</TABLE>")
      printer.println("<BR>")
      printer.println("<ADDRESS>")
      printer.println("<I>kopiLeft Services SARL, Tunis TN</I><BR>")
      printer.println("<I>kopiRight Managed Solutions GmbH, Wien AT</I><BR>")
      val version = Utils.getVersion()
      version.forEach {
        printer.println("<I>$it</I><BR>")
      }
      printer.println("</ADDRESS>")
      printer.println("</DIV>")
      printer.println("</BODY>")
      printer.println("</HTML>")
      printer.close()

      file.path
    } catch (e: IOException) {
      System.err.println("IO ERROR $e")
      null
    }
  }

  /**
   * printlns a compilation unit
   */
  fun helpOnColumn(label: String?, help: String?) {
    if (label == null) {
      return
    }
    printer.println("<DT>")
    printer.println("<H2>$label</H2>")
    printer.println("<DD>")
    if (help != null) {
      printer.println("<P>$help</P>")
    }
  }
}
