package team.abnormals.tut_new.engine.crash;

public class CrashException extends RuntimeException {
   private final CrashReport report;

   public CrashException(CrashReport crashReport_1) {
      this.report = crashReport_1;
   }

   public CrashReport getReport() {
      return this.report;
   }

   public Throwable getCause() {
      return this.report.getCause();
   }

   public String getMessage() {
      return this.report.getMessage();
   }
}
