package org.FXChemiEngine.engine;

import javafx.scene.image.Image;
import org.FXChemiEngine.draw.DrawingObject;
import org.FXChemiEngine.engine.shape.Dot;
import org.FXChemiEngine.engine.shape.Line;
import org.FXChemiEngine.engine.shape.Rectangle;
import org.FXChemiEngine.math.UnityMath.Vector2;
import javafx.scene.paint.Color;
import org.FXChemiEngine.util.Constants;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

/**Scene for 2D graphics*/
public class Scene {
    public static int WIDTH = 0;//Scene width
    public static int HEIGHT = 0;//Scene height
    public static ArrayList<ShapeObject> objects = new ArrayList<>();//set objects for painting
    private boolean Vaxis = false;//flag show axis XOY on scene
    private boolean Vcenter = false;//flag show objects centers
    public static Camera camera = new Camera();//camera
    public static ShapeObject[][] O_BUFFER;//TODO
    public int MaxX;//Max possible x
    public int MaxY;//Max possible y
    public int MinX;//Min possible x
    public int MinY;//Min possible y
    private final ShapeObject border = new ShapeObject("Border", -1);//Scene border
    private boolean Vborder = false;//flag show scene border
    /*private static int[] BACKGROUND_COLOR_ARRAY;
    private static final int BUFFER_SIZE = 3;
    private final BlockingQueue<WritableImageBuffer> emptyBuffers = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private final BlockingQueue<WritableImageBuffer> fullBuffers = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private WritableImageBuffer currentBuffer;*/
    private final AtomicBoolean drawing = new AtomicBoolean(false);
    private final AtomicBoolean resizing = new AtomicBoolean(false);
    private final DrawingObject<?> drawingObject;
    private final ShapeObject coords = new ShapeObject("coords", -1);
    private final ShapeObject coordCenter = new ShapeObject("coord center", -1);
    private int borderSize;
    private Color borderColor;

    /**Scene constructor
     * ini width, height, maxX, maxY, minX, minY, */
    public Scene(int w, int h, Color backgroundColor, DrawingObject<?> drawingObject){
        this.drawingObject = drawingObject;
        this.borderColor = Color.BLACK;
        this.borderSize = 0;

        this.coords.drawingObject = drawingObject;
        this.border.drawingObject = drawingObject;
        this.coordCenter.drawingObject = drawingObject;

        add(coords);
        add(border);
        add(coordCenter);

        WIDTH = w;//ini width
        HEIGHT = h;//ini height

//        BACKGROUND_COLOR_ARRAY = new int[WIDTH * HEIGHT];
//
//        Arrays.fill(BACKGROUND_COLOR_ARRAY, Utils.toARGB(Color.TRANSPARENT));
//
//        for (int i = 0; i < BUFFER_SIZE; i++) {
//            emptyBuffers.add(new WritableImageBuffer(WIDTH, HEIGHT));
//        }
        drawingObject.ini(WIDTH, HEIGHT, backgroundColor);

        var tmp = new Vector2(w, h);
        toScreenDimension(tmp);//get w, h in scene dimension
        MaxX = (int)tmp.x;//ini MaxX
        MinY = (int)tmp.y;//ini MaxY

        tmp = new Vector2(0,0);
        toScreenDimension(tmp);//get 0, 0 in scene dimension
        MinX = (int)tmp.x;//ini MinX
        MaxY = (int)tmp.y;//ini MinY
        //TODO
            O_BUFFER = new ShapeObject[WIDTH][HEIGHT];
            for(var i: O_BUFFER){
                Arrays.fill(i, new ShapeObject());
            }
        //TODO
        /*this.addComponentListener(new ComponentAdapter() {//add action on scene size change
            public void componentResized(ComponentEvent componentEvent) {
                WIDTH = getWidth();//ini new Scene width
                HEIGHT = getHeight();//ini new scene height
                //TODO
                    O_BUFFER = new ShapeObject[WIDTH][HEIGHT];
                    for(var i: O_BUFFER){
                        Arrays.fill(i, new ShapeObject());
                    }
                //TODO
            }
        });*/

        this.coords.clear();
        this.coords.add(new Line(new Vector2(1, HEIGHT / 2f), new Vector2(WIDTH - 1, HEIGHT / 2f), new Vector2(0,0), Color.BLACK));//-XOX
        this.coords.add(new Line(new Vector2(WIDTH / 2f, 1), new Vector2(WIDTH / 2f, HEIGHT - 1), new Vector2(0,0), Color.BLACK));//-YOY

        this.coordCenter.clear();
        Vector2 zero = new Vector2(0, 0);
        toSceneDimension(zero);//get center 0,0 in scene dimension
        this.coordCenter.add(new Dot(zero, Constants.centerColor));

        this.border.clear();
        setBorder(this.borderSize, this.borderColor);

        drawing.set(false);
        resizing.set(false);
    }

