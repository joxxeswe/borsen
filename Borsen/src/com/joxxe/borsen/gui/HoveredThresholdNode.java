package com.joxxe.borsen.gui;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public 	class HoveredThresholdNode extends StackPane {
	public HoveredThresholdNode(double value) {
		setPrefSize(5, 5);
		setStyle("-fx-background-color: rgba(0, 100, 100, 0);");
		Pane p = new Pane();
		final Label label = createDataThresholdLabel(value);
		p.getChildren().add(label);
		p.getStyleClass().add("chart-panel");
		p.setTranslateY(10);
		setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				getChildren().setAll(p);
				toFront();
			}
		});
		setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				getChildren().clear();
			}
		});
	}

	private Label createDataThresholdLabel(double value) {
		String v = value + "";
		
		final Label label = new Label(v);
		label.getStyleClass().add("chart-label");
		label.setTextFill(Color.RED);
		return label;
	}
}