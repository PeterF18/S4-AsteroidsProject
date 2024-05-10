package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface handles the post-processing of entities after initial updates have been performed.
 * It is intended for operations that must occur after all entities have been processed,
 * such as collision detection and handling.
 */
public interface IPostEntityProcessingService {

    /**
     * Processes game entities after initial updates to perform tasks that must happen
     * post-entity-update, such as collision handling and event triggering.
     *
     * @param gameData the game data containing current game state information
     * @param world    the game world containing the entities that might need post-processing
     * @pre gameData must not be null and should reflect the current state after entity processing.
     * @pre world must not be null and should contain entities that have been updated in this tick.
     * @post Entities in the world may have their states further updated based on post-processing logic.
     */
    void process(GameData gameData, World world);
}

