package ch.makery.address.model

import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}
//import java.time.LocalDate
import ch.makery.address.db.Database
import scalikejdbc._
import scala.util.{ Try, Success, Failure }
import scalafx.collections.ObservableBuffer

class Book (val idS : String, val titleS : String) extends Database {
	def this()     = this(null, null)
	var bID = new StringProperty(idS)
	var author  = new StringProperty("author")
	var title   = new StringProperty(titleS)
	var status     = new StringProperty("status")


	def save() : Try[Int] = {
		if (!(isExist)) {
			println("save - insert")
			Try(DB autoCommit { implicit session => 
				sql"""
					insert into book (bID, author,title, status) 
					values(${bID.value}, ${author.value}, ${title.value}, ${status.value})
				""".update.apply()
			})
		}
		else {
			println("save - update")
			Try(DB autoCommit { implicit session => 
				sql"""
					update book 
					set 
					bID = ${bID.value},
					author = ${author.value},
					title = ${title.value},
					status = ${status.value}
					where bID = ${bID.value}
				""".update.apply()
			})
		}		
	}

	def borrowBook() : Try[Int] = {
		status.value = "Borrowed"
		if (!(isExist)) {
			println("borrow - insert")
			Try(DB autoCommit { implicit session => 
				sql"""
					insert into book (bID, author,title, status) 
					values(${bID.value}, ${author.value}, ${title.value}, ${status.value})
				""".update.apply()
			})
		}
		else {
			println("borrow - update")
			Try(DB autoCommit { implicit session => 
				sql"""
					update book 
					set 
					bID = ${bID.value},
					author = ${author.value},
					title = ${title.value},
					status = ${status.value}
					where bID = ${bID.value}
				""".update.apply()
			})
		}		
	}

	def returnBook() : Try[Int] = {
		status.value = "Available"
		if (!(isExist)) {
			Try(DB autoCommit { implicit session => 
				sql"""
					insert into book (bID, author,title, status) 
					values(${bID.value}, ${author.value}, ${title.value}, ${status.value})
				""".update.apply()
			})
		} else {
			Try(DB autoCommit { implicit session => 
				sql"""
					update book 
					set 
					bID = ${bID.value},
					author = ${author.value},
					title = ${title.value},
					status = ${status.value}
					where bID = ${bID.value}
				""".update.apply()
			})
		}		
	}

	def delete() : Try[Int] = {
		if (isExist) {
			Try(DB autoCommit { implicit session => 
			sql"""
				delete from book where  
					bID = ${bID.value} and title = ${title.value}
				""".update.apply()
			})
		} else 
			throw new Exception("Book not Exists in Database")		
	}
	
	def isExist : Boolean =  {
		DB readOnly { 
		implicit session =>
			sql"""
				select * from book where 
				bID = ${bID.value}
			""".map(rs => rs.string("bID")).single.apply()
		} match {
			case Some(x) => true
			case None => false
		}
	}
}
object Book extends Database{
    val bookData = new ObservableBuffer[Book]()
	Database.setupBookDB()
    bookData ++= Book.getAllBooks

	def getBookByTitle(t:String) : List[Book]={
		val searchTitle = s"%$t%"
		DB readOnly { implicit session =>
			sql"select * from book where title like $searchTitle".map(
				rs => Book(rs.string("bID"),
				rs.string("author"),
				rs.string("title"), 
				rs.string("status"))
			).list.apply()
		}
	}

	def apply (
		idS : String, 
		authorS : String,
		titleS : String,
		statusS : String
		) : Book = {

		new Book(idS, titleS) {
			author.value = authorS
			status.value = statusS
		}
		
	}
	
	def getAllBooks : List[Book] = {
		
		DB readOnly { implicit session =>
			sql"select * from book".map(rs => Book(rs.string("bID"),
				rs.string("author"),rs.string("title"), 
				rs.string("status"))).list.apply()
		}
	}

	
}
