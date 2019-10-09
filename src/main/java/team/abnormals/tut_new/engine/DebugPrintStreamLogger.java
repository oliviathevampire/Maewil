package team.abnormals.tut_new.engine;

import java.io.OutputStream;

public class DebugPrintStreamLogger extends PrintStreamLogger {
   public DebugPrintStreamLogger(String string_1, OutputStream outputStream_1) {
      super(string_1, outputStream_1);
   }

   protected void log(String string_1) {
      StackTraceElement[] stackTraceElements_1 = Thread.currentThread().getStackTrace();
      StackTraceElement stackTraceElement_1 = stackTraceElements_1[Math.min(3, stackTraceElements_1.length)];
      LOGGER.info("[{}]@.({}:{}): {}", this.name, stackTraceElement_1.getFileName(), stackTraceElement_1.getLineNumber(), string_1);
   }
}
