package com.FXChemiEngine.engine;

import com.FXChemiEngine.engine.shape.Circle;
import com.FXChemiEngine.draw.DrawingObject;
import com.FXChemiEngine.engine.shape.Dot;
import com.FXChemiEngine.math.MatrixTransforms.MatrixTransforms;
import com.FXChemiEngine.math.UnityMath.Vector2;
import com.FXChemiEngine.math.UnityMath.Vector3;
import com.FXChemiEngine.util.Color;
import com.FXChemiEngine.util.Constants;

import java.util.ArrayList;
import java.util.Objects;

import static com.FXChemiEngine.util.Utils.Interpolate;

/**Abstract class for Shapes on scene*/
public abstract class Shape extends EngineObject implements Painting {
    public ArrayList<Vector2> vertices;//set of shape's vertices
    public Color color;//shape's body color
    protected Color strokeColor;//shape's stroke color
    public int width;//shape's width
    public int height;//shape's height
    private ShapeObject parent;//reference on parent ShapeObject

    /**Inner constructor for ini vertices and color members.*/
    protected Shape(Color c){
        this.color = Objects.requireNonNull(c, "Color must be not null!");
        this.rotation = new Vector3(0,0,0);
        this.vertices = new ArrayList<>();
        this.strokeColor = Color.BLACK;
    }

    protected void setDrawingObject(DrawingObject<?> drawingObject){
        parent.drawingObject = drawingObject;
    }

    /**Get vertices in screen dimension in camera projection after transformation.*/
    public Vector2 getVertices(Vector2 vertices) {
        Vector2 tmpPos = getParentRotateCenter();//get ShapeObject rotate center coord
        Vector2 screen_coord = new Vector2((int) (vertices.x), (int) (vertices.y));//get vertices in new variable
        Scene.toSceneDimension(screen_coord);//get vertices coord in scene dimension
        Vector3 newPoint = new Vector3(screen_coord, 0);//get vertices in new variable type of Vector3
        Vector2 sceneCenter = new Vector2(0,0);//get 0,0 point
        Scene.toSceneDimension(sceneCenter);//get 0,0 point in screen dimension

        MatrixTransforms.Offset(-(int)sceneCenter.x, -(int)sceneCenter.y, 0, newPoint);//move point in rotate's center
        MatrixTransforms.RotationX(rotation.x + parent.rotation.x, newPoint);//rotate in X axis
        MatrixTransforms.RotationY(rotation.y + parent.rotation.y, newPoint);//rotate in Y axis
        MatrixTransforms.RotationZ(rotation.z + parent.rotation.z, newPoint);//rotate in Z axis
        MatrixTransforms.Offset((int)sceneCenter.x, (int)sceneCenter.y, 0, newPoint);//move point from rotate's center
        //MatrixTransforms.Offset((int) this.position.x, (int) -(this.position.y), 0, newPoint);
        MatrixTransforms.Offset((int) tmpPos.x, (int) -(tmpPos.y), 0, newPoint);//move point in shape's position

        newPoint = new Vector3(Scene.camera.projection(new Vector2(newPoint.x, newPoint.y)), 0);//get point coord in camera projection

        if(newPoint.x < 0.0f)
            newPoint.x = 0.0f;
        if(newPoint.x > (float)Scene.WIDTH-1)
            newPoint.x = (float)Scene.WIDTH-1;
        if(newPoint.y < 0.0f)
            newPoint.y = 0.0f;
        if(newPoint.y > (float)Scene.HEIGHT-1)
            newPoint.y = (float)Scene.HEIGHT-1;
        /*if (newPoint.x < 0.0f || newPoint.x >= (float)Scene.WIDTH || newPoint.y < 0.0f || newPoint.y >= (float)Scene.HEIGHT) {
            return null;
        }*/
        return new Vector2(newPoint.x, newPoint.y);//return vertices point after transform
    }

