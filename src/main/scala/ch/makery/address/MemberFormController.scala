package ch.makery.address

import ch.makery.address.model.Member
import scalafx.scene.control.{TableView, TableColumn, Label,Alert}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.{StringProperty} 
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.input.MouseEvent

@sfxml
class MemberFormController(
  
    private val memberTable : TableView[Member],
    private val memberIDColumn : TableColumn[Member, String],
    private val nameColumn : TableColumn[Member, String],
     private val memberIDLabel : Label,  
    private val nameLabel : Label,  
    private val mobileLabel : Label,
    private val emailLabel : Label
      
    ) {
    // initialize Table View display contents model
    memberTable.items = Member.memberData

    // initialize columns's cell values
    memberIDColumn.cellValueFactory = {x => x.value.mID}
    nameColumn.cellValueFactory = {_.value.name}

    private def showMemberDetails (member : Option[Member]) = {
        member match {
            case Some(member) =>
            // Fill the labels with info from the book object.
            memberIDLabel.text <== member.mID
            nameLabel.text <== member.name
            mobileLabel.text <== member.mobile
            emailLabel.text <== member.email            
            
            case None =>
            // Member is null, remove all the text.
            memberIDLabel.text.unbind()
            nameLabel.text.unbind()
            mobileLabel.text.unbind()
            emailLabel.text.unbind()
            memberIDLabel.text = ""
            nameLabel.text = ""
            mobileLabel.text = ""
            emailLabel.text = ""
        }    
  }

    showMemberDetails(None)

    memberTable.selectionModel().selectedItem.onChange(
        (_, _, newValue) => showMemberDetails(Option(newValue))
    )

    def deleteMember(action : ActionEvent) = {
        val selectedIndex = memberTable.selectionModel().selectedIndex.value
        if (selectedIndex >= 0) {
            memberTable.items().remove(selectedIndex).delete()
        } else {
            // Nothing selected.
            val alert = new Alert(AlertType.Warning){
            initOwner(MainApp.stage)
            title       = "No Selection"
            headerText  = "No Member Selected"
            contentText = "Please select a member in the table."
            }.showAndWait()
        }
    }

    def addMember(action : ActionEvent) = {
        val member = new Member("","")
        val okClicked = MainApp.showMemberEditDialog(member);
            if (okClicked) {
                Member.memberData += member
                member.save()
            }
    }

    def editMember(action : ActionEvent) = {
        val selectedMember = memberTable.selectionModel().selectedItem.value
        if (selectedMember != null) {
            val okClicked = MainApp.showMemberEditDialog(selectedMember)

            if (okClicked) {
                showMemberDetails(Some(selectedMember))
                selectedMember.save()
            }

        } else {
            // Nothing selected.
            val alert = new Alert(Alert.AlertType.Warning){
            initOwner(MainApp.stage)
            title       = "No Selection"
            headerText  = "No Member Selected"
            contentText = "Please select a member in the table."
            }.showAndWait()
        }
    } 

    def handleBack(action: MouseEvent) = {
        MainApp.showView("MenuPage.fxml")
    }

    def handleExit(action : ActionEvent) = {
        System.exit(0)
    }
 
}
