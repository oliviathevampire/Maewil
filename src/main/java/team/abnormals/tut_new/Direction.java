package team.abnormals.tut_new;

import com.google.common.collect.Iterators;
import com.mojang.blaze3d.MathHelper;
import com.mojang.blaze3d.Vec3i;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum Direction implements StringIdentifiable {
   DOWN(0, 1, -1, "down", Direction.AxisDirection.NEGATIVE, Direction.Axis.Y, new Vec3i(0, -1, 0)),
   UP(1, 0, -1, "up", Direction.AxisDirection.POSITIVE, Direction.Axis.Y, new Vec3i(0, 1, 0)),
   NORTH(2, 3, 2, "north", Direction.AxisDirection.NEGATIVE, Direction.Axis.Z, new Vec3i(0, 0, -1)),
   SOUTH(3, 2, 0, "south", Direction.AxisDirection.POSITIVE, Direction.Axis.Z, new Vec3i(0, 0, 1)),
   WEST(4, 5, 1, "west", Direction.AxisDirection.NEGATIVE, Direction.Axis.X, new Vec3i(-1, 0, 0)),
   EAST(5, 4, 3, "east", Direction.AxisDirection.POSITIVE, Direction.Axis.X, new Vec3i(1, 0, 0));

   private final int id;
   private final int idOpposite;
   private final int idHorizontal;
   private final String name;
   private final Direction.Axis axis;
   private final Direction.AxisDirection direction;
   private final Vec3i vector;
   private static final Direction[] ALL = values();
   private static final Map<String, Direction> NAME_MAP = Arrays.stream(ALL).collect(Collectors.toMap(Direction::getName, (direction_1) -> {
      return direction_1;
   }));
   private static final Direction[] ID_TO_DIRECTION = Arrays.stream(ALL).sorted(Comparator.comparingInt((direction_1) -> {
      return direction_1.id;
   })).toArray((int_1) -> {
      return new Direction[int_1];
   });
   private static final Direction[] HORIZONTAL = Arrays.stream(ALL).filter((direction_1) -> {
      return direction_1.getAxis().isHorizontal();
   }).sorted(Comparator.comparingInt((direction_1) -> {
      return direction_1.idHorizontal;
   })).toArray((int_1) -> {
      return new Direction[int_1];
   });
   private static final Long2ObjectMap<Direction> VECTOR_TO_DIRECTION = Arrays.stream(ALL).collect(Collectors.toMap((direction_1) -> {
      return (new BlockPos(direction_1.getVector())).asLong();
   }, (direction_1) -> {
      return direction_1;
   }, (direction_1, direction_2) -> {
      throw new IllegalArgumentException("Duplicate keys");
   }, Long2ObjectOpenHashMap::new));

   Direction(int int_1, int int_2, int int_3, String string_1, Direction.AxisDirection direction$AxisDirection_1, Direction.Axis direction$Axis_1, Vec3i vec3i_1) {
      this.id = int_1;
      this.idHorizontal = int_3;
      this.idOpposite = int_2;
      this.name = string_1;
      this.axis = direction$Axis_1;
      this.direction = direction$AxisDirection_1;
      this.vector = vec3i_1;
   }

   private static Direction[] method_10145(Direction direction_1, Direction direction_2, Direction direction_3) {
      return new Direction[]{direction_1, direction_2, direction_3, direction_3.getOpposite(), direction_2.getOpposite(), direction_1.getOpposite()};
   }

   public int getId() {
      return this.id;
   }

   public int getHorizontal() {
      return this.idHorizontal;
   }

   public Direction.AxisDirection getDirection() {
      return this.direction;
   }

   public Direction getOpposite() {
      return byId(this.idOpposite);
   }

   @Environment(EnvType.CLIENT)
   public Direction rotateClockwise(Direction.Axis direction$Axis_1) {
      switch(direction$Axis_1) {
      case X:
         if (this != WEST && this != EAST) {
            return this.rotateXClockwise();
         }

         return this;
      case Y:
         if (this != UP && this != DOWN) {
            return this.rotateYClockwise();
         }

         return this;
      case Z:
         if (this != NORTH && this != SOUTH) {
            return this.rotateZClockwise();
         }

         return this;
      default:
         throw new IllegalStateException("Unable to get CW facing for axis " + direction$Axis_1);
      }
   }

   public Direction rotateYClockwise() {
      switch(this) {
      case NORTH:
         return EAST;
      case EAST:
         return SOUTH;
      case SOUTH:
         return WEST;
      case WEST:
         return NORTH;
      default:
         throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      }
   }

   @Environment(EnvType.CLIENT)
   private Direction rotateXClockwise() {
      switch(this) {
      case NORTH:
         return DOWN;
      case EAST:
      case WEST:
      default:
         throw new IllegalStateException("Unable to get X-rotated facing of " + this);
      case SOUTH:
         return UP;
      case UP:
         return NORTH;
      case DOWN:
         return SOUTH;
      }
   }

   @Environment(EnvType.CLIENT)
   private Direction rotateZClockwise() {
      switch(this) {
      case EAST:
         return DOWN;
      case SOUTH:
      default:
         throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
      case WEST:
         return UP;
      case UP:
         return EAST;
      case DOWN:
         return WEST;
      }
   }

   public Direction rotateYCounterclockwise() {
      switch(this) {
      case NORTH:
         return WEST;
      case EAST:
         return NORTH;
      case SOUTH:
         return EAST;
      case WEST:
         return SOUTH;
      default:
         throw new IllegalStateException("Unable to get CCW facing of " + this);
      }
   }

   public int getOffsetX() {
      return this.axis == Direction.Axis.X ? this.direction.offset() : 0;
   }

   public int getOffsetY() {
      return this.axis == Direction.Axis.Y ? this.direction.offset() : 0;
   }

   public int getOffsetZ() {
      return this.axis == Direction.Axis.Z ? this.direction.offset() : 0;
   }

   public String getName() {
      return this.name;
   }

   public Direction.Axis getAxis() {
      return this.axis;
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public static Direction byName(@Nullable String string_1) {
      return string_1 == null ? null : NAME_MAP.get(string_1.toLowerCase(Locale.ROOT));
   }

   public static Direction byId(int int_1) {
      return ID_TO_DIRECTION[MathHelper.abs(int_1 % ID_TO_DIRECTION.length)];
   }

   public static Direction fromHorizontal(int int_1) {
      return HORIZONTAL[MathHelper.abs(int_1 % HORIZONTAL.length)];
   }

   @Nullable
   public static Direction fromVector(int int_1, int int_2, int int_3) {
      return VECTOR_TO_DIRECTION.get(BlockPos.asLong(int_1, int_2, int_3));
   }

   public static Direction fromRotation(double double_1) {
      return fromHorizontal(MathHelper.floor(double_1 / 90.0D + 0.5D) & 3);
   }

   public static Direction from(Direction.Axis direction$Axis_1, Direction.AxisDirection direction$AxisDirection_1) {
      switch(direction$Axis_1) {
      case X:
         return direction$AxisDirection_1 == Direction.AxisDirection.POSITIVE ? EAST : WEST;
      case Y:
         return direction$AxisDirection_1 == Direction.AxisDirection.POSITIVE ? UP : DOWN;
      case Z:
      default:
         return direction$AxisDirection_1 == Direction.AxisDirection.POSITIVE ? SOUTH : NORTH;
      }
   }

   public float asRotation() {
      return (float)((this.idHorizontal & 3) * 90);
   }

   public static Direction random(Random random_1) {
      return values()[random_1.nextInt(values().length)];
   }

   public static Direction getFacing(double double_1, double double_2, double double_3) {
      return getFacing((float)double_1, (float)double_2, (float)double_3);
   }

   public static Direction getFacing(float float_1, float float_2, float float_3) {
      Direction direction_1 = NORTH;
      float float_4 = Float.MIN_VALUE;
      Direction[] var5 = ALL;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Direction direction_2 = var5[var7];
         float float_5 = float_1 * (float)direction_2.vector.getX() + float_2 * (float)direction_2.vector.getY() + float_3 * (float)direction_2.vector.getZ();
         if (float_5 > float_4) {
            float_4 = float_5;
            direction_1 = direction_2;
         }
      }

      return direction_1;
   }

   public String toString() {
      return this.name;
   }

   public String asString() {
      return this.name;
   }

   public static Direction get(Direction.AxisDirection direction$AxisDirection_1, Direction.Axis direction$Axis_1) {
      Direction[] var2 = values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Direction direction_1 = var2[var4];
         if (direction_1.getDirection() == direction$AxisDirection_1 && direction_1.getAxis() == direction$Axis_1) {
            return direction_1;
         }
      }

      throw new IllegalArgumentException("No such direction: " + direction$AxisDirection_1 + " " + direction$Axis_1);
   }

   public Vec3i getVector() {
      return this.vector;
   }

   public enum Type implements Iterable<Direction>, Predicate<Direction> {
      HORIZONTAL(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}, new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z}),
      VERTICAL(new Direction[]{Direction.UP, Direction.DOWN}, new Direction.Axis[]{Direction.Axis.Y});

      private final Direction[] facingArray;
      private final Direction.Axis[] axisArray;

      Type(Direction[] directions_1, Direction.Axis[] direction$Axiss_1) {
         this.facingArray = directions_1;
         this.axisArray = direction$Axiss_1;
      }

      public Direction random(Random random_1) {
         return this.facingArray[random_1.nextInt(this.facingArray.length)];
      }

      public boolean test(@Nullable Direction direction_1) {
         return direction_1 != null && direction_1.getAxis().getType() == this;
      }

      public Iterator<Direction> iterator() {
         return Iterators.forArray(this.facingArray);
      }

   }

   public enum AxisDirection {
      POSITIVE(1, "Towards positive"),
      NEGATIVE(-1, "Towards negative");

      private final int offset;
      private final String desc;

      AxisDirection(int int_1, String string_1) {
         this.offset = int_1;
         this.desc = string_1;
      }

      public int offset() {
         return this.offset;
      }

      public String toString() {
         return this.desc;
      }
   }

   public enum Axis implements StringIdentifiable, Predicate<Direction> {
      X("x") {
         public int choose(int int_1, int int_2, int int_3) {
            return int_1;
         }

         public double choose(double double_1, double double_2, double double_3) {
            return double_1;
         }
      },
      Y("y") {
         public int choose(int int_1, int int_2, int int_3) {
            return int_2;
         }

         public double choose(double double_1, double double_2, double double_3) {
            return double_2;
         }
      },
      Z("z") {
         public int choose(int int_1, int int_2, int int_3) {
            return int_3;
         }

         public double choose(double double_1, double double_2, double double_3) {
            return double_3;
         }
      };

      private static final Map<String, Direction.Axis> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(Axis::getName, (direction$Axis_1) -> {
         return direction$Axis_1;
      }));
      private final String name;

      Axis(String string_1) {
         this.name = string_1;
      }

      @Nullable
      @Environment(EnvType.CLIENT)
      public static Direction.Axis fromName(String string_1) {
         return BY_NAME.get(string_1.toLowerCase(Locale.ROOT));
      }

      public String getName() {
         return this.name;
      }

      public boolean isVertical() {
         return this == Y;
      }

      public boolean isHorizontal() {
         return this == X || this == Z;
      }

      public String toString() {
         return this.name;
      }

      public static Direction.Axis method_16699(Random random_1) {
         return values()[random_1.nextInt(values().length)];
      }

      public boolean test(@Nullable Direction direction_1) {
         return direction_1 != null && direction_1.getAxis() == this;
      }

      public Direction.Type getType() {
         switch(this) {
         case X:
         case Z:
            return Direction.Type.HORIZONTAL;
         case Y:
            return Direction.Type.VERTICAL;
         default:
            throw new Error("Someone's been tampering with the universe!");
         }
      }

      public String asString() {
         return this.name;
      }

      public abstract int choose(int var1, int var2, int var3);

      public abstract double choose(double var1, double var3, double var5);
   }
}
