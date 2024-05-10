package dk.sdu.mmmi.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {



    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {

        // Initialize the Spring container
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ModuleConfig.class);

        // Print out all the bean names for debugging purposes
        for (String beanName : ctx.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }

        // Retrieve the Game bean from the Spring container
        Game game = ctx.getBean(Game.class);
        game.start(window);
        game.render();
    }
}
