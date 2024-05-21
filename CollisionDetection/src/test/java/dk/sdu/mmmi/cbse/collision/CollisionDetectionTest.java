package dk.sdu.mmmi.cbse.collision;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class CollisionDetectionTest {

    @Test
    public void testEntitiesCollideWhenExpected() {
        // Arrange
        CollisionDetection collisionDetector = new CollisionDetection();
        Entity entity1 = new Entity();
        entity1.setX(100);
        entity1.setY(100);
        entity1.setRadius(10);

        Entity entity2 = new Entity();
        entity2.setX(110); // Adjusted to be within 20 units distance
        entity2.setY(100);
        entity2.setRadius(10);

        // Act
        boolean result = collisionDetector.collides(entity1, entity2);

        // Assert
        assertTrue(result, "Entities should collide as they are within each other's radius.");
    }

    @Test
    public void testEntitiesDoNotCollideWhenExpected() {
        // Arrange
        CollisionDetection collisionDetector = new CollisionDetection();
        Entity entity1 = new Entity();
        entity1.setX(100);
        entity1.setY(100);
        entity1.setRadius(10);

        Entity entity2 = new Entity();
        entity2.setX(130); // Adjusted to be more than 20 units apart
        entity2.setY(100);
        entity2.setRadius(10);

        // Act
        boolean result = collisionDetector.collides(entity1, entity2);

        // Assert
        assertFalse(result, "Entities should not collide as they are outside each other's radius.");
    }
}