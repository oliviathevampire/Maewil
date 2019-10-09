package team.abnormals.tut_new;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.MathHelper;
import com.mojang.blaze3d.Position;
import com.mojang.blaze3d.Vec3d;
import com.mojang.blaze3d.Vec3i;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.concurrent.Immutable;
import java.util.List;
import java.util.Spliterator.OfInt;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Immutable
public class BlockPos extends Vec3i implements DynamicSerializable {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
   private static final int SIZE_BITS_X = 1 + MathHelper.log2(MathHelper.smallestEncompassingPowerOfTwo(30000000));
   private static final int SIZE_BITS_Z;
   private static final int SIZE_BITS_Y;
   private static final long BITS_X;
   private static final long BITS_Y;
   private static final long BITS_Z;
   private static final int BIT_SHIFT_Z;
   private static final int BIT_SHIFT_X;

   public BlockPos(int int_1, int int_2, int int_3) {
      super(int_1, int_2, int_3);
   }

   public BlockPos(double double_1, double double_2, double double_3) {
      super(double_1, double_2, double_3);
   }

   public BlockPos(Vec3d vec3d_1) {
      this(vec3d_1.x, vec3d_1.y, vec3d_1.z);
   }

   public BlockPos(Position position_1) {
      this(position_1.getX(), position_1.getY(), position_1.getZ());
   }

   public BlockPos(Vec3i vec3i_1) {
      this(vec3i_1.getX(), vec3i_1.getY(), vec3i_1.getZ());
   }

   public static <T> BlockPos deserialize(Dynamic<T> dynamic_1) {
      OfInt spliterator$OfInt_1 = dynamic_1.asIntStream().spliterator();
      int[] ints_1 = new int[3];
      if (spliterator$OfInt_1.tryAdvance((Consumer<? super Integer>) (int_1) -> {
         ints_1[0] = int_1;
      }) && spliterator$OfInt_1.tryAdvance((Consumer<? super Integer>) (int_1) -> {
         ints_1[1] = int_1;
      })) {
         spliterator$OfInt_1.tryAdvance((Consumer<? super Integer>) (int_1) -> {
            ints_1[2] = int_1;
         });
      }

      return new BlockPos(ints_1[0], ints_1[1], ints_1[2]);
   }

   public <T> T serialize(DynamicOps<T> dynamicOps_1) {
      return dynamicOps_1.createIntList(IntStream.of(new int[]{this.getX(), this.getY(), this.getZ()}));
   }

   public static long offset(long long_1, Direction direction_1) {
      return add(long_1, direction_1.getOffsetX(), direction_1.getOffsetY(), direction_1.getOffsetZ());
   }

   public static long add(long long_1, int int_1, int int_2, int int_3) {
      return asLong(unpackLongX(long_1) + int_1, unpackLongY(long_1) + int_2, unpackLongZ(long_1) + int_3);
   }

   public static int unpackLongX(long long_1) {
      return (int)(long_1 << 64 - BIT_SHIFT_X - SIZE_BITS_X >> 64 - SIZE_BITS_X);
   }

   public static int unpackLongY(long long_1) {
      return (int)(long_1 << 64 - SIZE_BITS_Y >> 64 - SIZE_BITS_Y);
   }

   public static int unpackLongZ(long long_1) {
      return (int)(long_1 << 64 - BIT_SHIFT_Z - SIZE_BITS_Z >> 64 - SIZE_BITS_Z);
   }

   public static BlockPos fromLong(long long_1) {
      return new BlockPos(unpackLongX(long_1), unpackLongY(long_1), unpackLongZ(long_1));
   }

   public static long asLong(int int_1, int int_2, int int_3) {
      long long_1 = 0L;
      long_1 |= ((long)int_1 & BITS_X) << BIT_SHIFT_X;
      long_1 |= ((long)int_2 & BITS_Y) << 0;
      long_1 |= ((long)int_3 & BITS_Z) << BIT_SHIFT_Z;
      return long_1;
   }

   public static long removeChunkSectionLocalY(long long_1) {
      return long_1 & -16L;
   }

   public long asLong() {
      return asLong(this.getX(), this.getY(), this.getZ());
   }

