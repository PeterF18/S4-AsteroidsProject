package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class AsteroidPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)){
            world.removeEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Asteroid asteroid = new Asteroid();

        int size = 20;
        asteroid.setPolygonCoordinates(-size, size, size, size, size, -size, -size, -size);

        asteroid.setX(gameData.getDisplayWidth());
        asteroid.setY(gameData.getDisplayHeight()/2);

        asteroid.setRadius(size);

        double randomRotation = Math.random() * 360;
        asteroid.setRotation(randomRotation);

        return asteroid;
    }
}
