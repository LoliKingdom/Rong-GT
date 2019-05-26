package com.rong.rt.api.recipes.input;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import net.minecraftforge.items.IItemHandler;

public class IterableRecipeInputByIItemHandler implements Iterable<IRecipeInput> {

	private final IItemHandler handlerWrapped;

	public IterableRecipeInputByIItemHandler(IItemHandler handler) {
		this.handlerWrapped = handler;
	}

	@Nonnull
	@Override
	public Iterator<IRecipeInput> iterator() {
		return new Iterator<IRecipeInput>() {

			private final int count = handlerWrapped.getSlots();
			private int ptr = 0;

			@Override
			public boolean hasNext() {
				return ptr < count;
			}

			@Override
			public IRecipeInput next() {
				if(ptr < count) {
					return new RecipeInputItemStack(handlerWrapped.getStackInSlot(ptr++));
				}
				else {
					throw new NoSuchElementException();
				}
			}
		};
	}

	@Override
	public void forEach(Consumer<? super IRecipeInput> action) {
		for(IRecipeInput iFrogRecipeInput : this) {
			action.accept(iFrogRecipeInput);
		}
	}

	@Override
	public Spliterator<IRecipeInput> spliterator() {
		return new Spliterator<IRecipeInput>() {

			private int ptr = 0;

			@Override
			public boolean tryAdvance(Consumer<? super IRecipeInput> action) {
				if(action == null) throw new NullPointerException("Consumer cannot be null");
				if(ptr < handlerWrapped.getSlots()) {
					action.accept(new RecipeInputItemStack(handlerWrapped.getStackInSlot(ptr++)));
					return true;
				}
				else {
					return false;
				}
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @implSpec This implementation will always return null due to the fact that
			 *           instance of ItemHandler cannot be partitioned by all means.
			 * @return Always returns null
			 */
			@Override
			public Spliterator<IRecipeInput> trySplit() {
				return null;
			}

			@Override
			public long estimateSize() {
				return handlerWrapped.getSlots() - ptr;
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @implSpec This implementation, by default, has characteristics of
			 *           {@link #ORDERED}, {@link #SIZED}, and {@link #NONNULL}.
			 * @return
			 */
			@Override
			public int characteristics() {
				return ORDERED & SIZED & NONNULL;
			}
		};
	}
}