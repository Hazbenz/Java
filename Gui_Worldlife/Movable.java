/**
 * Created by Hasna Benzekri on 05/15/16.
 */
//An interface is a reference type in Java, it is similar to class, it is a collection of abstract methods. A class
//implements an interface, thereby inheriting the abstract methods of the interface.
//Along with abstract methods an interface may also contain constants, default methods,static methods,and nested
//types. Method bodies exist only for default methods and static methods.
import java.awt.Point;
public interface Movable {
        int NORTH = 0;
        int SOUTH = 1;
        int EAST = 2;
        int WEST = 3;

        Point getLocation();
        void setLocation(Point location);
        void move( Point location );
}
