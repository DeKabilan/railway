package com.railway.model;

public class Ticket {

	
	String type;
	Train train;
	String seatNo;
	String mail="";
	String name;
	int age;
	String bookDate;
	String travelDate;
	
	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Ticket [type=" + type + ", train=" + train + ", seatNo=" + seatNo + ", mail=" + mail + ", name=" + name
				+ ", age=" + age + ", bookDate=" + bookDate + ", travelDate=" + travelDate + "]";
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	



	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getBookDate() {
		return bookDate;
	}
	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}
	public String getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}


}
