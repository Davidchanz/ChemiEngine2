package com.FXChemiEngine.engine;

import com.FXChemiEngine.engine.shape.Circle;
import com.FXChemiEngine.draw.DrawingObject;
import com.FXChemiEngine.engine.shape.Line;
import com.FXChemiEngine.math.UnityMath.Vector2;
import com.FXChemiEngine.util.Color;
import com.FXChemiEngine.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.FXChemiEngine.util.Constants.borderColor;

/**Scene for 2D graphics*/
public class Scene {
    public static int WIDTH = 0;//Scene width
    public static int HEIGHT = 0;//Scene height
    public static ArrayList<ShapeObject> objects = new ArrayList<>();//set objects for painting
    private boolean Vaxis = false;//flag show axis XOY on scene
    private boolean Vcenter = false;//flag show objects centers
    private boolean Vborder = false;//flag show scene border
    public static Camera camera = new Camera();//camera
    public static ShapeObject[][] O_BUFFER;//TODO
    public int MaxX;//Max possible x
    public int MaxY;//Max possible y
    public int MinX;//Min possible x
    public int MinY;//Min possible y
    private final ShapeObject border = new ShapeObject("Border", -1);//Scene border
    private final AtomicBoolean drawing = new AtomicBoolean(false);
    private final AtomicBoolean resizing = new AtomicBoolean(false);
    private final DrawingObject<?> drawingObject;
    private final ShapeObject coords = new ShapeObject("coords", -1);
    private final ShapeObject coordCenter = new ShapeObject("coord center", -1);

    private Scene(int w, int h, double R, double G, double B, double A, DrawingObject<?> drawingObject){
        WIDTH = w;//ini width
        HEIGHT = h;//ini height

        drawingObject.ini(WIDTH, HEIGHT, new Color(R, G, B, A));

        this.drawingObject = drawingObject;

        this.coords.drawingObject = drawingObject;
        this.border.drawingObject = drawingObject;
        this.coordCenter.drawingObject = drawingObject;

        ini();

        drawing.set(false);
        resizing.set(false);
    }
    /**Scene constructor
     * ini width, height, drawingObject, coordCenter, coords, border, flags */
    /*public Scene(int w, int h, javafx.scene.paint.Color backgroundColor, DrawingObject<?> drawingObject){
        this(w, h, backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), backgroundColor.getOpacity(), drawingObject);
    }

    public Scene(int w, int h, java.awt.Color backgroundColor, DrawingObject<?> drawingObject){
        this(w, h, backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), backgroundColor.getAlpha(), drawingObject);
    }*/

