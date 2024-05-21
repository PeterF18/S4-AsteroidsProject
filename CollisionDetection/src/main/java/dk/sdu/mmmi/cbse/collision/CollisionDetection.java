package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public class CollisionDetection implements IPostEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // If entity1 and entity2 is the same entity, skip
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                // CollisionDetection
                if (this.collides(entity1, entity2)) {
                    handleGenericCollision(entity1, world);
                    handleGenericCollision(entity2, world);

                }
            }
        }
    }

    private void handleGenericCollision(Entity entity, World world) {
        if (entity.getIsHit()) {
            world.removeEntity(entity);
        } else {
            entity.setIsHit(true);
        }
    }

    public Boolean collides(Entity entity1, Entity entity2) {
        double dx = entity1.getX() - entity2.getX();
        double dy = entity1.getY() - entity2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }
}
