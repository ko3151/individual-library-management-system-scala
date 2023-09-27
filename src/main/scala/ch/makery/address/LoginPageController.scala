package ch.makery.address

import scalafxml.core.macros.sfxml
import scalafx.scene.control.{TextField, Alert}

@sfxml
class LoginPageController(
    private val loginField : TextField,
    private val passwordField : TextField  
){  

    def handleStart() { 
 
        var username = loginField.text().toString
        var password = passwordField.text().toString

        if (username=="" || password==""){

            val alert = new Alert(Alert.AlertType.Error){
            title = "Invalid username/password"
            headerText = "Please enter username and password !"
            }.showAndWait()
            
        }else if (!(username == "admin" && password =="admin123")){
            val alert = new Alert(Alert.AlertType.Error){
            title = "Incorrect username/password"
            headerText = "Please re-enter !"
            }.showAndWait()
  
        }else{
            MainApp.showView("MenuPage.fxml") 

        }
    }
}