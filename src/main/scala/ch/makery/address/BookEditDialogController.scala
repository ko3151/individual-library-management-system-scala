package ch.makery.address

import ch.makery.address.model.Book
import scalafx.scene.control.{TextField, TableColumn, Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import scalafx.event.ActionEvent

@sfxml
class BookEditDialogController (
    private val idField : TextField,
    private val authorField : TextField,
    private val titleField : TextField,
    private val statusField : TextField,
){
  var dialogStage : Stage  = null
  private var _book : Book = null
  var okClicked = false

  def book = _book
  def book_=(x : Book) {
        _book = x
        idField.text = _book.bID.value
        authorField.text  = _book.author.value
        titleField.text    = _book.title.value
        statusField.text= _book.status.value
  }

  def handleOk(action :ActionEvent){
     if (isInputValid()) {
        _book.bID <== idField.text
        _book.author <== authorField.text
        _book.title <== titleField.text
        _book.status <== statusField.text

        okClicked = true;
        dialogStage.close()
    }
  }

  def handleCancel(action :ActionEvent) {
        dialogStage.close();
  }

  def nullChecking (x : String) = x == null || x.length == 0

  def isInputValid() : Boolean = {
    var errorMessage = ""

    if (nullChecking(idField.text.value))
      errorMessage += "No valid id!\n"
    if (nullChecking(authorField.text.value))
      errorMessage += "No valid author!\n"
    if (nullChecking(titleField.text.value))
      errorMessage += "No valid title!\n"
    if (nullChecking(statusField.text.value))
      errorMessage += "No valid status!\n"

    if (errorMessage.length() == 0) {
        return true;
    } else {
        // Show the error message.
        val alert = new Alert(Alert.AlertType.Error){
          initOwner(dialogStage)
          title = "Invalid Fields"
          headerText = "Please correct invalid fields"
          contentText = errorMessage
        }.showAndWait()
        return false;
    }
   }
} 
