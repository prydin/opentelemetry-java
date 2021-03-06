/*
 * Copyright 2019, OpenTelemetry Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opentelemetry.opentracingshim;

import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.correlationcontext.CorrelationContextManager;
import io.opentelemetry.internal.Utils;
import io.opentelemetry.trace.Tracer;
import io.opentelemetry.trace.TracerProvider;

public final class TraceShim {
  private TraceShim() {}

  /**
   * Creates a {@code io.opentracing.Tracer} shim out of {@code OpenTelemetry.getTracer()} and
   * {@code OpenTelemetry.getCorrelationContextManager()}.
   *
   * @return a {@code io.opentracing.Tracer}.
   * @since 0.1.0
   */
  public static io.opentracing.Tracer createTracerShim() {
    return new TracerShim(
        new TelemetryInfo(
            getTracer(OpenTelemetry.getTracerRegistry()),
            OpenTelemetry.getCorrelationContextManager()));
  }

  /**
   * Creates a {@code io.opentracing.Tracer} shim out the specified {@code Tracer} and {@code
   * CorrelationContextManager}.
   *
   * @param tracerProvider the {@code TracerProvider} used by this shim.
   * @param contextManager the {@code CorrelationContextManager} used by this shim.
   * @return a {@code io.opentracing.Tracer}.
   * @since 0.1.0
   */
  public static io.opentracing.Tracer createTracerShim(
      TracerProvider tracerProvider, CorrelationContextManager contextManager) {
    Utils.checkNotNull(tracerProvider, "tracerProvider");
    Utils.checkNotNull(contextManager, "contextManager");
    return new TracerShim(new TelemetryInfo(getTracer(tracerProvider), contextManager));
  }

  private static Tracer getTracer(TracerProvider tracerProvider) {
    return tracerProvider.get("opentracingshim");
  }
}
