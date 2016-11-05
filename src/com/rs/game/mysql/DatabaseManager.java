package com.rs.game.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.rs.Settings;
import com.rs.utils.Logger;

public class DatabaseManager {

	private String host;
	private String database;
	private String username;
	private String password;

	private Connection connection;
	private Statement statement;

	private boolean connected;

	public DatabaseManager() {
		this.host = Settings.DB_HOST;
		this.database = Settings.DB_NAME;
		this.username = Settings.DB_USER;
		this.password = Settings.DB_PASS;
		this.connected = false;
	}

	public void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?jdbcCompliantTruncation=false", username, password);
			statement = connection.createStatement();
			Logger.log("CollabScape", "Successfully connected with " + host + "/" + database);
			connected = true;
		} catch (Exception e) {
			Logger.log("CollabScape", "Unable to connect with " + host + "/" + database + ".");
			connected = false;
		}
	}

	public ResultSet executeQuery(String query) {
		try {

			if (!connected())
				return null;

			statement = connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			return results;
		} catch (Exception e) {
			Logger.handle(e);
		}
		return null;
	}

	public int executeUpdate(String query) {
		try {

			if (!connected())
				return 0;

			statement = connection.createStatement();
			return statement.executeUpdate(query);
		} catch (Exception e) {
			Logger.handle(e);
		}

		return 0;
	}

	public boolean connected() {
		return connected;
	}

	public Statement statement() {
		return statement;
	}

}
