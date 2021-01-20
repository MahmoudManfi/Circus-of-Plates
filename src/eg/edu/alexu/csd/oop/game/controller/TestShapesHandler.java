package eg.edu.alexu.csd.oop.game.controller;

import eg.edu.alexu.csd.oop.game.GameObject;
import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;

 public class TestShapesHandler {

    private class DummyShape implements GameObject{
        int x;
        int y;
        int width ;
        int height ;

        public DummyShape(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }


        @Override
        public int getX() {
            return x;
        }

        @Override
        public void setX(int x) {
            this.x = x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public void setY(int y) {
            this.y = y;
        }

        @Override
        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        @Override
        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public boolean isVisible() {
            return false;
        }

        @Override
        public BufferedImage[] getSpriteImages() {
            return new BufferedImage[0];
        }
    }

    @Test
    public void testTangentIntersection(){
        DummyShape dummyShape1 = new DummyShape(0, 0, 10 ,2);
        DummyShape dummyShape2 = new DummyShape(10, 0, 10 ,2);
        Assert.assertTrue("Failed to detect intersection" , ShapesHandler.isInterSecting(dummyShape1,dummyShape2));

    }
    @Test
    public void testWhenTwoShapesAreInside(){
        DummyShape dummyShape1 = new DummyShape(0, 0, 10 ,5);
        DummyShape dummyShape2 = new DummyShape(1, -2, 3 ,2);
        Assert.assertTrue("Failed to detect intersection when one shape in inside the other" , ShapesHandler.isInterSecting(dummyShape1,dummyShape2));
    }
    @Test
    public void testIntersection(){
        DummyShape dummyShape1 = new DummyShape(0, 0, 10 ,5);
        DummyShape dummyShape2 = new DummyShape(8, -2, 3 ,2);
        Assert.assertTrue("Failed to detect intersection" , ShapesHandler.isInterSecting(dummyShape1,dummyShape2));

    }
    @Test
    public void testWrongIntersection(){
        DummyShape dummyShape1 = new DummyShape(0, 6, 10 ,5);
        DummyShape dummyShape2 = new DummyShape(8, -2, 3 ,2);
        Assert.assertFalse("returned true although the shapes do not intersect " , ShapesHandler.isInterSecting(dummyShape1,dummyShape2));

    }

    @Test
    public void testIntersectionLength(){
        DummyShape dummyShape1 = new DummyShape(0, 0, 10 ,2);
        DummyShape dummyShape2 = new DummyShape(10, 0, 10 ,2);
        Assert.assertEquals("Wrong intersection length" ,0,ShapesHandler.getIntersectionWidth(dummyShape1,dummyShape2) );

    }
    @Test
    public void testIntersectionLength2(){
        DummyShape dummyShape1 = new DummyShape(0, 0, 10 ,5);
        DummyShape dummyShape2 = new DummyShape(1, -2, 3 ,2);
        Assert.assertEquals("Wrong intersection length" ,3,ShapesHandler.getIntersectionWidth(dummyShape1,dummyShape2) );


    }
    @Test
    public void testIntersectionLength3(){
        DummyShape dummyShape1 = new DummyShape(1, 0, 7 ,3);
        DummyShape dummyShape2 = new DummyShape(5, 0, 5 ,1);
        Assert.assertEquals("Wrong intersection length" ,3,ShapesHandler.getIntersectionWidth(dummyShape1,dummyShape2) );


    }















}