   public BlockPos add(double double_1, double double_2, double double_3) {
      return double_1 == 0.0D && double_2 == 0.0D && double_3 == 0.0D ? this : new BlockPos((double)this.getX() + double_1, (double)this.getY() + double_2, (double)this.getZ() + double_3);
   }

   public BlockPos add(int int_1, int int_2, int int_3) {
      return int_1 == 0 && int_2 == 0 && int_3 == 0 ? this : new BlockPos(this.getX() + int_1, this.getY() + int_2, this.getZ() + int_3);
   }

   public BlockPos add(Vec3i vec3i_1) {
      return this.add(vec3i_1.getX(), vec3i_1.getY(), vec3i_1.getZ());
   }

   public BlockPos subtract(Vec3i vec3i_1) {
      return this.add(-vec3i_1.getX(), -vec3i_1.getY(), -vec3i_1.getZ());
   }

   public BlockPos up() {
      return this.up(1);
   }

   public BlockPos up(int int_1) {
      return this.offset(Direction.UP, int_1);
   }

   public BlockPos down() {
      return this.down(1);
   }

   public BlockPos down(int int_1) {
      return this.offset(Direction.DOWN, int_1);
   }

   public BlockPos north() {
      return this.north(1);
   }

   public BlockPos north(int int_1) {
      return this.offset(Direction.NORTH, int_1);
   }

   public BlockPos south() {
      return this.south(1);
   }

   public BlockPos south(int int_1) {
      return this.offset(Direction.SOUTH, int_1);
   }

   public BlockPos west() {
      return this.west(1);
   }

   public BlockPos west(int int_1) {
      return this.offset(Direction.WEST, int_1);
   }

   public BlockPos east() {
      return this.east(1);
   }

   public BlockPos east(int int_1) {
      return this.offset(Direction.EAST, int_1);
   }

   public BlockPos offset(Direction direction_1) {
      return this.offset(direction_1, 1);
   }

   public BlockPos offset(Direction direction_1, int int_1) {
      return int_1 == 0 ? this : new BlockPos(this.getX() + direction_1.getOffsetX() * int_1, this.getY() + direction_1.getOffsetY() * int_1, this.getZ() + direction_1.getOffsetZ() * int_1);
   }

   public BlockPos rotate(BlockRotation blockRotation_1) {
      switch(blockRotation_1) {
      case NONE:
      default:
         return this;
      case CLOCKWISE_90:
         return new BlockPos(-this.getZ(), this.getY(), this.getX());
      case CLOCKWISE_180:
         return new BlockPos(-this.getX(), this.getY(), -this.getZ());
      case COUNTERCLOCKWISE_90:
         return new BlockPos(this.getZ(), this.getY(), -this.getX());
      }
   }

   public BlockPos crossProduct(Vec3i vec3i_1) {
      return new BlockPos(this.getY() * vec3i_1.getZ() - this.getZ() * vec3i_1.getY(), this.getZ() * vec3i_1.getX() - this.getX() * vec3i_1.getZ(), this.getX() * vec3i_1.getY() - this.getY() * vec3i_1.getX());
   }

   public BlockPos toImmutable() {
      return this;
   }

   public static Iterable<BlockPos> iterate(BlockPos blockPos_1, BlockPos blockPos_2) {
      return iterate(Math.min(blockPos_1.getX(), blockPos_2.getX()), Math.min(blockPos_1.getY(), blockPos_2.getY()), Math.min(blockPos_1.getZ(), blockPos_2.getZ()), Math.max(blockPos_1.getX(), blockPos_2.getX()), Math.max(blockPos_1.getY(), blockPos_2.getY()), Math.max(blockPos_1.getZ(), blockPos_2.getZ()));
   }

   public static Stream<BlockPos> stream(BlockPos blockPos_1, BlockPos blockPos_2) {
      return stream(Math.min(blockPos_1.getX(), blockPos_2.getX()), Math.min(blockPos_1.getY(), blockPos_2.getY()), Math.min(blockPos_1.getZ(), blockPos_2.getZ()), Math.max(blockPos_1.getX(), blockPos_2.getX()), Math.max(blockPos_1.getY(), blockPos_2.getY()), Math.max(blockPos_1.getZ(), blockPos_2.getZ()));
   }

