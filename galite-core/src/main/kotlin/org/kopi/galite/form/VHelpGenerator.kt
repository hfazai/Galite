/*
 * Copyright (c) 2013-2020 kopiLeft Services SARL, Tunis TN
 * Copyright (c) 1990-2020 kopiRight Managed Solutions GmbH, Wien AT
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

package org.kopi.galite.form

import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.io.Serializable

import org.kopi.galite.base.Utils
import org.kopi.galite.visual.VCommand
import org.kopi.galite.visual.VlibProperties
import org.kopi.galite.visual.VHelpGenerator

/**
 * This class implements a pretty printer
 */
open class VHelpGenerator : VHelpGenerator(), VConstants, Serializable {

  /**
   * prints a compilation unit
   */
  open fun helpOnForm(name: String,
                      commands: Array<VCommand>?,
                      blocks: Array<VBlock>?,
                      title: String,
                      help: String?,
                      code: String): String? {
    return try {
      val file = Utils.getTempFile(name, "htm")
      
      printer = PrintWriter(BufferedWriter(OutputStreamWriter(FileOutputStream(file), "UTF-8")))
      printer.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//DE\">")
      printer.println("<!--Generated by help generator-->")
      printer.println("<TITLE>$name</TITLE>")
      printer.println("<META NAME=\"description\" CONTENT=\"$name\">")
      printer.println("<META NAME=\"keywords\" CONTENT=\"$name\">")
      printer.println("<META NAME=\"resource-type\" CONTENT=\"document\">")
      printer.println("<META NAME=\"distribution\" CONTENT=\"global\">")
      printer.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=utf-8\">")
      printer.println("</HEAD>")
      printer.println("<BODY BGCOLOR=#FFFFFF>")
      printer.println("<CENTER><H1>$title</H1></CENTER>")
      if (help != null) {
        printer.println("<P>$help</P>")
      }
      commands?.let { helpOnCommands(it) }
      if (blocks != null) {
        if (blocks.size != 1) {
          printer.println("<TABLE border=0 cellspacing=3 cellpadding=2>")
          blocks.forEach {
            val block = it
            printer.println("<TR>")
            printer.println("<TD><pre>    </pre>")
            printer.println("</TD>")
            block.helpOnBlock(this)
            printer.println("</TR>")
          }
          printer.println("</TABLE> ")
        } else {
          val block = blocks[0]
          block.helpOnBlock(this)
        }
      }
      printer.println("<BR>")
      printer.println("<ADDRESS>")
      printer.println("<I>kopiLeft Services SARL, Tunis TN</I><BR>")
      printer.println("<I>kopiRight Managed Solutions GmbH, Wien AT</I><BR>")
      val version: Array<String> = Utils.getVersion()

      version.forEach {
        printer.println("<I>$it</I><BR>")
      }
      printer.println("</ADDRESS>")
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
  open fun helpOnBlock(formCode: String,
                       title: String,
                       help: String?,
                       commands: Array<VCommand>?,
                       fields: Array<VField?>?,
                       alone: Boolean) {
    if (!alone) {
      printer.println("<TD><H2>$title</H2>")
    }
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
    fields!!.forEach {
      it!!.helpOnField(this)
    }
    printer.println("</DL>")
    printer.println("</TD>")
    printer.println("</TR>")
    printer.println("</TABLE>")
    if (!alone) {
      printer.println("</TD>")
    }
  }

  /**
   * printlns a compilation unit
   */
  open fun helpOnField(blockTitle: String,
                       pos: Int,
                       label: String?,
                       anchor: String?,
                       help: String?) {
    if (label == null) {
      return
    }
    printer.println("<DT>")
    printer.println("<A NAME=\"" + (blockTitle.replace(' ', '_')
            + anchor!!.replace(' ', '_')) + "\"></A>")
    printer.println("<H2>$label</H2>")
    printer.println("<DD>")
    if (help != null) {
      printer.println("<P>$help</P>")
    }
  }

  open fun helpOnType(modeName: String,
                      modeDesc: String,
                      typeName: String,
                      typeDesc: String,
                      names: Array<String>?) {
    printer.println("<TABLE valign=\"top\">")
    printer.println("<TR>")
    printer.println("<TD><B>")
    printer.println(VlibProperties.getString("Mode"))
    printer.println("</B></TD>")
    printer.println("<TD><I>")
    printer.println(modeName)
    printer.println("</I></TD>")
    printer.println("<TD>")
    printer.println(modeDesc)
    printer.println("</TD>")
    printer.println("</TR>")
    printer.println("<TR>")
    printer.println("<TD><B>")
    printer.println(VlibProperties.getString("Type"))
    printer.println("</B></TD>")
    printer.println("<TD><I>")
    printer.println(typeName)
    printer.println("</I></TD>")
    printer.println("<TD>")
    printer.println(typeDesc)
    printer.println("</TD>")
    printer.println("</TR>")
    printer.println("</TABLE>")
    names?.let { printCodeList(it) }
  }

  fun printCodeList(names: Array<String>) {
    printer.println("<OL>")
    names.forEach {
      printer.println("<LI>$it")
    }
    printer.println("</OL>")
  }

  open fun helpOnFieldCommand(commands: Array<VCommand>?) {
    commands?.let { helpOnCommands(it) }
  }
}
