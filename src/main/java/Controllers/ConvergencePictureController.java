package Controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import lib.NewtonsMethod;
import org.apache.commons.math3.complex.Complex;

import java.util.List;

/**
 * KSE was created by kvld on 08.12.16.
 */
public class ConvergencePictureController {

    private static double IMAGE_SCALE = 0.04;
    private static double MAGIC = 4.8;

    @FXML
    public Button convButton;

    @FXML
    private Canvas convImg;

    @FXML
    private Canvas wayImg;

    private double mapX(double x) {
        return (x / IMAGE_SCALE) + (convImg.getHeight() / 2);
    }

    private double mapY(double y) {
        return (y / IMAGE_SCALE) + (convImg.getWidth() / 2);
    }

    @FXML
    private void drawConvergencePicture() {
        wayImg.getGraphicsContext2D().setFill(Color.TRANSPARENT);
        wayImg.getGraphicsContext2D().fillRect(0, 0, wayImg.getHeight(), wayImg.getWidth());

        final Color[] colors = new Color[]{
                Color.RED.brighter().brighter(),
                Color.CORNFLOWERBLUE.brighter().brighter(),
                Color.GREEN.brighter().brighter()
        };

        for (int i = 0; i < convImg.getHeight(); i++) {
            for (int j = 0; j < convImg.getWidth(); j++) {
                double x = (i - convImg.getHeight() / 2) * IMAGE_SCALE;
                double y = (j - convImg.getWidth() / 2) * IMAGE_SCALE;

                Pair<Integer, Integer> res = NewtonsMethod.countConvergence(new Complex(x, y));

                Color col = colors[res.getKey()].deriveColor(0.0, 1.0, MAGIC / res.getValue(), 1.0);

                convImg.getGraphicsContext2D().getPixelWriter().setColor(i, j, col);
            }
        }
    }

    @FXML
    private void drawPath(MouseEvent event) {
        wayImg.getGraphicsContext2D().clearRect(0, 0, wayImg.getHeight(), wayImg.getWidth());
        double x = (event.getX() - wayImg.getHeight() / 2) * IMAGE_SCALE;
        double y = (event.getY() - wayImg.getWidth() / 2) * IMAGE_SCALE;

        List<Complex> way = NewtonsMethod.getWay(new Complex(x, y));

        wayImg.getGraphicsContext2D().beginPath();
        wayImg.getGraphicsContext2D().moveTo(mapX(way.get(0).getReal()), mapY(way.get(0).getImaginary()));
        for (int i = 1; i < way.size() - 1; i++) {
            wayImg.getGraphicsContext2D().lineTo(mapX(way.get(i).getReal()), mapY(way.get(i).getImaginary()));
        }
        wayImg.getGraphicsContext2D().stroke();
    }
}
