package team.abnormals.tut_new.engine.crash;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.abnormals.tut_new.BlockPos;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CrashReportSection {
   private final CrashReport report;
   private final String title;
   private final List<CrashReportSection.Element> elements = Lists.newArrayList();
   private StackTraceElement[] stackTrace = new StackTraceElement[0];

   public CrashReportSection(CrashReport crashReport_1, String string_1) {
      this.report = crashReport_1;
      this.title = string_1;
   }

   @Environment(EnvType.CLIENT)
   public static String createPositionString(double double_1, double double_2, double double_3) {
      return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", double_1, double_2, double_3, createPositionString(new BlockPos(double_1, double_2, double_3)));
   }

   public static String createPositionString(BlockPos blockPos_1) {
      return createPositionString(blockPos_1.getX(), blockPos_1.getY(), blockPos_1.getZ());
   }

   public static String createPositionString(int int_1, int int_2, int int_3) {
      StringBuilder stringBuilder_1 = new StringBuilder();

      try {
         stringBuilder_1.append(String.format("World: (%d,%d,%d)", int_1, int_2, int_3));
      } catch (Throwable var16) {
         stringBuilder_1.append("(Error finding world loc)");
      }

      stringBuilder_1.append(", ");

      int int_13;
      int int_14;
      int int_15;
      int int_16;
      int int_17;
      int int_18;
      int int_19;
      int int_20;
      int int_21;
      try {
         int_13 = int_1 >> 4;
         int_14 = int_3 >> 4;
         int_15 = int_1 & 15;
         int_16 = int_2 >> 4;
         int_17 = int_3 & 15;
         int_18 = int_13 << 4;
         int_19 = int_14 << 4;
         int_20 = (int_13 + 1 << 4) - 1;
         int_21 = (int_14 + 1 << 4) - 1;
         stringBuilder_1.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", int_15, int_16, int_17, int_13, int_14, int_18, int_19, int_20, int_21));
      } catch (Throwable var15) {
         stringBuilder_1.append("(Error finding chunk loc)");
      }

      stringBuilder_1.append(", ");

      try {
         int_13 = int_1 >> 9;
         int_14 = int_3 >> 9;
         int_15 = int_13 << 5;
         int_16 = int_14 << 5;
         int_17 = (int_13 + 1 << 5) - 1;
         int_18 = (int_14 + 1 << 5) - 1;
         int_19 = int_13 << 9;
         int_20 = int_14 << 9;
         int_21 = (int_13 + 1 << 9) - 1;
         int int_22 = (int_14 + 1 << 9) - 1;
         stringBuilder_1.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", int_13, int_14, int_15, int_16, int_17, int_18, int_19, int_20, int_21, int_22));
      } catch (Throwable var14) {
         stringBuilder_1.append("(Error finding world loc)");
      }

      return stringBuilder_1.toString();
   }

   public CrashReportSection add(String string_1, CrashCallable<String> crashCallable_1) {
      try {
         this.add(string_1, crashCallable_1.call());
      } catch (Throwable var4) {
         this.add(string_1, var4);
      }

      return this;
   }

   public CrashReportSection add(String string_1, Object object_1) {
      this.elements.add(new CrashReportSection.Element(string_1, object_1));
      return this;
   }

   public void add(String string_1, Throwable throwable_1) {
      this.add(string_1, (Object)throwable_1);
   }

   public int trimStackTrace(int int_1) {
      StackTraceElement[] stackTraceElements_1 = Thread.currentThread().getStackTrace();
      if (stackTraceElements_1.length <= 0) {
         return 0;
      } else {
         this.stackTrace = new StackTraceElement[stackTraceElements_1.length - 3 - int_1];
         System.arraycopy(stackTraceElements_1, 3 + int_1, this.stackTrace, 0, this.stackTrace.length);
         return this.stackTrace.length;
      }
   }

   public boolean method_584(StackTraceElement stackTraceElement_1, StackTraceElement stackTraceElement_2) {
      if (this.stackTrace.length != 0 && stackTraceElement_1 != null) {
         StackTraceElement stackTraceElement_3 = this.stackTrace[0];
         if (stackTraceElement_3.isNativeMethod() == stackTraceElement_1.isNativeMethod() && stackTraceElement_3.getClassName().equals(stackTraceElement_1.getClassName()) && stackTraceElement_3.getFileName().equals(stackTraceElement_1.getFileName()) && stackTraceElement_3.getMethodName().equals(stackTraceElement_1.getMethodName())) {
            if (stackTraceElement_2 != null != this.stackTrace.length > 1) {
               return false;
            } else if (stackTraceElement_2 != null && !this.stackTrace[1].equals(stackTraceElement_2)) {
               return false;
            } else {
               this.stackTrace[0] = stackTraceElement_1;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void method_580(int int_1) {
      StackTraceElement[] stackTraceElements_1 = new StackTraceElement[this.stackTrace.length - int_1];
      System.arraycopy(this.stackTrace, 0, stackTraceElements_1, 0, stackTraceElements_1.length);
      this.stackTrace = stackTraceElements_1;
   }

   public void addStackTrace(StringBuilder stringBuilder_1) {
      stringBuilder_1.append("-- ").append(this.title).append(" --\n");
      stringBuilder_1.append("Details:");
      Iterator var2 = this.elements.iterator();

      while(var2.hasNext()) {
         CrashReportSection.Element crashReportSection$Element_1 = (CrashReportSection.Element)var2.next();
         stringBuilder_1.append("\n\t");
         stringBuilder_1.append(crashReportSection$Element_1.getName());
         stringBuilder_1.append(": ");
         stringBuilder_1.append(crashReportSection$Element_1.getDetail());
      }

      if (this.stackTrace != null && this.stackTrace.length > 0) {
         stringBuilder_1.append("\nStacktrace:");
         StackTraceElement[] var6 = this.stackTrace;
         int var7 = var6.length;

         for(int var4 = 0; var4 < var7; ++var4) {
            StackTraceElement stackTraceElement_1 = var6[var4];
            stringBuilder_1.append("\n\tat ");
            stringBuilder_1.append(stackTraceElement_1);
         }
      }

   }

   public StackTraceElement[] getStackTrace() {
      return this.stackTrace;
   }

   static class Element {
      private final String name;
      private final String detail;

      public Element(String string_1, Object object_1) {
         this.name = string_1;
         if (object_1 == null) {
            this.detail = "~~NULL~~";
         } else if (object_1 instanceof Throwable) {
            Throwable throwable_1 = (Throwable)object_1;
            this.detail = "~~ERROR~~ " + throwable_1.getClass().getSimpleName() + ": " + throwable_1.getMessage();
         } else {
            this.detail = object_1.toString();
         }

      }

      public String getName() {
         return this.name;
      }

      public String getDetail() {
         return this.detail;
      }
   }
}
