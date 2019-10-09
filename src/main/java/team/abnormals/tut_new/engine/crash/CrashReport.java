package team.abnormals.tut_new.engine.crash;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.SystemUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.abnormals.tut_new.SharedConstants;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

public class CrashReport {
   private static final Logger LOGGER = LogManager.getLogger();
   private final String message;
   private final Throwable cause;
   private final CrashReportSection systemDetailsSection = new CrashReportSection(this, "System Details");
   private final List<CrashReportSection> otherSections = Lists.newArrayList();
   private File file;
   private boolean hasStackTrace = true;
   private StackTraceElement[] stackTrace = new StackTraceElement[0];

   public CrashReport(String string_1, Throwable throwable_1) {
      this.message = string_1;
      this.cause = throwable_1;
      this.fillSystemDetails();
   }

   private void fillSystemDetails() {
      this.systemDetailsSection.add("Minecraft Version", () -> {
         return SharedConstants.getGameVersion().getName();
      });
      this.systemDetailsSection.add("Minecraft Version ID", () -> {
         return SharedConstants.getGameVersion().getId();
      });
      this.systemDetailsSection.add("Operating System", () -> {
         return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
      });
      this.systemDetailsSection.add("Java Version", () -> {
         return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
      });
      this.systemDetailsSection.add("Java VM Version", () -> {
         return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
      });
      this.systemDetailsSection.add("Memory", () -> {
         Runtime runtime_1 = Runtime.getRuntime();
         long long_1 = runtime_1.maxMemory();
         long long_2 = runtime_1.totalMemory();
         long long_3 = runtime_1.freeMemory();
         long long_4 = long_1 / 1024L / 1024L;
         long long_5 = long_2 / 1024L / 1024L;
         long long_6 = long_3 / 1024L / 1024L;
         return long_3 + " bytes (" + long_6 + " MB) / " + long_2 + " bytes (" + long_5 + " MB) up to " + long_1 + " bytes (" + long_4 + " MB)";
      });
      this.systemDetailsSection.add("CPUs", Runtime.getRuntime().availableProcessors());
      this.systemDetailsSection.add("JVM Flags", () -> {
         List<String> list_1 = SystemUtil.getJVMFlags().collect(Collectors.toList());
         return String.format("%d total; %s", list_1.size(), list_1.stream().collect(Collectors.joining(" ")));
      });
   }

   public String getMessage() {
      return this.message;
   }

   public Throwable getCause() {
      return this.cause;
   }

   public void addStackTrace(StringBuilder stringBuilder_1) {
      if ((this.stackTrace == null || this.stackTrace.length <= 0) && !this.otherSections.isEmpty()) {
         this.stackTrace = ArrayUtils.subarray(this.otherSections.get(0).getStackTrace(), 0, 1);
      }

      if (this.stackTrace != null && this.stackTrace.length > 0) {
         stringBuilder_1.append("-- Head --\n");
         stringBuilder_1.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
         stringBuilder_1.append("Stacktrace:\n");
         StackTraceElement[] var2 = this.stackTrace;

         for (StackTraceElement stackTraceElement_1 : var2) {
            stringBuilder_1.append("\t").append("at ").append(stackTraceElement_1);
            stringBuilder_1.append("\n");
         }

         stringBuilder_1.append("\n");
      }

      for (CrashReportSection crashReportSection_1 : this.otherSections) {
         crashReportSection_1.addStackTrace(stringBuilder_1);
         stringBuilder_1.append("\n\n");
      }

      this.systemDetailsSection.addStackTrace(stringBuilder_1);
   }

   public String getCauseAsString() {
      StringWriter stringWriter_1 = null;
      PrintWriter printWriter_1 = null;
      Throwable throwable_1 = this.cause;
      if (throwable_1.getMessage() == null) {
         if (throwable_1 instanceof NullPointerException) {
            throwable_1 = new NullPointerException(this.message);
         } else if (throwable_1 instanceof StackOverflowError) {
            throwable_1 = new StackOverflowError(this.message);
         } else if (throwable_1 instanceof OutOfMemoryError) {
            throwable_1 = new OutOfMemoryError(this.message);
         }

         throwable_1.setStackTrace(this.cause.getStackTrace());
      }

      String var4;
      try {
         stringWriter_1 = new StringWriter();
         printWriter_1 = new PrintWriter(stringWriter_1);
         throwable_1.printStackTrace(printWriter_1);
         var4 = stringWriter_1.toString();
      } finally {
         IOUtils.closeQuietly(stringWriter_1);
         IOUtils.closeQuietly(printWriter_1);
      }

      return var4;
   }

