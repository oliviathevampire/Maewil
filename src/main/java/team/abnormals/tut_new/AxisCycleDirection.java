package team.abnormals.tut_new;

public enum AxisCycleDirection {
   NONE {
      public int choose(int int_1, int int_2, int int_3, Direction.Axis direction$Axis_1) {
         return direction$Axis_1.choose(int_1, int_2, int_3);
      }

      public Direction.Axis cycle(Direction.Axis direction$Axis_1) {
         return direction$Axis_1;
      }

      public AxisCycleDirection opposite() {
         return this;
      }
   },
   FORWARD {
      public int choose(int int_1, int int_2, int int_3, Direction.Axis direction$Axis_1) {
         return direction$Axis_1.choose(int_3, int_1, int_2);
      }

      public Direction.Axis cycle(Direction.Axis direction$Axis_1) {
         return AXES[Math.floorMod(direction$Axis_1.ordinal() + 1, 3)];
      }

      public AxisCycleDirection opposite() {
         return BACKWARD;
      }
   },
   BACKWARD {
      public int choose(int int_1, int int_2, int int_3, Direction.Axis direction$Axis_1) {
         return direction$Axis_1.choose(int_2, int_3, int_1);
      }

      public Direction.Axis cycle(Direction.Axis direction$Axis_1) {
         return AXES[Math.floorMod(direction$Axis_1.ordinal() - 1, 3)];
      }

      public AxisCycleDirection opposite() {
         return FORWARD;
      }
   };

   public static final Direction.Axis[] AXES = Direction.Axis.values();
   public static final AxisCycleDirection[] VALUES = values();

   private AxisCycleDirection() {
   }

   public abstract int choose(int var1, int var2, int var3, Direction.Axis var4);

   public abstract Direction.Axis cycle(Direction.Axis var1);

   public abstract AxisCycleDirection opposite();

   public static AxisCycleDirection between(Direction.Axis direction$Axis_1, Direction.Axis direction$Axis_2) {
      return VALUES[Math.floorMod(direction$Axis_2.ordinal() - direction$Axis_1.ordinal(), 3)];
   }

   // $FF: synthetic method
   AxisCycleDirection(Object axisCycleDirection$1_1) {
      this();
   }
}
