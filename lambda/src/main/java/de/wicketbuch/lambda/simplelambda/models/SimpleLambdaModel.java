/**
 * Copyright (C) 2016 Carl-Eric Menzel <cmenzel@wicketbuch.de>
 * and possibly other SimpleLambda contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.wicketbuch.lambda.simplelambda.models;

import java.util.function.Supplier;

import de.wicketbuch.lambda.simplelambda.functions.SerializableConsumer;
import de.wicketbuch.lambda.simplelambda.functions.SerializableSupplier;
import org.apache.wicket.model.IModel;


public class SimpleLambdaModel<T> implements IModel<T>
{
	public static <U> IModel<U> readonlyModel(SerializableSupplier<U> getter)
	{
		return new SimpleLambdaModel<U>(getter);
	}

	public static <U> IModel<U> simpleModel(SerializableSupplier<U> getter, SerializableConsumer<U> setter)
	{
		return new SimpleLambdaModel<U>(getter, setter);
	}

	private final Supplier<T> getter;
	private SerializableConsumer<T> setter;

	public SimpleLambdaModel(SerializableSupplier<T> getter)
	{
		this(getter, (val) -> {
			throw new UnsupportedOperationException("no setter defined");
		});
	}

	public SimpleLambdaModel(SerializableSupplier<T> getter, SerializableConsumer<T> setter)
	{
		this.getter = getter;
		this.setter = setter;
	}

	@Override
	public T getObject()
	{
		return getter.get();
	}

	@Override
	public void setObject(T value)
	{
		setter.accept(value);
	}

	@Override
	public void detach()
	{
		// Nothing to do
	}
}
