package team.abnormals.tut_new;

import com.mojang.datafixers.types.DynamicOps;

public interface DynamicSerializable {
   <T> T serialize(DynamicOps<T> var1);
}
