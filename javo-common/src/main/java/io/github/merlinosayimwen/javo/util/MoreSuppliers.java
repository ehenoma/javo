package io.github.merlinosayimwen.javo.util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public final class MoreSuppliers {
  private MoreSuppliers() {}

  public static <E> Supplier<E> memoize(Supplier<E> delegate) {
    return new MemoizingSupplier<>(null, delegate, new AtomicBoolean(false));
  }
  private static final class MemoizingSupplier<E> implements Supplier<E> {
    private E value;
    private Supplier<E> delegate;
    private AtomicBoolean state;

    private MemoizingSupplier(E value, Supplier<E> delegate, AtomicBoolean state) {
      this.value = value;
      this.delegate = delegate;
      this.state = state;
    }

    @Override
    public E get() {
      if (state.compareAndSet(true, true)) {
        synchronized (this) {
          this.value = delegate.get();
          return value;
        }
      }
      synchronized (this) {
        return value;
      }
    }
  }
}
