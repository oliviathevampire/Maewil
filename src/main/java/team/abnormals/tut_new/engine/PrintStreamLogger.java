package team.abnormals.tut_new.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.OutputStream;
import java.io.PrintStream;

public class PrintStreamLogger extends PrintStream {
   protected static final Logger LOGGER = LogManager.getLogger();
   protected final String name;

   public PrintStreamLogger(String string_1, OutputStream outputStream_1) {
      super(outputStream_1);
      this.name = string_1;
   }

   public void println(@Nullable String string_1) {
      this.log(string_1);
   }

   public void println(Object object_1) {
      this.log(String.valueOf(object_1));
   }

   protected void log(@Nullable String string_1) {
      LOGGER.info("[{}]: {}", this.name, string_1);
   }
}
