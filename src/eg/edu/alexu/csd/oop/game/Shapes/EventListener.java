package eg.edu.alexu.csd.oop.game.Shapes;

import eg.edu.alexu.csd.oop.game.GameObject;

import java.util.Stack;

public class EventListener {
    Stack<Shape> left, right;

    public EventListener(Stack<Shape> left, Stack<Shape> right) {
        this.left = left;
        this.right = right;
    }

    public EventListener() {
    }

    public void notify(int diff) {
        for (GameObject object : left) {
            object.setX(object.getX() - diff);
        }
        for (GameObject object : right) {
            object.setX(object.getX() - diff);
        }
    }
}
