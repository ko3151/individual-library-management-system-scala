package ch.makery.address

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import scalafx.stage.{Stage, Modality}
import ch.makery.address.model.Book
import ch.makery.address.model.Member

object MainApp extends JFXApp {
  // transform path of RootLayout.fxml to URI for resource location.
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  // initialize the loader object.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load();
  // retrieve the root component BorderPane from the FXML 
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // initialize stage
  stage = new PrimaryStage {
    title = "Library Management System"
    icons += new Image("file:resources/images/logo-icon.png")
    scene = new Scene {
      root = roots
      //stylesheets = List(getClass.getResource("view/Library.css").toString())
    }
  }

  def showView(v: String): Unit = {
    val resource = getClass.getResource(s"view/${v}")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.center_=(roots)
  }

  showView("LoginPage.fxml")

    def showBookEditDialog(book: Book): Boolean = {
    val resource = getClass.getResourceAsStream("view/BookEditDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[BookEditDialogController#Controller]

    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
        stylesheets = List(getClass.getResource("view/Library.css").toString())
      }
    }
    control.dialogStage = dialog
    control.book = book
    dialog.showAndWait()
    control.okClicked
  } 

  def showMemberEditDialog(member: Member): Boolean = {
    val resource = getClass.getResourceAsStream("view/MemberEditDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots3  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[MemberEditDialogController#Controller]

    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots3
        stylesheets = List(getClass.getResource("view/Library.css").toString())
      }
    }
    control.dialogStage = dialog
    control.member = member
    dialog.showAndWait()
    control.okClicked
  }

}