   public String asString() {
      StringBuilder stringBuilder_1 = new StringBuilder();
      stringBuilder_1.append("---- Minecraft Crash Report ----\n");
      stringBuilder_1.append("// ");
      stringBuilder_1.append(generateWittyComment());
      stringBuilder_1.append("\n\n");
      stringBuilder_1.append("Time: ");
      stringBuilder_1.append((new SimpleDateFormat()).format(new Date()));
      stringBuilder_1.append("\n");
      stringBuilder_1.append("Description: ");
      stringBuilder_1.append(this.message);
      stringBuilder_1.append("\n\n");
      stringBuilder_1.append(this.getCauseAsString());
      stringBuilder_1.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

      for(int int_1 = 0; int_1 < 87; ++int_1) {
         stringBuilder_1.append("-");
      }

      stringBuilder_1.append("\n\n");
      this.addStackTrace(stringBuilder_1);
      return stringBuilder_1.toString();
   }

   @Environment(EnvType.CLIENT)
   public File getFile() {
      return this.file;
   }

   public boolean writeToFile(File file_1) {
      if (this.file != null) {
         return false;
      } else {
         if (file_1.getParentFile() != null) {
            file_1.getParentFile().mkdirs();
         }

         OutputStreamWriter writer_1 = null;

         boolean var4;
         try {
            writer_1 = new OutputStreamWriter(new FileOutputStream(file_1), StandardCharsets.UTF_8);
            writer_1.write(this.asString());
            this.file = file_1;
            return true;
         } catch (Throwable var8) {
            LOGGER.error("Could not save crash report to {}", file_1, var8);
            var4 = false;
         } finally {
            IOUtils.closeQuietly(writer_1);
         }

         return var4;
      }
   }

   public CrashReportSection getSystemDetailsSection() {
      return this.systemDetailsSection;
   }

   public CrashReportSection addElement(String string_1) {
      return this.addElement(string_1, 1);
   }

   public CrashReportSection addElement(String string_1, int int_1) {
      CrashReportSection crashReportSection_1 = new CrashReportSection(this, string_1);
      if (this.hasStackTrace) {
         int int_2 = crashReportSection_1.trimStackTrace(int_1);
         StackTraceElement[] stackTraceElements_1 = this.cause.getStackTrace();
         StackTraceElement stackTraceElement_1 = null;
         StackTraceElement stackTraceElement_2 = null;
         int int_3 = stackTraceElements_1.length - int_2;
         if (int_3 < 0) {
            System.out.println("Negative index in crash report handler (" + stackTraceElements_1.length + "/" + int_2 + ")");
         }

         if (0 <= int_3 && int_3 < stackTraceElements_1.length) {
            stackTraceElement_1 = stackTraceElements_1[int_3];
            if (stackTraceElements_1.length + 1 - int_2 < stackTraceElements_1.length) {
               stackTraceElement_2 = stackTraceElements_1[stackTraceElements_1.length + 1 - int_2];
            }
         }

         this.hasStackTrace = crashReportSection_1.method_584(stackTraceElement_1, stackTraceElement_2);
         if (int_2 > 0 && !this.otherSections.isEmpty()) {
            CrashReportSection crashReportSection_2 = this.otherSections.get(this.otherSections.size() - 1);
            crashReportSection_2.method_580(int_2);
         } else if (stackTraceElements_1.length >= int_2 && 0 <= int_3 && int_3 < stackTraceElements_1.length) {
            this.stackTrace = new StackTraceElement[int_3];
            System.arraycopy(stackTraceElements_1, 0, this.stackTrace, 0, this.stackTrace.length);
         } else {
            this.hasStackTrace = false;
         }
      }

      this.otherSections.add(crashReportSection_1);
      return crashReportSection_1;
   }

   private static String generateWittyComment() {
      String[] strings_1 = new String[]{"Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine."};

      try {
         return strings_1[(int)(SystemUtil.getMeasuringTimeNano() % (long)strings_1.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }

   public static CrashReport create(Throwable throwable_1, String string_1) {
      while(throwable_1 instanceof CompletionException && throwable_1.getCause() != null) {
         throwable_1 = throwable_1.getCause();
      }

      CrashReport crashReport_2;
      if (throwable_1 instanceof CrashException) {
         crashReport_2 = ((CrashException)throwable_1).getReport();
      } else {
         crashReport_2 = new CrashReport(string_1, throwable_1);
      }

      return crashReport_2;
   }
}
