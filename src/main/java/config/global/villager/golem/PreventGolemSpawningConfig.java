package config.global.villager.golem;

import config.global.villager.VillagerSettingsConfig;
import fr.bramsou.yaml.api.configuration.dynamic.ConfigurationPart;
import fr.bramsou.yaml.api.configuration.dynamic.annotation.ConfigurationPath;

public class PreventGolemSpawningConfig extends ConfigurationPart {

    public static PreventGolemSpawningConfig getInstance() {
        return VillagerSettingsConfig.getInstance().getPreventGolemSpawning();
    }

    @ConfigurationPath(value = "village-defense", comments = "Prevents iron golem spawning when villager is scared or attacked.")
    private boolean villageDefense = false;
    @ConfigurationPath(value = "village-raids", comments = "Prevents iron golem spawning when the village is raiding.")
    private boolean villageRaids = false;

    public boolean isVillageDefense() {
        return villageDefense;
    }

    public boolean isVillageRaids() {
        return villageRaids;
    }
}