package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface defines the contract for entity processing services, which handle the
 * updating of game entities' logic per game tick.
 */
public interface IEntityProcessingService {

    /**
     * Processes game entities' logic based on the current game state.
     * This method is called on each game tick (frame) to perform updates
     * such as movement, collision checks, and other game logic.
     *
     * @param gameData the game data that includes details about the current game state
     * @param world    the game world that contains the entities to be processed
     * @pre gameData must not be null and should contain the current state of the game loop.
     * @pre world must not be null and must be populated with the current active game entities.
     * @post Entities in the world may have their states updated based on the game logic.
     */
    void process(GameData gameData, World world);
}

