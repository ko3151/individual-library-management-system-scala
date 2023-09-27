package ch.makery.address

import scalafxml.core.macros.sfxml
import scalafx.event.ActionEvent
import scalafx.scene.input.MouseEvent

@sfxml //tell compiler that this is marked as controller class
class MenuPageController(){  
     def toBookPage(action: MouseEvent) { 
        MainApp.showView("BookForm.fxml")
     }

     def toMemberPage(action: MouseEvent) {
        MainApp.showView("MemberForm.fxml")
     }

      def toBorrowReturnPage(action: MouseEvent) {
        MainApp.showView("BorrowReturnOverview.fxml")
     }

     def handleExit(action : ActionEvent) = {
        System.exit(0)
    }
}