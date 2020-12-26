package com.lbq.io;

import java.io.Serializable;

/**
 * Person需要满足如下的要求，方可序列化
 * 1.需要实现接口：Serializable
 * 2.当前类提供一个全局常量：serialVersionUID
 * 3.除了当前Person类需要实现serializable接口之外，还必须保证其内部所有属性也必须是可序列化的。（默认情况下，基本数据类型可序列化）
 * 
 * 补充：ObjectOutputStream和ObjectInputStream不能序列化static和transient修饰的成员变量
 * 
 * @author 14378
 *
 */
public class Person implements Serializable {
	
	private static final long serialVersionUID = 263642679277644163L;
	
	private String name;
	private int age;
	private int id;
	private Account acct;
	public Person() {
	}
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	public Person(String name, int age, int id, Account acct) {
		this.name = name;
		this.age = age;
		this.id = id;
		this.acct = acct;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Account getAcct() {
		return acct;
	}
	public void setAcct(Account acct) {
		this.acct = acct;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", id=" + id + ", acct=" + acct + "]";
	}
}

class Account implements Serializable {

	private static final long serialVersionUID = -8466205900754701392L;
	
	private double balance;

	public Account() {
	}

	public Account(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [balance=" + balance + "]";
	}
	
}