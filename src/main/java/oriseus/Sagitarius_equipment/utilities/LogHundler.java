package oriseus.Sagitarius_equipment.utilities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import oriseus.Sagitarius_equipment.model.LogEntity;

public class LogHundler {
	
	public static void writeLogingMessage(LogEntity logEntity) {		
		
		try (BufferedWriter writer = Files.newBufferedWriter(FileHundler.getLogFile(), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
		    writer.write(logEntity.getFullLogMessage());
		    writer.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