   public static Stream<BlockPos> stream(final int int_1, final int int_2, final int int_3, final int int_4, final int int_5, final int int_6) {
      return StreamSupport.stream(new AbstractSpliterator<BlockPos>((long)((int_4 - int_1 + 1) * (int_5 - int_2 + 1) * (int_6 - int_3 + 1)), 64) {
         final CuboidBlockIterator connector = new CuboidBlockIterator(int_1, int_2, int_3, int_4, int_5, int_6);
         final BlockPos.Mutable position = new BlockPos.Mutable();

         public boolean tryAdvance(Consumer<? super BlockPos> consumer_1) {
            if (this.connector.step()) {
               consumer_1.accept(this.position.set(this.connector.getX(), this.connector.getY(), this.connector.getZ()));
               return true;
            } else {
               return false;
            }
         }
      }, false);
   }

   public static Iterable<BlockPos> iterate(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6) {
      return () -> {
         return new AbstractIterator<BlockPos>() {
            final CuboidBlockIterator iterator = new CuboidBlockIterator(int_1, int_2, int_3, int_4, int_5, int_6);
            final BlockPos.Mutable pos = new BlockPos.Mutable();

            protected BlockPos computeNext() {
               return (BlockPos)(this.iterator.step() ? this.pos.set(this.iterator.getX(), this.iterator.getY(), this.iterator.getZ()) : (BlockPos)this.endOfData());
            }
         };
      };
   }

   static {
      SIZE_BITS_Z = SIZE_BITS_X;
      SIZE_BITS_Y = 64 - SIZE_BITS_X - SIZE_BITS_Z;
      BITS_X = (1L << SIZE_BITS_X) - 1L;
      BITS_Y = (1L << SIZE_BITS_Y) - 1L;
      BITS_Z = (1L << SIZE_BITS_Z) - 1L;
      BIT_SHIFT_Z = SIZE_BITS_Y;
      BIT_SHIFT_X = SIZE_BITS_Y + SIZE_BITS_Z;
   }

   public static final class PooledMutable extends BlockPos.Mutable implements AutoCloseable {
      private boolean free;
      private static final List<BlockPos.PooledMutable> POOL = Lists.newArrayList();

      private PooledMutable(int int_1, int int_2, int int_3) {
         super(int_1, int_2, int_3);
      }

      public static BlockPos.PooledMutable get() {
         return get(0, 0, 0);
      }

      public static BlockPos.PooledMutable get(double double_1, double double_2, double double_3) {
         return get(MathHelper.floor(double_1), MathHelper.floor(double_2), MathHelper.floor(double_3));
      }

      public static BlockPos.PooledMutable get(int int_1, int int_2, int int_3) {
         synchronized(POOL) {
            if (!POOL.isEmpty()) {
               BlockPos.PooledMutable blockPos$PooledMutable_1 = (BlockPos.PooledMutable)POOL.remove(POOL.size() - 1);
               if (blockPos$PooledMutable_1 != null && blockPos$PooledMutable_1.free) {
                  blockPos$PooledMutable_1.free = false;
                  blockPos$PooledMutable_1.method_10113(int_1, int_2, int_3);
                  return blockPos$PooledMutable_1;
               }
            }
         }

         return new BlockPos.PooledMutable(int_1, int_2, int_3);
      }

      public BlockPos.PooledMutable method_10113(int int_1, int int_2, int int_3) {
         return (BlockPos.PooledMutable)super.set(int_1, int_2, int_3);
      }

      public BlockPos.PooledMutable method_10112(double double_1, double double_2, double double_3) {
         return (BlockPos.PooledMutable)super.set(double_1, double_2, double_3);
      }

      public BlockPos.PooledMutable method_10114(Vec3i vec3i_1) {
         return (BlockPos.PooledMutable)super.set(vec3i_1);
      }

      public BlockPos.PooledMutable method_10118(Direction direction_1) {
         return (BlockPos.PooledMutable)super.setOffset(direction_1);
      }

      public BlockPos.PooledMutable method_10116(Direction direction_1, int int_1) {
         return (BlockPos.PooledMutable)super.setOffset(direction_1, int_1);
      }

      public BlockPos.PooledMutable method_10108(int int_1, int int_2, int int_3) {
         return (BlockPos.PooledMutable)super.setOffset(int_1, int_2, int_3);
      }

      public void close() {
         synchronized(POOL) {
            if (POOL.size() < 100) {
               POOL.add(this);
            }

            this.free = true;
         }
      }

