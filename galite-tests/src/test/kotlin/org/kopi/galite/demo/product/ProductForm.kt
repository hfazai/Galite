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
package org.kopi.galite.demo.product

import org.kopi.galite.demo.Product
import org.kopi.galite.demo.desktop.Application
import org.kopi.galite.domain.CodeDomain
import org.kopi.galite.domain.Domain
import org.kopi.galite.form.dsl.FormBlock
import org.kopi.galite.form.dsl.Key
import org.kopi.galite.form.dsl.ReportSelectionForm
import org.kopi.galite.report.Report
import java.util.Locale

object ProductForm : ReportSelectionForm() {
  override val locale = Locale.ENGLISH
  override val title = "product form"
  val page = page("page")
  val action = menu("Action")
  val report = actor(
          ident = "report",
          menu = action,
          label = "CreateReport",
          help = "Create report",
  ) {
    key = Key.F8          // key is optional here
    icon = "preview"  // icon is optional here
  }

  val block = insertBlock(BlockProduct, page) {
    command(item = report) {
      action = {
        createReport(BlockProduct)
      }
    }
  }

  override fun createReport(): Report {
    return ProductR
  }
}

object BlockProduct : FormBlock(1, 1, "product block") {
  val u = table(Product)

  val idPdt = hidden(domain = Domain<Int>(20)) {
    label = "product id"
    help = "The product id"
    columns(u.idPdt)
  }
  val designation = mustFill(domain = Domain<String>(50), position = at(1, 1)) {
    label = "product designation"
    help = "The product designation"
    columns(u.designation)
  }
  val category = mustFill(domain = Category, position = at(2, 1)) {
    label = "product category"
    help = "The product category"
    columns(u.category)
  }
  val taxName = mustFill(domain = Tax, position = at(3, 1)) {
    label = "Product tax name"
    help = "The product tax name"
    columns(u.taxName)
  }
  val price = visit(domain = Domain<Int>(20), position = at(4, 1)) {
    label = "product price"
    help = "The product price"
    columns(u.price)
  }
/*
  val photo = visit(domain = Domain<Image>(width = 100, height = 100), position = at(5, 1)) {
    label = "product image"
    help = "The product image"
    columns(u.photo)
  }
*/
}

object Category: CodeDomain<String>() {
  init {
    "shoes" keyOf "cat 1"
    "shirts" keyOf "cat 2"
    "glasses" keyOf  "cat 3"
    "pullovers" keyOf "cat 4"
    "jeans" keyOf "cat 5"
  }
}

object Tax: CodeDomain<String>() {
  init {
    "Taux 19%"  keyOf "tax 1"
    "Taux 9%" keyOf  "tax 2"
    "Taux 13%" keyOf "tax 3"
    "Taux 22%" keyOf "tax 4"
    "Taux 11%"  keyOf "tax 5"
  }
}

fun main() {
  Application.runForm(formName = ProductForm)
}
