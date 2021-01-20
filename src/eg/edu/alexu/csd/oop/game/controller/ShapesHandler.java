package eg.edu.alexu.csd.oop.game.controller;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.Shapes.Bar;

import java.awt.*;
import java.util.Arrays;

public class ShapesHandler {
    private static final int validIntersectionRatio = 50; // i.e 50% of the smallest shape area

    /**
     *
     * @param firstShape
     * @param secondShape
     * @return true if the two rectangles surrounding the two gameObject are intersecting
     */
    public static boolean isInterSecting(GameObject firstShape, GameObject secondShape) {
        int firstUpperLeftX = firstShape.getX();
        int firstUpperLeftY = firstShape.getY();
        int firstBottomRightX = firstUpperLeftX + firstShape.getWidth();
        int firstBottomRightY = firstUpperLeftY - firstShape.getHeight();

        int secondUpperLeftX = secondShape.getX();
        int secondUpperLeftY = secondShape.getY();
        int secondBottomRightX = secondUpperLeftX + secondShape.getWidth();
        int secondBottomRightY = secondUpperLeftY - secondShape.getHeight();


        if (firstUpperLeftX > secondBottomRightX || secondUpperLeftX > firstBottomRightX)
            return false;
        if (firstUpperLeftY < secondBottomRightY || secondUpperLeftY < firstBottomRightY)
            return false;
        return true;
    }

    /**
     * @param firstShape
     * @param secondShape
     * @return the intersecting area between the two shapes and -1 if they do not intersect
     */
//    private static long getIntersectingArea(GameObject firstShape, GameObject secondShape) {
//        if (!isInterSecting(firstShape, secondShape))
//            return -1;
//
//        Integer[] x = {firstShape.getX(), firstShape.getX() + firstShape.getWidth(), secondShape.getX(), secondShape.getX() + secondShape.getWidth()};
//        Integer[] y = {firstShape.getY(), firstShape.getY() + firstShape.getWidth(), secondShape.getY(), secondShape.getY() + secondShape.getWidth()};
//        Arrays.sort(x);
//        Arrays.sort(y);
//
//        return (x[2] - x[1])*(y[2] - y[1]) ;
//    }
    protected static  long getIntersectionWidth(GameObject firstShape, GameObject secondShape){

        Integer[] x = {firstShape.getX(), firstShape.getX() + firstShape.getWidth(), secondShape.getX(), secondShape.getX() + secondShape.getWidth()};
        Arrays.sort(x);
        return x[2] - x[1] ;
    }

    /**
     *
     * @param firstShape the peek of the clown
     * @param secondShape the ball
     *  in case of the collision we want to know whether the falling object is going to be fixed above the last object
     * the clown has
     * @return true if valid collision
     */
    public static boolean validIntersection(GameObject firstShape, GameObject secondShape){
        if (!isInterSecting(firstShape, secondShape)) {
            return false ;
        }
        long smallestWidth = getSmallestWidth(firstShape, secondShape);
        long intersectionWidth = getIntersectionWidth(firstShape, secondShape);

        return (intersectionWidth / (double)smallestWidth )* 100 > validIntersectionRatio ;

    }

    /**
     *
     * @param firstShape the first game object
     * @param secondShape the second game object
     * @return the area of the smallest game object (width * height )
     */
//    private static long getSmallestArea(GameObject firstShape, GameObject secondShape){
//        long area1 = firstShape.getWidth() * firstShape.getHeight();
//        long area2 = secondShape.getWidth() * secondShape.getHeight();
//
//        return Math.min(area1, area2);
//
//    }
    private static long getSmallestWidth(GameObject firstShape , GameObject secondShape){
        long width1 = firstShape.getWidth();
        long width2 = secondShape.getWidth();

        return Math.min(width2 , width1);
    }



}