package oriseus.Sagitarius_equipment.model;

import java.time.LocalDateTime;

import oriseus.Sagitarius_equipment.ports.LogLevel;

public class LogEntity {

	private LogLevel logLevel;
	private String message;
	private LocalDateTime time;
	
	public LogEntity(LogLevel logLevel, String message) {
		this.logLevel = logLevel;
		this.message = message;
		time = LocalDateTime.now();
	}

	public String getFullLogMessage() {
		return logLevel + " : " + message + " : " + time;
	}
	
	public LogLevel getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public LocalDateTime getTime() {
		return time;
	}
}