    /**Get list of vertices in screen dimension in camera projection after transformation.*/
    public ArrayList<Vector2> getVertices(ArrayList<Vector2> vertices) {
        Vector2 tmpPos = getParentRotateCenter();//get ShapeObject rotate center coord
        ArrayList<Vector2> dots = new ArrayList<>();//ini set for return
        for (var i : vertices){
            Vector2 screen_coord = new Vector2((int) (i.x), (int) (i.y));//get vertices in new variable
            Scene.toSceneDimension(screen_coord);//get vertices coord in scene dimension
            Vector3 newPoint = new Vector3(screen_coord, 0);//get vertices in new variable type of Vector3
            Vector2 sceneCenter = new Vector2(0,0);//get 0,0 point
            Scene.toSceneDimension(sceneCenter);//get 0,0 point in screen dimension

            MatrixTransforms.Offset(-(int)sceneCenter.x, -(int)sceneCenter.y, 0, newPoint);//move point in rotate's center
            MatrixTransforms.RotationX(rotation.x+parent.rotation.x, newPoint);//rotate in X axis
            MatrixTransforms.RotationY(rotation.y+parent.rotation.y, newPoint);//rotate in Y axis
            MatrixTransforms.RotationZ(rotation.z+parent.rotation.z, newPoint);//rotate in Z axis
            MatrixTransforms.Offset((int)sceneCenter.x, (int)sceneCenter.y, 0, newPoint);//move point from rotate's center
            //MatrixTransforms.Offset((int) this.position.x, (int) -(this.position.y), 0, newPoint);
            MatrixTransforms.Offset((int) tmpPos.x, (int) -(tmpPos.y), 0, newPoint);//move point in shape's position

            newPoint = new Vector3(Scene.camera.projection(new Vector2(newPoint.x, newPoint.y)), 0);//get point coord in camera projection

            if(newPoint.x < 0.0f)
                newPoint.x = 0.0f;
            if(newPoint.x > (float)Scene.WIDTH-1)
                newPoint.x = (float)Scene.WIDTH-1;
            if(newPoint.y < 0.0f)
                newPoint.y = 0.0f;
            if(newPoint.y > (float)Scene.HEIGHT-1)
                newPoint.y = (float)Scene.HEIGHT-1;

           /* if(newPoint.x < 0.0f || newPoint.x >= Scene.WIDTH || newPoint.y < 0.0f || newPoint.y >= Scene.HEIGHT) return null;//if point out of sceen return null
            else*/ dots.add(new Vector2(newPoint.x, newPoint.y));//else return vertices point after transform

        }
        return dots;
    }

    /**Get parent's ShapeObject for rotate.*/
    private Vector2 getParentRotateCenter(){
        Vector2 screen_coord = new Vector2((int) (this.position.x), (int) (this.position.y));//get vertices in new variable
        Scene.toSceneDimension(screen_coord);//get vertices coord in scene dimension
        Vector3 newPoint = new Vector3(screen_coord, 0);//get vertices in new variable type of Vector3
        Vector2 parentCenter = new Vector2(parent.center);//get parent center in new variable
        Scene.toSceneDimension(parentCenter);//get parent's center in screen dimension

        MatrixTransforms.Offset(-(int)parentCenter.x, -(int)parentCenter.y, 0, newPoint);//move point in rotate's center
        MatrixTransforms.RotationX(parent.rotation.x, newPoint);//rotate in X axis
        MatrixTransforms.RotationY(parent.rotation.y, newPoint);//rotate in Y axis
        MatrixTransforms.RotationZ(parent.rotation.z, newPoint);//rotate in Z axis
        MatrixTransforms.Offset((int)parentCenter.x, (int)parentCenter.y, 0, newPoint);//move point from rotate's center

        var tmp = new Vector2(newPoint.x, newPoint.y);//get Vector2 from Vector3 newPoint
        Scene.toScreenDimension(tmp);//get parent rotate center in Scene dimension
        newPoint.x = tmp.x;
        newPoint.y = tmp.y;

        //newPoint = new Vector3(Scene.camera.Projection(new Vector2(newPoint.x, newPoint.y)), 0);

        return new Vector2(newPoint.x, newPoint.y);//return parent rotate center
    }

    /**Draw filled triangle using interpolate*///todo exlpore this function
    private void drawFilledTriangle (STriangle triangle) {
        Vector2 v0 = new Vector2(triangle.P0);
        Vector2 v1 = new Vector2(triangle.P1);
        Vector2 v2 = new Vector2(triangle.P2);
        // Сортировка точек так, что y0 <= y1 <= y2
        if (v0.y > v1.y) {
            var tmp = v0;
            v0 = v1;
            v1 = tmp;
        }
        if (v0.y > v2.y) {
            var tmp = v0;
            v0 = v2;
            v2 = tmp;
        }
        if (v1.y > v2.y) {
            var tmp = v1;
            v1 = v2;
            v2 = tmp;
        }

        // Вычисление координат x рёбер треугольника
        var x01 = Interpolate(v0.y, v0.x, v1.y, v1.x);
        var x12 = Interpolate(v1.y, v1.x, v2.y, v2.x);
        var x02 = Interpolate(v0.y, v0.x, v2.y, v2.x);

        //# Конкатенация коротких сторон
        x01.remove(x01.size()-1);

        x01.addAll(x12);
        var x012 = x01;

        // Определяем, какая из сторон левая и правая
        ArrayList<Integer> x_left;
        ArrayList<Integer> x_right;
        var m = x012.size() / 2;
        if (x02.get(m) < x012.get(m)) {
            x_left = x02;
            x_right = x012;
        } else {
            x_left = x012;
            x_right = x02;
        }

        //# Отрисовка горизонтальных отрезков
        for (int y = (int)v0.y; y <= v2.y; ++y){
            for (int x = x_left.get(y - (int)v0.y); x <= x_right.get(y - (int)v0.y); ++x){
                parent.drawingObject.draw(x, y, color);
                setObjectcBufferElement(x, y);
            }
        }
    }

