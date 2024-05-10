package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface is used for game plugins that initialize and clean up game entities.
 */
public interface IGamePluginService {

    /**
     * Initializes and adds entities to the game world at the start of the game.
     *
     * @param gameData the game data for current game initialization context
     * @param world    the game world where the entities should be added
     * @pre gameData must not be null and should be ready to be used for initialization.
     * @pre world must not be null and should be in a state ready to accept new entities.
     * @post Entities required for the start of the game are added to the world.
     */
    void start(GameData gameData, World world);

    /**
     * Cleans up resources and removes entities from the game world upon stopping the game.
     *
     * @param gameData the game data that may be used to determine which entities to clean up
     * @param world    the game world from which entities should be removed
     * @pre gameData must not be null and should reflect the current state before stopping.
     * @pre world must not be null and should contain entities that were previously added.
     * @post Entities added by the start method are removed from the world.
     */
    void stop(GameData gameData, World world);
}
