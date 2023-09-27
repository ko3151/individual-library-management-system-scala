package ch.makery.address

import ch.makery.address.model.Member
import scalafx.scene.control.{TextField, TableColumn, Label, Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import scalafx.event.ActionEvent

@sfxml
class MemberEditDialogController (
    private val memberIDField : TextField,
    private val nameField : TextField,
    private val mobileField : TextField,
    private val emailField : TextField
){
  var dialogStage : Stage  = null
  private var _member : Member = null
  var okClicked = false

  def member = _member
  def member_=(x : Member) {
        _member = x
        memberIDField.text  = _member.mID.value
        nameField.text = _member.name.value
        mobileField.text    = _member.mobile.value
        emailField.text= _member.email.value
  }

  def handleOk(action :ActionEvent){
     if (isInputValid()) {
        _member.mID <== memberIDField.text
        _member.name <== nameField.text
        _member.mobile <== mobileField.text
        _member.email <== emailField.text

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

    if (nullChecking(memberIDField.text.value))
      errorMessage += "No valid memberID!\n"
    if (nullChecking(nameField.text.value))
      errorMessage += "No valid name!\n"  
    if (nullChecking(mobileField.text.value))
      errorMessage += "No valid mobile!\n"
    if (nullChecking(emailField.text.value))
      errorMessage += "No valid email!\n"

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
