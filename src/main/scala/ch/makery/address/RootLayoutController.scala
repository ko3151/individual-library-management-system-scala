package ch.makery.address

import scalafxml.core.macros.sfxml 

@sfxml
class RootLayoutController() {
    def handleClose() {
        System.exit(0)
    }
     
    def handleMenu(){
        MainApp.showView("MenuPage.fxml") 
    }

}