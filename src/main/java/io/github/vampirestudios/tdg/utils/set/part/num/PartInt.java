/*
 * This file ("PartInt.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame/>.
 * View information on the project at <https://rockbottom.ellpeck.de/>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 *
 * © 2018 Ellpeck
 */

package io.github.vampirestudios.tdg.utils.set.part.num;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.github.vampirestudios.tdg.utils.set.part.BasicDataPart;
import io.github.vampirestudios.tdg.utils.set.part.IPartFactory;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Locale;

public final class PartInt extends BasicDataPart<Integer> {

    public static final IPartFactory<PartInt> FACTORY = new IPartFactory<PartInt>() {
        @Override
        public PartInt parse(JsonElement element) {
            if (element.isJsonPrimitive()) {
                JsonPrimitive prim = element.getAsJsonPrimitive();
                if (prim.isString()) {
                    String string = prim.getAsString().toLowerCase(Locale.ROOT);
                    if (string.endsWith("i")) {
                        try {
                            return new PartInt(Integer.parseInt(string.substring(0, string.length() - 1)));
                        } catch (Exception ignored) {
                        }
                    }
                } else if (prim.isNumber()) {
                    int i = prim.getAsInt();
                    if (prim.getAsDouble() == i) {
                        return new PartInt(i);
                    }
                }
            }
            return null;
        }

        @Override
        public PartInt parse(DataInput stream) throws Exception {
            return new PartInt(stream.readInt());
        }
    };

    public PartInt(Integer data) {
        super(data);
    }

    @Override
    public void write(DataOutput stream) throws IOException {
        stream.writeInt(this.data);
    }

    @Override
    public JsonElement write() {
        return new JsonPrimitive(this.data + "i");
    }

    @Override
    public IPartFactory getFactory() {
        return FACTORY;
    }
}
