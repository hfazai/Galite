/*
 * Copyright (c) 2013-2021 kopiLeft Services SARL, Tunis TN
 * Copyright (c) 1990-2021 kopiRight Managed Solutions GmbH, Wien AT
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

package org.kopi.galite.visual.util.ipp

import java.io.IOException

class IPPHttpHeader {

  constructor(printerName: String, contentLength: Int) {
    this.name = printerName
    this.size = contentLength
  }

  constructor(inputStream: IPPInputStream) {
    var line = inputStream.readLine()

    if (line != null) {
      val lineFields = line.split(" ".toRegex()).toTypedArray()
      if (lineFields.size < 2 ||
              lineFields[1].toInt() != HTTP_OK) {
        throw IOException("Http error")
      }
    } else {
      throw IOException("Http error")
    }

    while (line != null && line.isNotEmpty()) {
      line = inputStream.readLine()
      if (IPP.DEBUG) {
        println(line)
      }
    }
  }

  // --------------------------------------------------------------------
  // ACCESSORS
  // --------------------------------------------------------------------

  fun write(os: IPPOutputStream) {
    os.writeString("POST $name HTTP/1.0\r\n")
    os.writeString("Content-type: application/ipp\r\n")
    os.writeString("Host: " + "localhost" + "\r\n")
    os.writeString("Content-length: $size\r\n")
    os.writeString("\r\n")
  }

  companion object {
    private const val HTTP_OK = 200
  }

  private var name: String? = null
  private var size = 0
}
