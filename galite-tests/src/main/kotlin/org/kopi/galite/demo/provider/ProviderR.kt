/*
 * Copyright (c) 2013-2020 kopiLeft Services SARL, Tunis TN
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
package org.kopi.galite.demo.provider

import java.util.Locale

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

import org.kopi.galite.demo.Provider
import org.kopi.galite.domain.INT
import org.kopi.galite.domain.STRING
import org.kopi.galite.form.dsl.Key
import org.kopi.galite.report.FieldAlignment
import org.kopi.galite.report.Report
import org.kopi.galite.report.VCellFormat
import org.kopi.galite.report.VReport

/**
 * Provider Report
 */
class ProviderR : Report() {
  override val locale = Locale.UK

  override val title = "Providers_Report"

  val action = menu("Action")

  val csv = actor(
          ident = "CSV",
          menu = action,
          label = "CSV",
          help = "CSV Format",
  ) {
    key = Key.F8          // key is optional here
    icon = "exportCsv"  // icon is optional here
  }

  val xls = actor(
          ident = "XLS",
          menu = action,
          label = "XLS",
          help = "Excel (XLS) Format",
  ) {
    key = Key.SHIFT_F8          // key is optional here
    icon = "exportXlsx"  // icon is optional here
  }

  val xlsx = actor(
          ident = "XLSX",
          menu = action,
          label = "XLSX",
          help = "Excel (XLSX) Format",
  ) {
    key = Key.SHIFT_F8          // key is optional here
    icon = "export"  // icon is optional here
  }

  val pdf = actor(
          ident = "PDF",
          menu = action,
          label = "PDF",
          help = "PDF Format",
  ) {
    key = Key.F9          // key is optional here
    icon = "exportPdf"  // icon is optional here
  }

  val cmdCSV = command(item = csv) {
    action = {
      model.export(VReport.TYP_CSV)
    }
  }

  val cmdPDF = command(item = pdf) {
    action = {
      model.export(VReport.TYP_PDF)
    }
  }

  val cmdXLS = command(item = xls) {
    action = {
      model.export(VReport.TYP_XLS)
    }
  }

  val cmdXLSX = command(item = xlsx) {
    action = {
      model.export(VReport.TYP_XLSX)
    }
  }

  val nameProvider = field(STRING(50)) {
    label = "Name"
    help = "The provider name"
    align = FieldAlignment.LEFT
    format {
      object : VCellFormat() {
        override fun format(value: Any?): String {
          return (value as String).toUpperCase()
        }
      }
    }
  }

  val tel = field(INT(25)) {
    label = "Phone number"
    help = "The provider phone number"
    align = FieldAlignment.LEFT
  }

  val description = field(STRING(255)) {
    label = "Description"
    help = "The provider description"
    align = FieldAlignment.LEFT
    format {
      object : VCellFormat() {
        override fun format(value: Any?): String {
          return (value as String).toUpperCase()
        }
      }
    }
  }

  val address = field(STRING(70)) {
    label = "Address"
    help = "The provider address"
    align = FieldAlignment.LEFT
  }

  val zipCode = field(INT(50)) {
    label = "Zip code"
    help = "The provider zip code"
    align = FieldAlignment.LEFT
  }

  val providers = Provider.selectAll()

  init {
    transaction {
      providers.forEach { result ->
        add {
          this[nameProvider] = result[Provider.nameProvider]
          this[description] = result[Provider.description]
          this[tel] = result[Provider.tel]
          this[zipCode] = result[Provider.zipCode]
          this[address] = result[Provider.address]
        }
      }
    }
  }
}
