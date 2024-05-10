package dk.sdu.mmmi.cbse.bulletsystem;


import dk.sdu.mmmi.cbse.common.Bullet;
import dk.sdu.mmmi.cbse.common.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletProcess implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + changeX * 2);
            bullet.setY(bullet.getY() + changeY * 2);

            //Removing offscreen bullets
            if (bullet.getX() < 0 || bullet.getX() > gameData.getDisplayWidth() ||
                    bullet.getY() < 0 || bullet.getY() > gameData.getDisplayHeight()) {
                world.removeEntity(bullet);
            }
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {

        Entity bullet = new Bullet();
        bullet.setIsHit(true);

        int size = 3;
        bullet.setPolygonCoordinates(size, size, size, -size, -size, -size, -size, size);
        bullet.setRadius(size);

        //Move the bullets away from the shooters radius
        double offset = shooter.getRadius() + bullet.getRadius() + 2;
        double spawnX = shooter.getX() + Math.cos(Math.toRadians(shooter.getRotation())) * offset;
        double spawnY = shooter.getY() + Math.sin(Math.toRadians(shooter.getRotation())) * offset;

        bullet.setX(spawnX);
        bullet.setY(spawnY);

        bullet.setRotation(shooter.getRotation());
        return bullet;
    }
}