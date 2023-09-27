package ch.makery.address.db

import scalikejdbc._

trait Database {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
  val dbURL = "jdbc:derby:myDB;create=true;";
  Class.forName(derbyDriverClassname)
  ConnectionPool.singleton(dbURL, "me", "mine")
  implicit val session = AutoSession
}

object Database extends Database{

  def setupBookDB() = {
  	if (!hasBookDBInitialize)
      DB autoCommit { implicit session => 
        sql"""
        create table book (
          bID varchar(10) primary key,
          author varchar(64), 
          title varchar(200), 
          status varchar(64)			  
        )
        """.execute.apply()
      }
  }

  def setupMemberDB() = {
  	if (!hasMemberDBInitialize)
      DB autoCommit { implicit session => 
        sql"""
        create table member (
          memberID varchar(10) primary key,
          name varchar(64),
          mobile varchar(20), 
          email varchar(100)			  
        )
        """.execute.apply()
      }			
  }
  
  def hasBookDBInitialize : Boolean = {
    DB getTable "Book" match {
      case Some(x) => true
      case None => false
    } 
  }

  def hasMemberDBInitialize : Boolean = {
    DB getTable "Member" match {
      case Some(x) => true
      case None => false
    } 
  }
}
