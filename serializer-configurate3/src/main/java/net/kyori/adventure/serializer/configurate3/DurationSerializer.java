/*
 * This file is part of adventure, licensed under the MIT License.
 *
 * Copyright (c) 2017-2021 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.adventure.serializer.configurate3;

import com.google.common.reflect.TypeToken;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.ScalarSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * A serializer for the JDK {@link Duration} type.
 *
 * <p>This will eventually be incorporated into upstream Configurate</p>
 */
@SuppressWarnings("UnstableApiUsage") // TypeToken
final class DurationSerializer extends ScalarSerializer<Duration> {
  static final DurationSerializer INSTANCE = new DurationSerializer();

  private DurationSerializer() {
    super(Duration.class);
  }

  @Override
  public Duration deserialize(final @NotNull TypeToken<?> type, final @NotNull Object obj) throws ObjectMappingException {
    if (obj instanceof CharSequence) {
      String value = obj.toString();
      if (!value.startsWith("P") && !value.startsWith("p")) {
        value = "P" + value;
      }

      try {
        return Duration.parse(value);
      } catch (final DateTimeParseException ex) {
        throw new ObjectMappingException(ex);
      }
    }
    throw new ObjectMappingException("Value was not of appropriate type");
  }

  @Override
  public Object serialize(final @NotNull Duration item, final @NotNull Predicate<Class<?>> typeSupported) {
    return item.toString();
  }
}
