package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Game {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();


    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServiceList;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;

    Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServiceList, List<IPostEntityProcessingService> postEntityProcessingServices) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServiceList = entityProcessingServiceList;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }


    public void start(Stage window) throws Exception {
        Text text = new Text(10, 20, "Destroyed asteroids: 0");
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(text);

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
        });

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getGamePluginServices()) {
            iGamePlugin.start(gameData, world);
        }
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }

        render();

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
    }

    public void render() {
        new AnimationTimer() {
            private long then = 0;

            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
            }

        }.start();
    }

    private void update() {

        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        // Create a list to keep track of entities that need their polygons removed
        List<Entity> entitiesToRemove = new ArrayList<>();


        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }

        // Find polygons that no longer have an associated entity in the world
        for (Entity entity : polygons.keySet()) {
            if (!world.getEntities().contains(entity)) {
                entitiesToRemove.add(entity);
            }
        }

        // Remove the polygons from the game window and the map
        for (Entity entity : entitiesToRemove) {
            Polygon polygon = polygons.get(entity);
            gameWindow.getChildren().remove(polygon);
            polygons.remove(entity);
        }

    }

    public List<IGamePluginService> getGamePluginServices() {
        return gamePluginServices;
    }

    public List<IEntityProcessingService> getEntityProcessingServices() {
        return entityProcessingServiceList;
    }

    public List<IPostEntityProcessingService> getPostEntityProcessingServices() {
        return postEntityProcessingServices;
    }


// Code used for previous lab before SpringLab, which uses DI:

//    Path pluginsDir = Paths.get("plugins"); // Directory with plugins JARs
//
//    // Search for plugins in the plugins directory
//    ModuleFinder pluginsFinder = ModuleFinder.of(pluginsDir);
//
//    // Find all names of all found plugin modules
//    List<String> plugins = pluginsFinder
//            .findAll()
//            .stream()
//            .map(ModuleReference::descriptor)
//            .map(ModuleDescriptor::name)
//            .collect(Collectors.toList());
//
//    // Create configuration that will resolve plugin modules
//    // (verify that the graph of modules is correct)
//    Configuration pluginsConfiguration = ModuleLayer
//            .boot()
//            .configuration()
//            .resolve(pluginsFinder, ModuleFinder.of(), plugins);
//
//    // Create a module layer for plugins
//    layer = ModuleLayer
//            .boot()
//            .defineModulesWithOneLoader(pluginsConfiguration, ClassLoader.getSystemClassLoader());
//
//
//    launch(Main.class);
//}

//    private Collection<? extends IGamePluginService> getPluginServices() {
//        return ServiceLoader.load(layer, IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
//    }
//
//    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
//        return ServiceLoader.load(layer, IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
//    }
//
//    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
//        return ServiceLoader.load(layer, IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
//    }
//
//    private static ModuleLayer createLayer(String from, String module) {
//        System.out.println("Layer created");
//
//        var finder = ModuleFinder.of(Paths.get(from));
//        var parent = ModuleLayer.boot();
//        var cf = parent.configuration().resolve(finder, ModuleFinder.of(), Set.of(module));
//        return parent.defineModulesWithOneLoader(cf, ClassLoader.getSystemClassLoader());
//    }

}
