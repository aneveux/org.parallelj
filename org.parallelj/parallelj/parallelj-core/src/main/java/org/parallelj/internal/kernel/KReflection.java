/*
 *     ParallelJ, framework for parallel computing
 *
 *     Copyright (C) 2010, 2011, 2012 Atos Worldline or third-party contributors as
 *     indicated by the @author tags or express copyright attribution
 *     statements applied by the authors.
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.parallelj.internal.kernel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.parallelj.internal.MessageKind;
import org.parallelj.mirror.Event;
import org.parallelj.mirror.EventListener;
import org.parallelj.mirror.ExecutorServiceKind;
import org.parallelj.mirror.Processor;
import org.parallelj.mirror.ProgramType;
import org.parallelj.mirror.Reflection;

/**
 * {@link KReflection} is the default implementation of {@link Reflection}.
 * 
 * @author Laurent Legrand
 * @since 0.5.0
 */
public class KReflection implements Reflection {

	/**
	 * http://en.wikipedia.org/wiki/Initialization_on_demand_holder_idiom
	 * 
	 * @author Laurent Legrand
	 * 
	 */
	private static class Holder {

		private static final KReflection INSTANCE = new KReflection();
	}

	/**
	 * Set of programs
	 */
	Set<KProgram> programs = Collections
			.synchronizedSet(new HashSet<KProgram>());

	/**
	 * Set of event listeners
	 */
	Set<EventListener> listeners = Collections
			.synchronizedSet(new HashSet<EventListener>());

	/**
	 * The executor that dispatch the event
	 */
	ExecutorService service = Executors.newSingleThreadExecutor();

	BlockingQueue<Event<?>> queue = new ArrayBlockingQueue<Event<?>>(100);

	/**
	 * Return the singleton instance.
	 * 
	 * @return the singleton instance.
	 */
	public static final KReflection getInstance() {
		return Holder.INSTANCE;
	}

	private KReflection() {

		// load built-in listener from META-INF
		for (EventListener listener : ServiceLoader.load(EventListener.class)) {
			this.listeners.add(listener);
		}

		this.service.submit(new Callable<Void>() {

			/**
			 * @throws InterruptedException
			 *             when calling take
			 */
			@Override
			public Void call() throws Exception {
				// TODO avoid while true if possible
				while (true) {
					System.out.println(KReflection.this.queue);
					Event<?> event = KReflection.this.queue.take();
					synchronized (KReflection.this.listeners) {
						for (EventListener listener : KReflection.this.listeners) {
							try {
								listener.handleEvent(event);
							} catch (Exception e) {
								// ignore
								// TODO add message kind
							}
						}
					}
				}
			}
		});
	}

	@Override
	public List<ProgramType> getPrograms() {
		return new ArrayList<ProgramType>(this.programs);
	}

	void register(KProgram program) {
		if (program != null) {
			this.programs.add(program);
		}
	}

	@Override
	public Processor newProcessor(ExecutorServiceKind executorKind,
			Object... args) {
		switch (executorKind) {
		case NONE:
			return new KProcessor();
		case SINGLE_THREAD_EXECUTOR:
			return new KProcessor(Executors.newSingleThreadExecutor());
		case CACHED_THREAD_POOL:
			return new KProcessor(Executors.newCachedThreadPool());
		case FIXED_THREAD_POOL:
			if ((args.length == 0) || !(args[0] instanceof Integer)) {
				throw new IllegalArgumentException(MessageKind.E0001.format(
						executorKind, (args.length == 0) ? "null" : args[0]));
			}
			return new KProcessor(
					Executors.newFixedThreadPool((Integer) args[0]));
		case PROVIDED:
			if ((args.length == 0) || !(args[0] instanceof ExecutorService)) {
				throw new IllegalArgumentException(MessageKind.E0001.format(
						executorKind, (args.length == 0) ? "null" : args[0]));
			}
			return new KProcessor((ExecutorService) args[0]);
		}
		throw new IllegalArgumentException(MessageKind.E0001.format(
				executorKind, ""));

	}

	/**
	 * Dispatch an event to all listeners.
	 * 
	 * @param event the event to dispatch
	 */
	public void dispatch(Event<?> event) {
		try {
			this.queue.put(event);
		} catch (InterruptedException e) {
			// TODO add message kind
		}
	}

	@Override
	public void addEventListener(EventListener listener) {
		if (listener != null) {
			this.listeners.add(listener);
		}
	}

	@Override
	public void removeEventListener(EventListener listener) {
		if (listener != null) {
			this.listeners.remove(listener);
		}
	}

}
