package team.abnormals.tut_new.engine;

import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.command.EntitySelectorOptions;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.DebugPrintStreamLogger;
import net.minecraft.util.Language;
import net.minecraft.util.PrintStreamLogger;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.abnormals.tut_new.SharedConstants;

import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

public class Bootstrap {
   public static final PrintStream SYSOUT;
   private static boolean initialized;
   private static final Logger LOGGER;

   public static void initialize() {
      if (!initialized) {
         initialized = true;
         if (Registry.REGISTRIES.isEmpty()) {
            throw new IllegalStateException("Unable to load registries");
         } else {
            FireBlock.registerDefaultFlammables();
            ComposterBlock.registerDefaultCompostableItems();
            if (EntityType.getId(EntityType.PLAYER) == null) {
               throw new IllegalStateException("Failed loading EntityTypes");
            } else {
               BrewingRecipeRegistry.registerDefaults();
               EntitySelectorOptions.register();
               DispenserBehavior.registerDefaults();
               ArgumentTypes.register();
               setOutputStreams();
            }
         }
      }
   }

   private static <T> void collectMissingTranslations(Registry<T> registry_1, Function<T, String> function_1, Set<String> set_1) {
      Language language_1 = Language.getInstance();
      registry_1.iterator().forEachRemaining((object_1) -> {
         String string_1 = (String)function_1.apply(object_1);
         if (!language_1.hasTranslation(string_1)) {
            set_1.add(string_1);
         }

      });
   }

   public static Set<String> getMissingTranslations() {
      Set<String> set_1 = new TreeSet();
      collectMissingTranslations(Registry.ENTITY_TYPE, EntityType::getTranslationKey, set_1);
      collectMissingTranslations(Registry.STATUS_EFFECT, StatusEffect::getTranslationKey, set_1);
      collectMissingTranslations(Registry.ITEM, Item::getTranslationKey, set_1);
      collectMissingTranslations(Registry.ENCHANTMENT, Enchantment::getTranslationKey, set_1);
      collectMissingTranslations(Registry.BIOME, Biome::getTranslationKey, set_1);
      collectMissingTranslations(Registry.BLOCK, Block::getTranslationKey, set_1);
      collectMissingTranslations(Registry.CUSTOM_STAT, (identifier_1) -> "stat." + identifier_1.toString().replace(':', '.'), set_1);
      return set_1;
   }

   public static void logMissingTranslations() {
      if (!initialized) {
         throw new IllegalArgumentException("Not bootstrapped");
      } else {
         if (SharedConstants.isDevelopment) {
            getMissingTranslations().forEach((string_1) -> {
               LOGGER.error("Missing translations: " + string_1);
            });
         }

      }
   }

   private static void setOutputStreams() {
      if (LOGGER.isDebugEnabled()) {
         System.setErr(new DebugPrintStreamLogger("STDERR", System.err));
         System.setOut(new DebugPrintStreamLogger("STDOUT", SYSOUT));
      } else {
         System.setErr(new PrintStreamLogger("STDERR", System.err));
         System.setOut(new PrintStreamLogger("STDOUT", SYSOUT));
      }

   }

   public static void println(String string_1) {
      SYSOUT.println(string_1);
   }

   static {
      SYSOUT = System.out;
      LOGGER = LogManager.getLogger();
   }
}
