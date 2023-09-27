package ch.makery.address.model

import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}
import ch.makery.address.db.Database
import scalikejdbc._
import scala.util.{ Try, Success, Failure }
import scalafx.collections.ObservableBuffer

class Member (val mIDs: String, val mName : String) extends Database {
	var mID = new StringProperty(mIDs)
	var name = new StringProperty(mName)
	var mobile   = new StringProperty("mobile")
	var email     = new StringProperty("email")
	
	def save() : Try[Int] = {
		if (!(isExist)) {
			Try(DB autoCommit { implicit session => 
				sql"""
					insert into member (memberID, name,mobile, email)
					values(${mID.value}, ${name.value}, ${mobile.value}, ${email.value})
				""".update.apply()
			})
		} else {
			Try(DB autoCommit { implicit session => 
				sql"""
				update member 
				set 
				memberID  = ${mID.value},
				name  = ${name.value},
				mobile     = ${mobile.value},
				email = ${email.value}
				where memberID  = ${mID.value}
				""".update.apply()
			})
		}
			
	}
	def delete() : Try[Int] = {
		println(isExist)
		if (isExist) {
			Try(DB autoCommit { implicit session => 
			sql"""delete from member where memberID = ${mID.value}""".update.apply()
			})
		} else 
			throw new Exception("Member not Exists in Database")		
	}
	
	def isExist : Boolean =  {
		DB readOnly { 
		implicit session =>
			sql"""
				select * from member where
				memberID  = ${mID.value}
			""".map(rs => rs.string("memberID")).single.apply()
		} match {
			case Some(x) => true
			case None => false
		}
	}
}

object Member extends Database{
    val memberData = new ObservableBuffer[Member]()
    Database.setupMemberDB()
    memberData ++= Member.getAllMembers

	def apply (
		mIDs: String,
		mName : String, 
		mMobile : String,
		mEmail : String
		) : Member = {

		new Member(mIDs, mName) {
			mobile.value = mMobile
			email.value = mEmail
		}
		
	}
	
	def getAllMembers : List[Member] = {
		DB readOnly { implicit session =>
			sql"select * from member".map(rs => Member(rs.string("memberID"), rs.string("name"),rs.string("mobile"), rs.string("email"))).list.apply()
		}
	}
}
