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
package org.kopi.galite.tests.examples

import java.util.Locale

import org.kopi.galite.demo.Application
import org.kopi.galite.domain.BOOL
import org.kopi.galite.domain.DATETIME
import org.kopi.galite.domain.DECIMAL
import org.kopi.galite.domain.IMAGE
import org.kopi.galite.domain.INT
import org.kopi.galite.domain.MONTH
import org.kopi.galite.domain.STRING
import org.kopi.galite.domain.TIME
import org.kopi.galite.domain.WEEK
import org.kopi.galite.form.dsl.DictionaryForm
import org.kopi.galite.form.dsl.FormBlock

class SimpleForm : DictionaryForm() {
  override val locale = Locale.UK
  override val title = "Training"
  val action = menu("Action")
  val autoFill = actor(
    ident = "Autofill",
    menu = action,
    label = "Autofill",
    help = "Autofill",
  )

  val block = insertBlock(TraineeshipWithAllFieldTypes())
}

class TraineeshipWithAllFieldTypes : FormBlock(1, 1, "Training") {
  val t = table(Training)

  val trainingID = visit(domain = INT(25), position = at(1, 1)) {
    label = "training ID"
    help = "training ID"
    columns(t.id)
  }
  val trainingName = visit(domain = STRING(50), position = at(2, 1)) {
    label = "training Name"
    help = "training Name"
    columns(t.trainingName) {
      priority = 1
    }
  }
  val trainingType = visit(domain = Type, position = follow(trainingName)) {
    label = "training Type"
    help = "training Type"
    columns(t.type) {
      priority = 1
    }
  }
  val trainingPrice = visit(domain = DECIMAL(10, 5), position = at(3, 1)) {
    label = "training Price"
    help = "training Price"
    columns(t.price) {
      priority = 1
    }
  }
  val active = visit(domain = BOOL, position = at(4, 1)) {
    label = "active?"
    help = "active"
    columns(t.active) {
      priority = 1
    }
  }
  val date = visit(domain = DATETIME, position = at(5, 1)) {
    label = "Date"
    help = "The Date"
  }
  val week = visit(domain = WEEK, position = at(6, 1)) {
    label = "Week"
    help = "The Week"
  }
  val month = visit(domain = MONTH, position = at(7, 1)) {
    label = "Month"
    help = "The Month"
  }
  val time= visit(domain = TIME, position = at(8, 1)) {
    label = "Time"
    help = "The Time"
  }
  val photo = visit(domain = IMAGE(100, 100), position = at(9, 1)) {
    label = "photo"
    help = "photo"
    columns(t.photo)
  }
  val informations = visit(domain = STRING(80, 50, 10, true), position = at(10, 1)) {
    label = "training informations"
    help = "The training informations"
    columns(t.informations) {
      priority = 1
    }
  }
}

fun main() {
  Application.runForm(formName = SimpleForm())
}