      // $FF: synthetic method
      public BlockPos.Mutable setOffset(int var1, int var2, int var3) {
         return this.method_10108(var1, var2, var3);
      }

      // $FF: synthetic method
      public BlockPos.Mutable setOffset(Direction var1, int var2) {
         return this.method_10116(var1, var2);
      }

      // $FF: synthetic method
      public BlockPos.Mutable setOffset(Direction var1) {
         return this.method_10118(var1);
      }

      // $FF: synthetic method
      public BlockPos.Mutable set(Vec3i var1) {
         return this.method_10114(var1);
      }

      // $FF: synthetic method
      public BlockPos.Mutable set(double var1, double var3, double var5) {
         return this.method_10112(var1, var3, var5);
      }

      // $FF: synthetic method
      public BlockPos.Mutable set(int var1, int var2, int var3) {
         return this.method_10113(var1, var2, var3);
      }
   }

   public static class Mutable extends BlockPos {
      protected int x;
      protected int y;
      protected int z;

      public Mutable() {
         this(0, 0, 0);
      }

      public Mutable(BlockPos blockPos_1) {
         this(blockPos_1.getX(), blockPos_1.getY(), blockPos_1.getZ());
      }

      public Mutable(int int_1, int int_2, int int_3) {
         super(0, 0, 0);
         this.x = int_1;
         this.y = int_2;
         this.z = int_3;
      }

      public Mutable(double double_1, double double_2, double double_3) {
         this(MathHelper.floor(double_1), MathHelper.floor(double_2), MathHelper.floor(double_3));
      }

      public BlockPos add(double double_1, double double_2, double double_3) {
         return super.add(double_1, double_2, double_3).toImmutable();
      }

      public BlockPos add(int int_1, int int_2, int int_3) {
         return super.add(int_1, int_2, int_3).toImmutable();
      }

      public BlockPos offset(Direction direction_1, int int_1) {
         return super.offset(direction_1, int_1).toImmutable();
      }

      public BlockPos rotate(BlockRotation blockRotation_1) {
         return super.rotate(blockRotation_1).toImmutable();
      }

      public int getX() {
         return this.x;
      }

      public int getY() {
         return this.y;
      }

      public int getZ() {
         return this.z;
      }

      public BlockPos.Mutable set(int int_1, int int_2, int int_3) {
         this.x = int_1;
         this.y = int_2;
         this.z = int_3;
         return this;
      }

      public BlockPos.Mutable set(double double_1, double double_2, double double_3) {
         return this.set(MathHelper.floor(double_1), MathHelper.floor(double_2), MathHelper.floor(double_3));
      }

      public BlockPos.Mutable set(Vec3i vec3i_1) {
         return this.set(vec3i_1.getX(), vec3i_1.getY(), vec3i_1.getZ());
      }

      public BlockPos.Mutable set(long long_1) {
         return this.set(unpackLongX(long_1), unpackLongY(long_1), unpackLongZ(long_1));
      }

      public BlockPos.Mutable set(AxisCycleDirection axisCycleDirection_1, int int_1, int int_2, int int_3) {
         return this.set(axisCycleDirection_1.choose(int_1, int_2, int_3, Direction.Axis.X), axisCycleDirection_1.choose(int_1, int_2, int_3, Direction.Axis.Y), axisCycleDirection_1.choose(int_1, int_2, int_3, Direction.Axis.Z));
      }

      public BlockPos.Mutable setOffset(Direction direction_1) {
         return this.setOffset(direction_1, 1);
      }

      public BlockPos.Mutable setOffset(Direction direction_1, int int_1) {
         return this.set(this.x + direction_1.getOffsetX() * int_1, this.y + direction_1.getOffsetY() * int_1, this.z + direction_1.getOffsetZ() * int_1);
      }

      public BlockPos.Mutable setOffset(int int_1, int int_2, int int_3) {
         return this.set(this.x + int_1, this.y + int_2, this.z + int_3);
      }

      public void setX(int int_1) {
         this.x = int_1;
      }

      public void setY(int int_1) {
         this.y = int_1;
      }

      public void setZ(int int_1) {
         this.z = int_1;
      }

      public BlockPos toImmutable() {
         return new BlockPos(this);
      }
   }
}
