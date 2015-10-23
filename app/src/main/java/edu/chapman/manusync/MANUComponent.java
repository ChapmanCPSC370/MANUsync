package edu.chapman.manusync;

import javax.inject.Singleton;

import dagger.Component;
import edu.chapman.manusync.activity.LogInActivity;
import edu.chapman.manusync.activity.MainMenuActivity;
import edu.chapman.manusync.activity.NewLotActivity;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/8/2015.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface MANUComponent {
    void inject(LogInActivity logInActivity);
    void inject(MainMenuActivity mainMenuActivity);
    void inject(NewLotActivity newLotActivity);

    final class Instance {
        private static MANUComponent component;

        /**
         * Initialize the object graph from Application onCreate.
         */
        public static void init(BaseApplication application) {
            component = DaggerMANUComponent.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }

        public static MANUComponent get() {
            return component;
        }
    }
}
