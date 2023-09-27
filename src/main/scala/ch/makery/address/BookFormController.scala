package ch.makery.address

import ch.makery.address.model.Book
import scalafx.scene.control.{TableView, TableColumn, Label, TextField}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.{StringProperty} 
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.input.MouseEvent
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.collections.ObservableBuffer

@sfxml
class BookFormController(
  
    private val bookTable : TableView[Book],
    private val idColumn : TableColumn[Book, String],
    private val titleColumn : TableColumn[Book, String],
    private val idLabel : Label,  
    private val authorLabel : Label,
    private val titleLabel : Label,
    private val statusLabel : Label,
    private val searchField : TextField  
    )extends Book {
    // initialize Table View display contents model
    bookTable.items = Book.bookData

    // initialize columns's cell values
    idColumn.cellValueFactory = {x => x.value.bID}
    titleColumn.cellValueFactory = {_.value.title}

    def searchBook(){
        val text = searchField.text().toString
        val bookData = new ObservableBuffer[Book]()
        bookData ++= Book.getBookByTitle(text)
        bookTable.items = bookData
        
    }

    private def showBookDetails (book : Option[Book]) = {
        book match {
            case Some(book) =>
            // Fill the labels with info from the book object.
            idLabel.text <== book.bID
            authorLabel.text <== book.author
            titleLabel.text <== book.title
            statusLabel.text <== book.status;
            
            
            case None =>
            // Book is null, remove all the text.
            idLabel.text.unbind()
            authorLabel.text.unbind()
            titleLabel.text.unbind()
            statusLabel.text.unbind()
            idLabel.text = ""
            authorLabel.text = ""
            titleLabel.text = ""
            statusLabel.text = ""
        }    
    }

    showBookDetails(None)

    bookTable.selectionModel().selectedItem.onChange(
        (_, _, newValue) => showBookDetails(Option(newValue))
    )

    def deleteBook(action : ActionEvent) = {
        val selectedIndex = bookTable.selectionModel().selectedIndex.value
        if (selectedIndex >= 0) {
            bookTable.items().remove(selectedIndex).delete()
        } else {
            // Nothing selected.
            val alert = new Alert(AlertType.Warning){
            initOwner(MainApp.stage)
            title       = "No Selection"
            headerText  = "No Book Selected"
            contentText = "Please select a book in the table."
            }.showAndWait()
        }
    }

    def addBook(action : ActionEvent) = {
        val book = new Book("","")
        val okClicked = MainApp.showBookEditDialog(book);
            if (okClicked) {
                Book.bookData += book
                book.save()
            }
    }

    def editBook(action : ActionEvent) = {
        val selectedBook = bookTable.selectionModel().selectedItem.value
        if (selectedBook != null) {
            val okClicked = MainApp.showBookEditDialog(selectedBook)

            if (okClicked) {
                showBookDetails(Some(selectedBook))
                selectedBook.save()
            }

        } else {
            // Nothing selected.
            val alert = new Alert(Alert.AlertType.Warning){
            initOwner(MainApp.stage)
            title       = "No Selection"
            headerText  = "No Book Selected"
            contentText = "Please select a Book in the table."
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
