/*
 * FxCanvas
 */

package fxcanvas;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class FxCanvas extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private void clearAll(Canvas c) {
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(0, 0, c.getWidth(), c.getHeight());
    }
    
    private void clearRect(Canvas c, int x, int y, int l) {
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(x-l, y-l, l*2, l*2);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fx Canvas Application");
        Group root = new Group();
        final Canvas canvas = new Canvas(640, 480);
        canvas.setTranslateX(0);
        canvas.setTranslateY(0);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // cancella una piccola zona trasvinando il mouse cliccato
        canvas.addEventHandler( MouseEvent.MOUSE_DRAGGED,
           new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent e) { clearRect(canvas, (int)e.getX(), (int)e.getY(), 6); }
            });

        // cancella tutto con un doppio click
        canvas.addEventHandler( MouseEvent.MOUSE_CLICKED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) { 
                    if (e.getButton()==MouseButton.MIDDLE) dropShadow(canvas, Color.RED, Color.BLUE, Color.GREEN, Color.BEIGE);
                    else if (e.getButton() == MouseButton.SECONDARY) clearAll(canvas);
                    else if (e.getClickCount() > 1) draw(canvas);
                         else clearRect(canvas, (int)e.getX(), (int)e.getY(), 6);
                }
            });

        draw(canvas);
        
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void draw(Canvas c) {
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.beginPath();
        gc.strokeText("Fx Canvas - Mouse Controlled", 10, 20);
        
        drawGeo(gc);
        gc.beginPath();
        drawBezier(gc);
        /*
        Rectangle rect = new Rectangle(400, 400);
        drawBackground(rect);
        root.getChildren().add(rect);
        */
        
        gc.beginPath();
        drawLinearGradient(gc, Color.GREEN, Color.ORANGE);
        
        // gc.closePath();
    }

    private void drawGeo(GraphicsContext gc) {
        gc.beginPath();
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(3);
        gc.strokeLine(640, 0, 0, 480);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40}, new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90}, new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140}, new double[]{210, 210, 240, 240}, 4);
    }
    
    private void drawBezier(GraphicsContext gc) {
        gc.beginPath();
        double w = gc.getCanvas().getWidth();
        double h = gc.getCanvas().getHeight();
        gc.moveTo(w, h);
        gc.bezierCurveTo(3*w/4, h/4, w/4, h, 0, 3*h/4);
        fillRadialGradient(gc, Color.FUCHSIA, Color.CORAL);
    }
    
    private void fillRadialGradient(GraphicsContext gc, Color firstColor, Color lastColor) {
        gc.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.1, true,
               CycleMethod.REFLECT,
               new Stop(0.0, firstColor),
               new Stop(1.0, lastColor)));
        gc.fill();
    }
    
    private void drawLinearGradient(GraphicsContext gc, Color firstColor, Color secondColor) {
        LinearGradient lg = new LinearGradient(0, 0, 1, 1, true,
                        CycleMethod.REFLECT,
                        new Stop(0.0, firstColor),
                        new Stop(1.0, secondColor));
        gc.setStroke(lg);
        gc.setLineWidth(20);
        gc.stroke();
    }

    private void dropShadow(Canvas c, Color firstColor, Color secondColor,
                                Color thirdColor, Color fourthColor) {
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.applyEffect(new DropShadow(10, 10, 0, firstColor));
        gc.applyEffect(new DropShadow(10, 0, 10, secondColor));
        gc.applyEffect(new DropShadow(10, -10, 0, thirdColor));
        gc.applyEffect(new DropShadow(10, 0, -10, fourthColor));
    }
    
    private void drawBackground(Rectangle rect) {
        rect.setFill(new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.REFLECT,
                new Stop(0, Color.RED),
                new Stop(1, Color.YELLOW)));
    }
    
}