    public Scene(int w, int h, Color backgroundColor, DrawingObject<?> drawingObject){
        this(w, h, backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), backgroundColor.getAlpha(), drawingObject);
    }


    /**ini maxX, maxY, minX, minY*/
    private void ini(){

        O_BUFFER = new ShapeObject[WIDTH][HEIGHT];
        for (var i : O_BUFFER) {
            Arrays.fill(i, new ShapeObject()); //TODO
        }

        setSceneMaxMin();
        setCoords();
        setCoordCenter();
        setBorder();
    }

    public synchronized void resize(int w, int h){
        resizing.set(true);
        if (!drawing.get()) {
            if(w > 0 && h > 0) {
                WIDTH = w;//ini width
                HEIGHT = h;//ini height

                drawingObject.resize(WIDTH, HEIGHT);

                ini();
            }
        }
        resizing.set(false);
    }

    public void enableDevelopMode(){
        setCoordVisible(true);
        setCenterVisible(true);
        setBorderVisible(true);
    }

    /**Set scene's maximum and minimum*/
    private void setSceneMaxMin(){
        var tmp = new Vector2(WIDTH, HEIGHT);
        toScreenDimension(tmp);//get w, h in scene dimension
        MaxX = (int)tmp.x-1;//ini MaxX
        MinY = (int)tmp.y+1;//ini MaxY

        tmp = new Vector2(0,0);
        toScreenDimension(tmp);//get 0, 0 in scene dimension
        MinX = (int)tmp.x+1;//ini MinX
        MaxY = (int)tmp.y-1;//ini MinY
    }

    /**Set coords*/
    private void setCoords(){
        this.coords.clear();
        this.coords.add(new Line(new Vector2(MaxX/2f, MinY), new Vector2(MaxX/2f, MaxY), new Vector2(-MaxX/2f,0), Color.BLACK));//-YOY
        this.coords.add(new Line(new Vector2(MinX, MaxY/2f), new Vector2(MaxX, MaxY/2f), new Vector2(0,-MaxY/2f), Color.BLACK));//-XOX
    }

    /**Set coord center*/
    private void setCoordCenter(){
        this.coordCenter.clear();
        this.coordCenter.add(new Circle(5, new Vector2(0, 0), Constants.centerColor));
    }

    /**Set border with size and color*/
    public void setBorder(){
        this.border.clear();
        this.border.add(new Line(new Vector2(MinX,MinY), new Vector2(MaxX, MinY), new Vector2(0,0), borderColor));//ini border top
        this.border.add(new Line(new Vector2(MinX,MaxY), new Vector2(MaxX,MaxY), new Vector2(0,0), borderColor));//ini border bot
        this.border.add(new Line(new Vector2(MaxX,MinY), new Vector2(MaxX,MaxY), new Vector2(0,0), borderColor));//ini border right
        this.border.add(new Line(new Vector2(MinX,MinY), new Vector2(MinX,MaxY), new Vector2(0,0), borderColor));//ini border left
    }

    /**Show scene border*/
    public void setBorderVisible(boolean b){
        this.Vborder = b;//set border show flag true
    }

    /**Load map from file. Version for block's games*/
    @Deprecated
    public void loadMap(String path){
        /*try{
            this.map = Map.loadMap(path);
            camera.position = moveInput.object.position;//todo this is lock camera to player
            useMap = true;
        }catch (IOException e){
            System.out.println(e.getMessage());
            useMap = false;
        }*/
    }

    /**Show XOY coord axis*/
    public void setCoordVisible(boolean b){
        this.Vaxis = b;//set axis show flag true
    }

    /**Show XOY center objects*/
    public void setCenterVisible(boolean b){
        this.Vcenter = b;//set center show flag true
    }

    /**Find object in radius of object size*/
    protected static ShapeObject findObject(Vector2 onPoint){//todo need testing
        for(var object: objects) {
            for(var shape: object.body) {
                int radius = Math.max(shape.width,shape.height);//ini search radius
                Vector2 positionCenter = shape.getVertices(shape.center);//get shape center in after transform
                if (Math.sqrt(Math.pow(onPoint.x - positionCenter.x, 2.0) + Math.pow(onPoint.y - positionCenter.y, 2.0)) <= Math.sqrt(radius * radius)) {
                    return object;//if shape radius into search radius we found according shape and return shape's object
                }
            }
        }
        return null;//if we don't find any according shape return null
    }

    /**Set object for moving*/
    @Deprecated
    public static void setActiveObject(ShapeObject o){
        //moveInput.setMovedObject(o);
    }

    /**Get point in scene dimension*/
    public static void toSceneDimension(Vector2 point){
        point.x = point.x + WIDTH/2f;
        point.y = -(point.y - HEIGHT/2f);
    }

    /**Get point in screen dimension*/
    public static void toScreenDimension(Vector2 point){
        point.x = point.x - WIDTH/2f;
        point.y = HEIGHT/2f - point.y;
    }

    /**Remove shape from scene*/
    public void remove(ShapeObject o){
        objects.remove(o);
    }

    /**Add new shape on scene*/
    public void add(ShapeObject o){
        o.drawingObject = this.drawingObject;
        objects.add(o);
    }

    /**Add all shape in collection on scene*/
    public void addAll(Collection<ShapeObject> o){
        o.forEach(this::add);
    }

    /**Paint on the Image ande draw it*/
    public synchronized void repaint() {
        drawing.set(true);
        if (!resizing.get()) {
            drawingObject.paint(this::draw);
        }
        drawing.set(false);
    }

    /**Draw method
     *  paint scene's objects on ImageGraphics*/
    private synchronized void draw(){
        drawingObject.clear();
        if (this.Vaxis) {//if flag axis true paint XOY axis
            this.coords.forEach(Painting::paint);
        }
        if (this.Vcenter) {
            this.coordCenter.forEach(Painting::paint);//paint O(XOY) - center point
        }
        if (Vborder) {//if flag border true paint scene border
            this.border.forEach(Painting::paint);//paint border's shapes
        }
        Arrays.stream(objects.toArray(new ShapeObject[0])).toList().forEach(shapeObject -> {
            Arrays.stream(shapeObject.body.toArray(new Shape[0])).toList().forEach(shape -> {
                shape.paint();//paint shape
                if (this.Vcenter) //if centerVisible draw shape's center
                    shape.paintCenter();//paint shapes center
            });
        });
    }

    /**Get image from drawingObject*/
    public <R> R getImage() {
        if (resizing.get())
            return null;//TODO
        //drawing = true;
        //drawing = false;
        return (R) drawingObject.get();//TODO
    }
}