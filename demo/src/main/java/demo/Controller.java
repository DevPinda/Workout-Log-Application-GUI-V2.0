package demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
	@FXML private TextField cardioNameField;
	@FXML private TextField cardioDurationField;
	@FXML private TextField weightName;
	@FXML private TextField weightSets;
	@FXML private TextField weightReps;
	@FXML private Button cardioSubmit;
	@FXML private Button weightSubmit;
	@FXML private ResourceBundle resources;
	@FXML private URL location;
	
	final String cardioFileName = System.getProperty("user.dir")+"cardioOutput.json";
	final String weightFileName = System.getProperty("user.dir")+"weightOutput.json";
	

	public void initialize()  {
		try {
			weightSubmit.setDisable(true);
			} catch(Exception e) {
				//e.printStackTrace();
				}
		try {
			cardioSubmit.setDisable(true);
		} catch(Exception e) {
			//e.printStackTrace();
			}
		}

	@FXML
	public void menuLog(ActionEvent e) throws IOException {
		final String addFile = "/AddLog.fxml";
		final Parent root = FXMLLoader.load(getClass().getResource(addFile));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Select Type");
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	public void cardioConfirm(ActionEvent e) throws IOException {
		final String addFile = "/CardioLog.fxml";
		final Parent root = FXMLLoader.load(getClass().getResource(addFile));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Log Cardiovascular Exercise");
		stage.setResizable(false);
		stage.show();
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	public void submitCardio(ActionEvent event) throws IOException, ParseException {
		//set file variable with file path
		File file = new File(cardioFileName);
		//get text field user input from GUI
		String nameField = cardioNameField.getText();
		//get text field user input from GUI
		String durationField = cardioDurationField.getText();
		//declare and initialise Array
		String[] cardioArr = new String[2];
		//define array indexes
		cardioArr[0] = nameField;
		cardioArr[1] = durationField;
		//declare JSON Array
		JSONArray jsonArray = new JSONArray();
		//iterate over indexes of String array and adding strings to JSON array
		for (int i = 0; i < 2; i++) {
			jsonArray.add(cardioArr[i]);
		}
		JSONObject jsonObject = new JSONObject();
		// Creating JSON Object with cardio key and containing the JSON Array
		/**
		 * Checks to see whether the file is empty and appends to File
		 */
		if(file.length()>1) {
			try{
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JSONParser parser = new JSONParser();
				JSONObject jo =  (JSONObject) parser.parse(new FileReader(cardioFileName));
				Set<String> set =  jo.keySet();
				String[] array = set.stream().toArray(String[]::new);
				int key = Integer.valueOf(array[array.length-1]);
				key = key + 1;
				jo.put(key, jsonArray);
				BufferedWriter bw2 = new BufferedWriter(new FileWriter(cardioFileName,false));
				String jsonString2 = gson.toJson(jo);
				bw2.append(jsonString2);
				bw2.close();
			} catch(IOException e) {
				e.printStackTrace();
			} catch(ParseException pe) {
				pe.printStackTrace();
			}
		}

		if(file.length()==0) {
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(cardioFileName, true))){
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				jsonObject.put("1", jsonArray);
				String jsonString = gson.toJson(jsonObject);
				bw.write(jsonString);
				bw.flush();
				bw.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		final String addFile = "/MainMenu.fxml";
		final Parent root = FXMLLoader.load(getClass().getResource(addFile));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Main Menu");
		stage.setResizable(false);
		stage.show();
	}
	
	
	@FXML
	public void submitLock() {
		String nameField = cardioNameField.getText();
		String durationField = cardioDurationField.getText();
		Boolean isDisabled = (nameField.isEmpty() || nameField.trim().isEmpty() || durationField.isEmpty() || durationField.trim().isEmpty());
		cardioSubmit.setDisable(isDisabled);
	}
	
	@FXML
	public void cancelCardio(ActionEvent e) throws IOException {
		final String addFile = "/AddLog.fxml";
		final Parent root = FXMLLoader.load(getClass().getResource(addFile));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Select Type");
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	public void weightConfirm(ActionEvent e) throws IOException {
		final String addFile = "/WeightsLog.fxml";
		final Parent root = FXMLLoader.load(getClass().getResource(addFile));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Log Weightlifting Exercise");
		stage.setResizable(false);
		stage.show();
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	public void submitWeight(ActionEvent event) throws IOException{
		//set file variable with file path
		File file = new File(weightFileName);
		//get text field user input from GUI
		String nameField = weightName.getText();
		//get text field user input from GUI
		String setsField = weightSets.getText();
		//get text field user input from GUI
		String repsField = weightReps.getText();
		//declare and initialise Array
		String[] weightArr = new String[3];
		//define array indexes
		weightArr[0] = nameField;
		weightArr[1] = setsField;
		weightArr[2] = repsField;
		//declare JSON Array
		JSONArray jsonArray = new JSONArray();
		//iterate over indexes of String array and adding strings to JSON array
		for (int i = 0; i < weightArr.length-1; i++) {
			jsonArray.add(weightArr[i]);
		}
		JSONObject jsonObject = new JSONObject();
		// Creating JSON Object with cardio key and containing the JSON Array
		/**
		 * Checks to see whether the file is empty and appends to File
		 */
		if(file.length()>1) {
			try{
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JSONParser parser = new JSONParser();
				JSONObject jo =  (JSONObject) parser.parse(new FileReader(weightFileName));
				Set<String> set =  jo.keySet();
				String[] array = set.stream().toArray(String[]::new);
				int key = Integer.valueOf(array[array.length-1]);
				key = key + 1;
				jo.put(key, jsonArray);
				BufferedWriter bw2 = new BufferedWriter(new FileWriter(weightFileName,false));
				String jsonString2 = gson.toJson(jo);
				bw2.append(jsonString2);
				bw2.close();
			} catch(IOException e) {
				e.printStackTrace();
			} catch(ParseException pe) {
				pe.printStackTrace();
			}
		}

		if(file.length()==0) {
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(weightFileName, true))){
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				jsonObject.put("1", jsonArray);
				String jsonString = gson.toJson(jsonObject);
				bw.write(jsonString);
				bw.flush();
				bw.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		final String addFile = "/MainMenu.fxml";
		final Parent root = FXMLLoader.load(getClass().getResource(addFile));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Main Menu");
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	public void submitWeightLock() {
		String nameField = weightName.getText();
		String setsField = weightSets.getText();
		String repsField = weightReps.getText();
		Boolean isDisabled = (nameField.isEmpty() || nameField.trim().isEmpty() || setsField.isEmpty() || setsField.trim().isEmpty() || repsField.isEmpty() || repsField.trim().isEmpty());
		weightSubmit.setDisable(isDisabled);
	}
	
	@FXML
	public void cancelWeight(ActionEvent e) throws IOException{
		final String addFile = "/AddLog.fxml";
		final Parent root = FXMLLoader.load(getClass().getResource(addFile));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Select Type");
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	public void menuView(ActionEvent event) throws IOException {
		final String viewFile = "/viewLog.fxml";
		final Parent root = FXMLLoader.load(getClass().getResource(viewFile));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("View Log");
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	public void menuDelete(ActionEvent e) throws IOException {
		final String deleteFile = "/DeleteLog.fxml";
		final Parent root = FXMLLoader.load(getClass().getResource(deleteFile));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Delete Log");
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	public void deleteConfirm(ActionEvent event) throws IOException {
        try{
        	File file = new File(cardioFileName);
        	if(file.delete()==true) {
        		System.out.println("File Deleted successfully");
        		} else { 
        			System.out.println("Error while Deleting file");
        			}
        }catch(SecurityException se) {
        	se.printStackTrace();
        }catch(Exception e) {
        	e.printStackTrace();
        }
        		

        final String menuFile = "/MainMenu.fxml";
		final Parent root = FXMLLoader.load(getClass().getResource(menuFile));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Main Menu");
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	public void deleteCancel(ActionEvent e) throws IOException {
		final String menuFile = "/MainMenu.fxml";
		final Parent root = FXMLLoader.load(getClass().getResource(menuFile));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Main Menu");
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	public void menuExit(ActionEvent e) {
	    Node  source = (Node)  e.getSource(); 
	    Stage stage  = (Stage) source.getScene().getWindow();
	    stage.close();
	}
}
