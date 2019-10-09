package com.mojang.blaze3d;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import it.unimi.dsi.fastutil.Hash.Strategy;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.abnormals.tut_new.SharedConstants;
import team.abnormals.tut_new.engine.Identifier;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.time.Instant;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SystemUtil {
   private static final AtomicInteger field_18034 = new AtomicInteger(1);
   private static final ExecutorService SERVER_WORKER_EXECUTOR = createServerWorkerExecutor();
   public static LongSupplier nanoTimeSupplier = System::nanoTime;
   private static final Logger LOGGER = LogManager.getLogger();

   public static <K, V> Collector<Entry<? extends K, ? extends V>, ?, Map<K, V>> toMap() {
      return Collectors.toMap(Entry::getKey, Entry::getValue);
   }

   public static String createTranslationKey(String string_1, @Nullable Identifier identifier_1) {
      return identifier_1 == null ? string_1 + ".unregistered_sadface" : string_1 + '.' + identifier_1.getNamespace() + '.' + identifier_1.getPath().replace('/', '.');
   }

   public static long getMeasuringTimeMs() {
      return getMeasuringTimeNano() / 1000000L;
   }

   public static long getMeasuringTimeNano() {
      return nanoTimeSupplier.getAsLong();
   }

   public static long getEpochTimeMs() {
      return Instant.now().toEpochMilli();
   }

   private static ExecutorService createServerWorkerExecutor() {
      int int_1 = MathHelper.clamp(Runtime.getRuntime().availableProcessors() - 1, 1, 7);
      Object executorService_2;
      if (int_1 <= 0) {
         executorService_2 = MoreExecutors.newDirectExecutorService();
      } else {
         executorService_2 = new ForkJoinPool(int_1, (forkJoinPool_1) -> {
            ForkJoinWorkerThread forkJoinWorkerThread_1 = new ForkJoinWorkerThread(forkJoinPool_1) {
            };
            forkJoinWorkerThread_1.setName("Server-Worker-" + field_18034.getAndIncrement());
            return forkJoinWorkerThread_1;
         }, (thread_1, throwable_1) -> {
            method_22320(throwable_1);
            if (throwable_1 instanceof CompletionException) {
               throwable_1 = throwable_1.getCause();
            }

            /*if (throwable_1 instanceof CrashException) {
               Bootstrap.println(((CrashException)throwable_1).getReport().asString());
               System.exit(-1);
            }*/

            LOGGER.error(String.format("Caught exception in thread %s", thread_1), throwable_1);
         }, true);
      }

      return (ExecutorService)executorService_2;
   }

   public static Executor getServerWorkerExecutor() {
      return SERVER_WORKER_EXECUTOR;
   }

   public static void shutdownServerWorkerExecutor() {
      SERVER_WORKER_EXECUTOR.shutdown();

      boolean boolean_2;
      try {
         boolean_2 = SERVER_WORKER_EXECUTOR.awaitTermination(3L, TimeUnit.SECONDS);
      } catch (InterruptedException var2) {
         boolean_2 = false;
      }

      if (!boolean_2) {
         SERVER_WORKER_EXECUTOR.shutdownNow();
      }

   }

   @Environment(EnvType.CLIENT)
   public static <T> CompletableFuture<T> completeExceptionally(Throwable throwable_1) {
      CompletableFuture<T> completableFuture_1 = new CompletableFuture();
      completableFuture_1.completeExceptionally(throwable_1);
      return completableFuture_1;
   }

   public static SystemUtil.OperatingSystem getOperatingSystem() {
      String string_1 = System.getProperty("os.name").toLowerCase(Locale.ROOT);
      if (string_1.contains("win")) {
         return SystemUtil.OperatingSystem.WINDOWS;
      } else if (string_1.contains("mac")) {
         return SystemUtil.OperatingSystem.OSX;
      } else if (string_1.contains("solaris")) {
         return SystemUtil.OperatingSystem.SOLARIS;
      } else if (string_1.contains("sunos")) {
         return SystemUtil.OperatingSystem.SOLARIS;
      } else if (string_1.contains("linux")) {
         return SystemUtil.OperatingSystem.LINUX;
      } else {
         return string_1.contains("unix") ? SystemUtil.OperatingSystem.LINUX : SystemUtil.OperatingSystem.UNKNOWN;
      }
   }

   public static Stream<String> getJVMFlags() {
      RuntimeMXBean runtimeMXBean_1 = ManagementFactory.getRuntimeMXBean();
      return runtimeMXBean_1.getInputArguments().stream().filter((string_1) -> {
         return string_1.startsWith("-X");
      });
   }

   public static <T> T method_20793(List<T> list_1) {
      return list_1.get(list_1.size() - 1);
   }

   public static <T> T next(Iterable<T> iterable_1, @Nullable T object_1) {
      Iterator<T> iterator_1 = iterable_1.iterator();
      T object_2 = iterator_1.next();
      if (object_1 != null) {
         Object object_3 = object_2;

         while(object_3 != object_1) {
            if (iterator_1.hasNext()) {
               object_3 = iterator_1.next();
            }
         }

         if (iterator_1.hasNext()) {
            return iterator_1.next();
         }
      }

      return object_2;
   }

   public static <T> T previous(Iterable<T> iterable_1, @Nullable T object_1) {
      Iterator<T> iterator_1 = iterable_1.iterator();

      Object object_2;
      Object object_3;
      for(object_2 = null; iterator_1.hasNext(); object_2 = object_3) {
         object_3 = iterator_1.next();
         if (object_3 == object_1) {
            if (object_2 == null) {
               object_2 = iterator_1.hasNext() ? Iterators.getLast(iterator_1) : object_1;
            }
            break;
         }
      }

      return (T) object_2;
   }

   public static <T> T get(Supplier<T> supplier_1) {
      return supplier_1.get();
   }

   public static <T> T consume(T object_1, Consumer<T> consumer_1) {
      consumer_1.accept(object_1);
      return object_1;
   }

   public static <K> Strategy<K> identityHashStrategy() {
      return (Strategy<K>) IdentityHashStrategy.INSTANCE;
   }

   public static <V> CompletableFuture<List<V>> thenCombine(List<? extends CompletableFuture<? extends V>> list_1) {
      List<V> list_2 = Lists.newArrayListWithCapacity(list_1.size());
      CompletableFuture<?>[] completableFutures_1 = new CompletableFuture[list_1.size()];
      CompletableFuture<Void> completableFuture_1 = new CompletableFuture();
      list_1.forEach((completableFuture_2) -> {
         int int_1 = list_2.size();
         list_2.add(null);
         completableFutures_1[int_1] = completableFuture_2.whenComplete((object_1, throwable_1) -> {
            if (throwable_1 != null) {
               completableFuture_1.completeExceptionally(throwable_1);
            } else {
               list_2.set(int_1, object_1);
            }

         });
      });
      return CompletableFuture.allOf(completableFutures_1).applyToEither(completableFuture_1, (void_1) -> list_2);
   }

   public static <T> Stream<T> stream(Optional<? extends T> optional_1) {
      return (Stream)DataFixUtils.orElseGet(optional_1.map(Stream::of), Stream::empty);
   }

   public static <T> Optional<T> ifPresentOrElse(Optional<T> optional_1, Consumer<T> consumer_1, Runnable runnable_1) {
      if (optional_1.isPresent()) {
         consumer_1.accept(optional_1.get());
      } else {
         runnable_1.run();
      }

      return optional_1;
   }

   public static Runnable debugRunnable(Runnable runnable_1, Supplier<String> supplier_1) {
      return runnable_1;
   }

   public static Optional<UUID> readUuid(String string_1, Dynamic<?> dynamic_1) {
      return dynamic_1.get(string_1 + "Most").asNumber().flatMap((number_1) -> {
         return dynamic_1.get(string_1 + "Least").asNumber().map((number_2) -> {
            return new UUID(number_1.longValue(), number_2.longValue());
         });
      });
   }

   public static <T> Dynamic<T> writeUuid(String string_1, UUID uUID_1, Dynamic<T> dynamic_1) {
      return dynamic_1.set(string_1 + "Most", dynamic_1.createLong(uUID_1.getMostSignificantBits())).set(string_1 + "Least", dynamic_1.createLong(uUID_1.getLeastSignificantBits()));
   }

   public static <T extends Throwable> T method_22320(T throwable_1) {
      if (SharedConstants.isDevelopment) {
         LOGGER.error("Trying to throw a fatal exception, pausing in IDE", throwable_1);

         while(true) {
            try {
               Thread.sleep(1000L);
               LOGGER.error("paused");
            } catch (InterruptedException var2) {
               return throwable_1;
            }
         }
      } else {
         return throwable_1;
      }
   }

   public static String method_22321(Throwable throwable_1) {
      if (throwable_1.getCause() != null) {
         return method_22321(throwable_1.getCause());
      } else {
         return throwable_1.getMessage() != null ? throwable_1.getMessage() : throwable_1.toString();
      }
   }

   static enum IdentityHashStrategy implements Strategy<Object> {
      INSTANCE;

      public int hashCode(Object object_1) {
         return System.identityHashCode(object_1);
      }

      public boolean equals(Object object_1, Object object_2) {
         return object_1 == object_2;
      }
   }

   public static enum OperatingSystem {
      LINUX,
      SOLARIS,
      WINDOWS {
         @Environment(EnvType.CLIENT)
         protected String[] getURLOpenCommand(URL uRL_1) {
            return new String[]{"rundll32", "url.dll,FileProtocolHandler", uRL_1.toString()};
         }
      },
      OSX {
         @Environment(EnvType.CLIENT)
         protected String[] getURLOpenCommand(URL uRL_1) {
            return new String[]{"open", uRL_1.toString()};
         }
      },
      UNKNOWN;

      private OperatingSystem() {
      }

      @Environment(EnvType.CLIENT)
      public void open(URL uRL_1) {
         try {
            Process process_1 = AccessController.doPrivileged((PrivilegedAction<Process>) () -> {
               try {
                  return Runtime.getRuntime().exec(this.getURLOpenCommand(uRL_1));
               } catch (IOException e) {
                  e.printStackTrace();
               }
               return null;
            });

            for (String string_1 : IOUtils.readLines(process_1.getErrorStream())) {
               SystemUtil.LOGGER.error(string_1);
            }

            process_1.getInputStream().close();
            process_1.getErrorStream().close();
            process_1.getOutputStream().close();
         } catch (IOException var5) {
            SystemUtil.LOGGER.error("Couldn't open url '{}'", uRL_1, var5);
         }

      }

      @Environment(EnvType.CLIENT)
      public void open(URI uRI_1) {
         try {
            this.open(uRI_1.toURL());
         } catch (MalformedURLException var3) {
            SystemUtil.LOGGER.error("Couldn't open uri '{}'", uRI_1, var3);
         }

      }

      @Environment(EnvType.CLIENT)
      public void open(File file_1) {
         try {
            this.open(file_1.toURI().toURL());
         } catch (MalformedURLException var3) {
            SystemUtil.LOGGER.error("Couldn't open file '{}'", file_1, var3);
         }

      }

      @Environment(EnvType.CLIENT)
      protected String[] getURLOpenCommand(URL uRL_1) {
         String string_1 = uRL_1.toString();
         if ("file".equals(uRL_1.getProtocol())) {
            string_1 = string_1.replace("file:", "file://");
         }

         return new String[]{"xdg-open", string_1};
      }

      @Environment(EnvType.CLIENT)
      public void open(String string_1) {
         try {
            this.open((new URI(string_1)).toURL());
         } catch (MalformedURLException | IllegalArgumentException | URISyntaxException var3) {
            SystemUtil.LOGGER.error("Couldn't open uri '{}'", string_1, var3);
         }

      }
   }
}
