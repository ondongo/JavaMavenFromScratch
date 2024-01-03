package com.ism.repositories.core;

public class MySQLImpl extends DatabaseImpl {

  public MySQLImpl() {
    super(
      "com.mysql.cj.jdbc.Driver",
      "jdbc:mysql://localhost:8889/projet_java_s5_bd"
    );
  }
}
