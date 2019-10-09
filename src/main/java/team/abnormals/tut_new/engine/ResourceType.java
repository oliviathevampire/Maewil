package team.abnormals.tut_new.engine;

public enum ResourceType {
   CLIENT_RESOURCES("assets"),
   SERVER_DATA("data");

   private final String name;

   private ResourceType(String string_1) {
      this.name = string_1;
   }

   public String getName() {
      return this.name;
   }
}
