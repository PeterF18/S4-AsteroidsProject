package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroid.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public class AsteroidSplitterImpl implements IAsteroidSplitter {
    @Override
    public void createSplitAsteroid(Entity e, World w) {
        if (e.getRadius() >= 10) {

            Asteroid fragment1 = new Asteroid();
            Asteroid fragment2 = new Asteroid();

            //Polygons
            double[] newPolygon = new double[e.getPolygonCoordinates().length];

            for (int i = 0; i < newPolygon.length; i++) {
                newPolygon[i] = e.getPolygonCoordinates()[i] * 0.5;
            }
            fragment1.setPolygonCoordinates(newPolygon);
            fragment2.setPolygonCoordinates(newPolygon);

            //Spawning coordinates with an offset so the asteroids don't collide on spawn
            fragment1.setX(e.getX() + e.getRadius());
            fragment1.setY(e.getY());

            fragment2.setX(e.getX() - e.getRadius());
            fragment2.setY(e.getY());

            //Radius
            double newRadius = e.getRadius() / 2;
            fragment1.setRadius(newRadius);
            fragment2.setRadius(newRadius);

            //Momentum with a slight angle, to make the fragments move away from each other
            int angleOffset = 10;

            fragment1.setRotation(e.getRotation() - angleOffset);
            fragment2.setRotation(e.getRotation() + angleOffset);

            //Deleting original Asteroid and adding the new split asteroids
            w.removeEntity(e);

            //We only spawn 1 fragment for this test file, so we are able to see the difference
//            w.addEntity(fragment1);
            w.addEntity(fragment2);


        } else {
            w.removeEntity(e);
        }
    }
}
