package cw3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;


public class Zichen_Qiu_2252705_sankeydiagrams extends Application {
    //--module-path "C:\Program Files\Java\javafx-sdk-17.0.9\lib" --add-modules javafx.controls,javafx.fxml
    private Double wordSize =16.0;
    private Double rate=0.5;
    private int width = 800;
    private int height = 800;
    private Double bound =0.08 * height;

    @Override
    public void start(Stage primaryStage) {

        String filePath = "C:\\Users\\Qiu\\Desktop\\code\\java\\javaFX\\src\\cw3\\example3.txt"; // The file's position
        Zichen_Qiu_2252705_readFile data = new Zichen_Qiu_2252705_readFile(filePath); // Use another class to readFile

        Pane pane = new Pane(); // The main pane
        ScrollPane scrollPane = new ScrollPane(pane);
        Scene scene = new Scene(scrollPane, width, height); // The scene

        Pane curvePane = new Pane();
        Pane textPane = new Pane();

        double leftWidth = 0.1 * width;
        double leftHeight = 0.4 * height;
        double outScene = 0.05 * width;
        double n = 5.0;
        double textToRectangle = 0.03 * width;

        final double minRate = 0.1;
        final double maxRate = 0.9;

        final double minWordSize = 14.0;
        final double maxWordSize = 20.0;

        double initialLine = 0.5 * height - leftHeight * 0.5;
        double startRectangle = n * outScene;

        Rectangle leftRectangle = new Rectangle(startRectangle, initialLine, leftWidth, leftHeight); // Create a rectangle
        Color color0 = Color.color(Math.random(), Math.random(), Math.random(), Math.random());
        leftRectangle.setStroke(color0);
        leftRectangle.setFill(color0);
        curvePane.getChildren().add(leftRectangle); // Add the rectangle to the curve pane

        Text text1 = new Text(data.getSortedString().get(0) + ": " + data.getSortedInteger().get(0)); // Create a text element
        text1.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, minWordSize));
        double textWidth = text1.getLayoutBounds().getWidth();
        text1.setLayoutX(startRectangle - textWidth - textToRectangle);
        text1.setLayoutY(initialLine + leftHeight * 0.5);
        textPane.getChildren().add(text1); // Add the text to the text pane

        int len = data.getSortedInteger().size();
        double rightHeightLocation = 0.5 * (height - leftHeight - (len - 2) * bound);

        for (int i = 1; i < len; i++) {
            double rate = 1.0 * data.getSortedInteger().get(i) / data.getSortedInteger().get(0);
            double rateLeftRight = rate * leftHeight;

            // Create a rectangle for the right pane
            Rectangle rightRectangle = new Rectangle(width - leftWidth - outScene, rightHeightLocation, leftWidth, rateLeftRight);
            Color color1 = Color.color(Math.random(), Math.random(), Math.random(), Math.random());
            rightRectangle.setStroke(color1);
            rightRectangle.setFill(color1);
            curvePane.getChildren().add(rightRectangle);

            // Create text for the right pane
            Text text2 = new Text(data.getSortedString().get(i) + ": " + data.getSortedInteger().get(i));
            text2.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, wordSize));
            double textWidth2 = text2.getLayoutBounds().getWidth();
            text2.setLayoutX(width - outScene - leftWidth - textToRectangle - textWidth2);
            text2.setLayoutY(rightHeightLocation + rateLeftRight * 0.5);
            textPane.getChildren().add(text2);

            // Calculate control points for the cubic curve
            double startX = startRectangle + leftWidth + 0.5 * rateLeftRight;
            double startY = initialLine + 0.5 * rateLeftRight;
            double endX = width - leftWidth - outScene - 0.5 * rateLeftRight;
            double endY = rightHeightLocation + 0.5 * rateLeftRight;
            double controlY1 = startY;
            double controlY2 = endY;
            double controlX1 = startX + (endX - startX) * rate;
            double controlX2 = endX - (endX - startX) * rate;

            // Create a cubic curve
            double red = color1.getRed();
            double green = color1.getGreen();
            double blue = color1.getBlue();
            double opacity = color1.getOpacity();
            double hueShift = 0.05;
            double saturationShift = 0.05;
            double brightnessShift = 0.05;
            CubicCurve curve1 = new CubicCurve(startX, startY, controlX1, controlY1, controlX2, controlY2, endX, endY);
            Color color2 = Color.rgb(
                    (int) (red * 255 + hueShift * 255) % 255,
                    (int) (green * 255 + saturationShift * 255) % 255,
                    (int) (blue * 255 + brightnessShift * 255) % 255,
                    opacity
            );
            Color color3 = Color.color(Math.random(), Math.random(), Math.random(), 0);
            curve1.setStroke(color2);
            curve1.setFill(color3);
            curve1.setStrokeWidth(rate * leftHeight);
            curvePane.getChildren().add(curve1);

            // Update positions for the next iteration
            initialLine += rate * leftHeight;
            rightHeightLocation += bound + rate * leftHeight;
        }

        // Add the curve and text panes to the main pane
        pane.getChildren().addAll(curvePane, textPane);
        textPane.toFront();


        // Create a button for control
        Button controlBtn = new Button("Control Panel");
        controlBtn.setLayoutX(outScene);
        controlBtn.setLayoutY(width - outScene);
        pane.getChildren().add(controlBtn);

        // Create a scroll bar for adjusting text size
        Label textSizeLabel = new Label("Text Size");
        ScrollBar colorSlider0 = new ScrollBar();
        colorSlider0.setMin(-1);
        colorSlider0.setMax(1);
        colorSlider0.setValue(0);
        colorSlider0.setLayoutX(outScene);
        colorSlider0.setLayoutY(width - outScene * 2);
        pane.getChildren().addAll(colorSlider0, textSizeLabel);
        textSizeLabel.setLayoutX(outScene + colorSlider0.getWidth() + 90);
        textSizeLabel.setLayoutY(width - outScene * 2 - textSizeLabel.getHeight() / 2);

        // Create a scroll bar for adjusting rectangle opacity
        Label opacityLabel = new Label("Rectangle Opacity");
        ScrollBar colorSlider1 = new ScrollBar();
        colorSlider1.setMin(0);
        colorSlider1.setMax(1);
        colorSlider1.setValue(0.5);
        colorSlider1.setLayoutX(outScene);
        colorSlider1.setLayoutY(width - outScene * 3);
        pane.getChildren().addAll(colorSlider1, opacityLabel);
        opacityLabel.setLayoutX(outScene + colorSlider1.getWidth() + 90);
        opacityLabel.setLayoutY(width - outScene * 3 - opacityLabel.getHeight() / 2);

        // Create a scroll bar for adjusting curve opacity
        Label curveOpacityLabel = new Label("Curve Opacity");
        ScrollBar colorSlider2 = new ScrollBar();
        colorSlider2.setMin(-1);
        colorSlider2.setMax(1);
        colorSlider2.setValue(0);
        colorSlider2.setLayoutX(outScene);
        colorSlider2.setLayoutY(width - outScene * 4);
        pane.getChildren().addAll(colorSlider2, curveOpacityLabel);
        curveOpacityLabel.setLayoutX(outScene + colorSlider2.getWidth() + 90);
        curveOpacityLabel.setLayoutY(width - outScene * 4 - curveOpacityLabel.getHeight() / 2);

        // Create a scroll bar for adjusting curve slope
        Label curveSlopeLabel = new Label("Curve Slope");
        ScrollBar colorSlider3 = new ScrollBar();
        colorSlider3.setMin(0);
        colorSlider3.setMax(1);
        colorSlider3.setValue(0.5);
        colorSlider3.setLayoutX(outScene);
        colorSlider3.setLayoutY(width - outScene * 5);
        pane.getChildren().addAll(colorSlider3, curveSlopeLabel);
        curveSlopeLabel.setLayoutX(outScene + colorSlider3.getWidth() + 90);
        curveSlopeLabel.setLayoutY(width - outScene * 5 - curveSlopeLabel.getHeight() / 2);

        // Handle button click event
        controlBtn.setOnAction(event -> {
            if (!pane.getChildren().contains(colorSlider0)) {
                // Show the scroll bars and labels
                pane.getChildren().addAll(
                        colorSlider0, colorSlider1, colorSlider2, colorSlider3,
                        opacityLabel, textSizeLabel, curveOpacityLabel, curveSlopeLabel
                );
            } else {
                // Hide the scroll bars and labels
                pane.getChildren().removeAll(
                        colorSlider0, colorSlider1, colorSlider2, colorSlider3,
                        opacityLabel, textSizeLabel, curveOpacityLabel, curveSlopeLabel
                );
            }
        });

        // Update the word size based on the value of colorSlider0
        colorSlider0.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            wordSize = (maxWordSize - minWordSize) * new_val.doubleValue() + minWordSize;
            textPane.getChildren().stream()
                    .filter(node -> node instanceof Text)
                    .map(node -> (Text) node)
                    .forEach(text -> {
                        double curX = text.getLayoutX();
                        double textWidth1 = text.getBoundsInLocal().getWidth();
                        text.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, wordSize));
                        double textWidth2 = text.getBoundsInLocal().getWidth();
                        text.setLayoutX(curX - textWidth2 + textWidth1);
                    });
        });

        // Update the opacity of rectangles in curvePane based on the value of colorSlider1
        colorSlider1.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            double opacity = (new_val.doubleValue() + 1) / 2;
            curvePane.getChildren().stream()
                    .filter(node -> node instanceof Rectangle)
                    .map(node -> (Rectangle) node)
                    .forEach(rectangle -> rectangle.setOpacity(opacity));
        });

        // Update the opacity of CubicCurves in curvePane based on the value of colorSlider2
        colorSlider2.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            double opacity = (new_val.doubleValue() + 1) / 2;
            curvePane.getChildren().stream()
                    .filter(node -> node instanceof CubicCurve)
                    .map(node -> (CubicCurve) node)
                    .forEach(curve -> curve.setOpacity(opacity));
        });

        // Update the rate of CubicCurves in curvePane based on the value of colorSlider3
        colorSlider3.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            rate = (maxRate - minRate) * new_val.doubleValue() + minRate;
            curvePane.getChildren().stream()
                    .filter(node -> node instanceof CubicCurve)
                    .map(node -> (CubicCurve) node)
                    .forEach(curve -> {
                        double startX = curve.getStartX();
                        double endX = curve.getEndX();
                        double controlX1 = startX + (endX - startX) * rate;
                        double controlX2 = endX - (endX - startX) * rate;
                        curve.setControlX1(controlX1);
                        curve.setControlX2(controlX2);
                    });
        });


        scrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            pane.setScaleX(scrollPane.getViewportBounds().getWidth() / width);
            pane.setScaleY(scrollPane.getViewportBounds().getHeight() / height);
        });

        primaryStage.setTitle(data.getTitle());
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
