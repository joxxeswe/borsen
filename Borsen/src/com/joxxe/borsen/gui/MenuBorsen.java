package com.joxxe.borsen.gui;


import com.joxxe.borsen.Main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class MenuBorsen {

	private MenuBar menuBar;

	public MenuBorsen(Main m){
		menuBar = new MenuBar();
		String os = System.getProperty ("os.name");
		if (os != null && os.startsWith ("Mac")){
			 menuBar.useSystemMenuBarProperty ().set (true);
		}
		Menu menuFile = new Menu("File");
		MenuItem open = new MenuItem("Hämta sparad data");
		MenuItem save = new MenuItem("Spara inläst data");
		MenuItem clear = new MenuItem("Ta bort data (även sparad)");
		MenuItem exit = new MenuItem("Avsluta");
		exit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		        m.exit();
		    }
		});
		clear.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		        m.clear();
		    }
		});
		save.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		    	m.getMarketCrawler().save();
		    }
		});
		open.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		    	m.getMarketCrawler().open();
		    }
		});
		menuFile.getItems().addAll(open,save,new SeparatorMenuItem(),clear,new SeparatorMenuItem(),exit);
		menuBar.getMenus().addAll(menuFile);
	}
	
	public MenuBar getMenuBar(){
		return menuBar;
	}
}
