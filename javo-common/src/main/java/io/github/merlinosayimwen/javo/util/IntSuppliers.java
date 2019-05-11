package io.github.merlinosayimwen.javo.util;

import com.google.common.base.Preconditions;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;

public final class IntSuppliers {
  private IntSuppliers() {}

  public static IntSupplier memoize(IntSupplier delegate) {
    Preconditions.checkNotNull(delegate);
    return new MemoizingSupplier(0, delegate, new AtomicBoolean(false));
  }

  private static class MemoizingSupplier implements IntSupplier {
    private int value;
    private IntSupplier delegate;
    private AtomicBoolean state;

    private MemoizingSupplier(int value, IntSupplier delegate, AtomicBoolean state) {
      this.value = value;
      this.delegate = delegate;
      this.state = state;
    }

    @Override
    public int getAsInt() {
      if (state.compareAndSet(true, true)) {
        synchronized (this) {
          this.value = delegate.getAsInt();
          return value;
        }
      }
      synchronized (this) {
        return value;
      }
    }
  }
}