    /**Fill all screen points references on according ShapeObjects*/
    public void setObjectcBufferElement(int x, int y){//TODO
        if(x >= 0 & x < Scene.WIDTH && y >= 0 & y < Scene.HEIGHT)
            Scene.O_BUFFER[x][y] = parent;
    }

    /**Draw line using intorpolation*///todo exlpore this function
    private void drawLineBrezenheim(Vector2 v1, Vector2 v2, Color color) {
        float dx = v2.x - v1.x;
        float dy = v2.y - v1.y;
        if (Math.abs(dx) > Math.abs(dy)) {
            if (v1.x > v2.x) {
                var tmp = v1;
                v1 = v2;
                v2 = tmp;
            }
            var ys = Interpolate(v1.x, v1.y, v2.x, v2.y);
            /*float invslope1 = (v2.y - v1.y) / (v2.x - v1.x);

            float curx1 = v1.y;*/

            for (int scanlineY = (int) v1.x; scanlineY <= v2.x; scanlineY++) {
                parent.drawingObject.draw(scanlineY, ys.get(scanlineY - (int)v1.x), color);
                setObjectcBufferElement(scanlineY, ys.get(scanlineY - (int)v1.x));
                //curx1 += invslope1;
            }
        } else /*if (Math.abs(dx) > Math.abs(dy))*/{
            if (v1.y > v2.y) {
                var tmp = v1;
                v1 = v2;
                v2 = tmp;
            }
            /*float invslope1 = (v2.x - v1.x) / (v2.y - v1.y);

            float curx1 = v1.x;*/
            var xs = Interpolate(v1.y, v1.x, v2.y, v2.x);

            for (int scanlineY = (int) v1.y; scanlineY <= v2.y; scanlineY++) {
                parent.drawingObject.draw(xs.get(scanlineY - (int)v1.y), scanlineY, color);
                setObjectcBufferElement(xs.get(scanlineY - (int)v1.y), scanlineY);
                //curx1 += invslope1;
            }
        } /*else{
            g.drawRect((int)v1.x, (int)v2.y, 1, 1);
        }*/
    }

    /**Draw stroke*/
    protected void drawStroke(ArrayList<Vector2> dots){
        for(int i = 0; i < dots.size()-1; ++i){
            drawLineBrezenheim(dots.get(i), dots.get(i+1), strokeColor);//paint line form i to i+1 point
        }
        drawLineBrezenheim(dots.get(dots.size()-1), dots.get(0), strokeColor);//paint line from last to first point
    }

    /**Fill body*/
    protected void fillShape(ArrayList<STriangle> triangles){
        triangles.forEach(this::drawFilledTriangle);
    }
    protected void fillShape(STriangle triangle){
        drawFilledTriangle(triangle);
    }
    protected void fillDot(int x, int y){
        draw(parent.drawingObject, x, y, color);
    }

    /**Set stroke color*/
    public void setStrokeColor(Color color){
        this.strokeColor = color;
    }

    /**Set body color*/
    public void setColor(Color c){
        this.color = c;

    }

    /**Draw shape's center*/
    public void paintCenter() {
        if(this instanceof Dot)
            return;
        Circle center = new Circle(5, new Vector2(this.position.x + this.center.x, this.position.y + this.center.y), Constants.centerColor);
        center.rotation = new Vector3(this.rotation);
        center.setParent(parent);
        center.paint();

    }

    public void setParent(ShapeObject parent) {
        this.parent = parent;
    }

    protected static class STriangle{
        public Vector2 P0, P1, P2;

        public STriangle(Vector2 p0, Vector2 p1, Vector2 p2) {
            P0 = p0;
            P1 = p1;
            P2 = p2;
        }
    }
}