    public synchronized void resize(int w, int h){
        resizing.set(true);
        if (!drawing.get()) {
            //resizing.set(true);
            //System.out.println("resizing: " + resizing);
            WIDTH = w;//ini width
            HEIGHT = h;//ini height
           /* BACKGROUND_COLOR_ARRAY = new int[WIDTH * HEIGHT];

            Arrays.fill(BACKGROUND_COLOR_ARRAY, Utils.toARGB(backGroundColor));

            emptyBuffers.clear();
            fullBuffers.clear();

            for (int i = 0; i < BUFFER_SIZE; i++) {
                emptyBuffers.add(new WritableImageBuffer(WIDTH, HEIGHT));
            }*/
            drawingObject.resize(WIDTH, HEIGHT);

            O_BUFFER = new ShapeObject[WIDTH][HEIGHT];
            for (var i : O_BUFFER) {
                Arrays.fill(i, new ShapeObject());
            }
            this.coords.clear();
            this.coords.add(new Line(new Vector2(1, HEIGHT / 2f), new Vector2(WIDTH - 1, HEIGHT / 2f), new Vector2(0,0), Color.BLACK));//-XOX
            this.coords.add(new Line(new Vector2(WIDTH / 2f, 1), new Vector2(WIDTH / 2f, HEIGHT - 1), new Vector2(0,0), Color.BLACK));//-YOY

            this.coordCenter.clear();
            Vector2 zero = new Vector2(0, 0);
            toSceneDimension(zero);//get center 0,0 in scene dimension
            this.coordCenter.add(new Dot(new Vector2(0, 0), Constants.centerColor));

            this.border.clear();
            setBorder(this.borderSize, this.borderColor);
            //resizing.set(false);
           // System.out.println("resizing: " + resizing);
        }
        resizing.set(false);
    }

    /**Set border with size and color*/
    public void setBorder(int size, Color c){
        this.borderSize = size;
        this.borderColor = c;
        this.border.add(new Rectangle(size, MaxX-1, new Vector2(0, MaxY-size), c));//ini border top
        this.border.add(new Rectangle(size, MaxX-1, new Vector2(0, MinY+size+1), c));//ini border bot
        this.border.add(new Rectangle(MaxY-1, size, new Vector2(MaxX-size-1, 0), c));//ini border right
        this.border.add(new Rectangle(MaxY-1, size, new Vector2(MinX+size, 0), c));//ini border left
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
                //drawing.set(true);
               // System.err.println("drawing: " + drawing);

                /*try {
                    var buffer = emptyBuffers.take();
                    if(buffer.getPixels().length != BACKGROUND_COLOR_ARRAY.length) {
                        //System.out.println("Error");
                        return;//TODO
                    }
                    draw(buffer);//invoke draw method with image graphics

                    fullBuffers.add(buffer);


                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                drawingObject.paint(this::draw);

                /*try {
                    var buffer = fullBuffers.take();

                    if (currentBuffer != null) {
                        emptyBuffers.add(currentBuffer);
                    }

                    buffer.updateBuffer();

                    currentBuffer = buffer;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                //drawing.set(false);
               // System.err.println("drawing: " + drawing);
            }
        drawing.set(false);
    }

    /**Draw method
     *  paint scene's objects on ImageGraphics*/
    private synchronized void draw(/*WritableImageBuffer buffer*/){

            //TODO
            /*buffer.setPixels(BACKGROUND_COLOR_ARRAY);*/
        drawingObject.clear();
            if (this.Vaxis) {//if flag axis true paint XOY axis
                //g.setColor(Color.BLACK);//set color BLACK
                this.coords.forEach(Painting::paint);
            }
            if (this.Vcenter) {
                this.coordCenter.forEach(Painting::paint);//paint O(XOY) - center point
            }
            if (Vborder) {//if flag border true paint scene border
                this.border.forEach(Painting::paint);//paint border's shapes
                /*for (var shape : this.border.body) {
                    shape.paint();//paint border's shapes
                }*/
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
        return (R) drawingObject.get();
    }
}