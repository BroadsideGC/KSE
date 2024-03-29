package Controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import lib.FixedPointMethod;

import java.util.List;

/**
 * KSE was created by kvld on 08.12.16.
 */
public class SecondDiagramController {

    @FXML
    private TextField x0Field;

    @FXML
    private TextField rField;

    @FXML
    private Canvas canvas;

    private class CoordinatesSystem {

        GraphicsContext graphicsContext;
        double height;
        double width;
        double originX;
        double originY;
        double scaleX;
        double scaleY;

        CoordinatesSystem(Canvas canvas, double originX, double originY, double scaleX, double scaleY) {
            graphicsContext = canvas.getGraphicsContext2D();
            height = canvas.getHeight();
            width = canvas.getWidth();
            this.originX = originX;
            this.originY = originY;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            init();
        }

        private void init() {
            graphicsContext.clearRect(0, 0, width, height);
        }

        void drawLine(double x1, double y1, double x2, double y2) {
            graphicsContext.beginPath();
            graphicsContext.strokeLine(mapX(x1), mapY(y1), mapX(x2), mapY(y2));
            graphicsContext.closePath();
        }

        double mapX(double x) {
            return originX + x * scaleX;
        }

        double mapY(double y) {
            return originY - y * scaleY;
        }

        void drawParabola(double from, double to, double a, double b, double c) {
            double x1 = mapX(from);
            double y1 = mapY(a * from * from + b * from + c);
            double x3 = mapX(to);
            double y3 = mapY(a * to * to + b * to + c);
            double x2 = mapX((from + to) / 2);
            double y2 = mapY((a * from * from + b * from + c) + (2 * a * from + b) * (to - from) / 2);
            graphicsContext.beginPath();
            graphicsContext.moveTo(x1, y1);
            graphicsContext.quadraticCurveTo(x2, y2, x3, y3);
            graphicsContext.stroke();
            graphicsContext.closePath();
        }

    }


    @FXML
    public void plotSecondDiagram() {
        double x0;
        double r;
        try {
            x0 = Double.parseDouble(x0Field.getText());
            r = Double.parseDouble(rField.getText());
        } catch (NumberFormatException e) {
            return;
        }
        canvas.getGraphicsContext2D().setStroke(Color.BLACK);
        CoordinatesSystem coord = new CoordinatesSystem(canvas, 388, 624, 443, 435);
        coord.drawLine(-3, -3, 3, 3);
        coord.drawParabola(-3, 3, -r, r, 0);
        canvas.getGraphicsContext2D().setStroke(Color.RED);
        List<Double> points = FixedPointMethod.getLinear(x0, r, 100);
        for (int i = 0; i < points.size() - 2; i++) {
            double p1 = points.get(i);
            double p2 = points.get(i + 1);
            double p3 = points.get(i + 2);
            coord.drawLine(p1, p2, p2, p2);
            coord.drawLine(p2, p2, p2, p3);
        }
    }
}
