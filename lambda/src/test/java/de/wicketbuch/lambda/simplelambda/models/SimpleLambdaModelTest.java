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

import static de.wicketbuch.lambda.simplelambda.models.SimpleLambdaModel.readonlyModel;
import static de.wicketbuch.lambda.simplelambda.models.SimpleLambdaModel.simpleModel;
import static org.junit.Assert.assertEquals;

import org.apache.wicket.model.IModel;
import org.junit.Test;

/**
 * Created by calle on 12/04/16.
 */
public class SimpleLambdaModelTest
{
	static class Bean
	{
		private String string;

		public String getString()
		{
			return string;
		}

		public void setString(String string)
		{
			this.string = string;
		}
	}

	@Test
	public void readonlyGetsProperty() throws Exception
	{
		final Bean bean = new Bean();
		bean.string = "foo";
		final SimpleLambdaModel<String> underTest = new SimpleLambdaModel<>(bean::getString);
		assertEquals("foo", underTest.getObject());
		bean.string = "bar";
		assertEquals("bar", underTest.getObject());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void readonlyRejectsSetObject() throws Exception
	{
		final Bean bean = new Bean();
		IModel<String> underTest = new SimpleLambdaModel<>(bean::getString);
		underTest.setObject("should blow up");
	}

	@Test
	public void readwriteGetsProperty() throws Exception
	{
		final Bean bean = new Bean();
		bean.string = "foo";
		final IModel<String> underTest = new SimpleLambdaModel<>(bean::getString, bean::setString);
		assertEquals("foo", underTest.getObject());
		bean.string = "bar";
		assertEquals("bar", underTest.getObject());
	}

	@Test
	public void readwriteSetsProperty() throws Exception
	{
		final Bean bean = new Bean();
		bean.string = "foo";
		final IModel<String> underTest = new SimpleLambdaModel<>(bean::getString, bean::setString);
		underTest.setObject("bar");
		assertEquals("bar", bean.getString());
		assertEquals(bean.getString(), underTest.getObject());
	}

	@Test
	public void readonlyHelperGetsProperty() throws Exception
	{
		final Bean bean = new Bean();
		bean.string = "foo";
		final IModel<String> underTest = readonlyModel(bean::getString);
		assertEquals("foo", underTest.getObject());
		bean.string = "bar";
		assertEquals("bar", underTest.getObject());
	}

	@Test
	public void readwriteHelperSetsProperty() throws Exception
	{
		final Bean bean = new Bean();
		bean.string = "foo";
		final IModel<String> underTest = simpleModel(bean::getString, bean::setString);
		underTest.setObject("bar");
		assertEquals("bar", bean.string);
	}
}
