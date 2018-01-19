package Client;

import java.io.Serializable;

//Une classe juste pour stocker un message et un login parce que y'a pas de tuples en java
//x = login y = message
public class Pair<X, Y> implements Serializable {
    public X x;
    public Y y;
    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}