/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.utils.StringUtils;

/**
 * @since 2.0
 */
public enum ReferenceBy {
	TEXT(100) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return other != LABEL && other != TOOLTIP && other != MNEMONIC;
		}

		public String matcherMethod() {
			return "withText(" + argumentName() + ")";
		}

		public String methodNameSuffix() {
			return "";
		}
	},
	MNEMONIC(100) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return other != LABEL && other != TOOLTIP && other != TEXT;
		}

		public String matcherMethod() {
			return "withMnemonic(" + argumentName() + ")";
		}

		public String methodNameSuffix() {
			return "";
		}

		public String argumentName() {
			return "mnemonicText";
		}
	},
	LABEL(100) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return other != TEXT && other != TOOLTIP && other != MNEMONIC;
		}

		public String matcherMethod() {
			return "withLabel(" + argumentName() + ")";
		}
	},
	TOOLTIP(90) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return other != TEXT && other != LABEL && other != MNEMONIC;
		}

		public String matcherMethod() {
			return "withTooltip(" + argumentName() + ")";
		}
	},
	ID_KEY_VALUE(100) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return false;
		}

		public String methodArgument() {
			return "String key, String value";
		}

		public String matcherMethod() {
			return "withId(key, value)";
		}

		public String paramJavaDoc() {
			return "@param key the key set on the widget.\n" + "@param value the value for the key.\n";
		}

		public String methodNameSuffix() {
			return "WithId";
		}

		public String argumentName() {
			return "key/value";
		}
	},
	ID_VALUE(100) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return false;
		}

		public String methodArgument() {
			return "String value";
		}

		public String matcherMethod() {
			return "withId(value)";
		}

		public String paramJavaDoc() {
			return "@param value the value for the key {@value " + SWTBotPreferences.class.getName() + "#DEFAULT_KEY}.\n";
		}

		public String methodNameSuffix() {
			return "WithId";
		}

		public String argumentName() {
			return "value";
		}
	},
	NONE(100) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return false;
		}

		public String methodArgument() {
			return "";
		}

		public String methodNameSuffix() {
			return "";
		}

		public String matcherMethod() {
			return "";
		}

		public String paramJavaDoc() {
			return "";
		}
	},
	IN_GROUP(80) {
		public String methodNameSuffix() {
			return "InGroup";
		}

		public String matcherMethod() {
			return "inGroup(inGroup)";
		}
	};
	private final int	priority;

	private ReferenceBy(int priority) {
		this.priority = priority;
	}

	protected boolean isCompatibleWith(ReferenceBy other) {
		return true;
	}

	protected boolean canExistByItself() {
		return true;
	}

	public String describeJavaDoc() {
		return "with the specified <code>" + argumentName() + "</code>";
	}

	public String methodArgument() {
		return "String " + argumentName();
	}

	public String argumentName() {
		return StringUtils.toCamelCase(name().toLowerCase());
	}

	public String methodNameSuffix() {
		return "With" + StringUtils.capitalize(name().toLowerCase());
	}

	public abstract String matcherMethod();

	protected static Comparator<ReferenceBy> comparator() {
		return new Comparator<ReferenceBy>() {
			public int compare(ReferenceBy o1, ReferenceBy o2) {
				return o2.priority - o1.priority;
			}

		};
	}

	/**
	 * @param references the references
	 * @return all possible combinations of the specified combinations
	 */
	public static List<List<ReferenceBy>> getCombinations(ReferenceBy... references) {
		return sort(filter(new CombinationGenerator<ReferenceBy>(references.length, references)));
	}

	private static List<List<ReferenceBy>> sort(List<List<ReferenceBy>> combinations) {
		for (List<ReferenceBy> list : combinations) {
			Collections.sort(list, ReferenceBy.comparator());
		}
		return combinations;
	}

	private static List<List<ReferenceBy>> filter(CombinationGenerator<ReferenceBy> uptoCombinationGenerator) {
		ArrayList<List<ReferenceBy>> result = new ArrayList<List<ReferenceBy>>();
		for (List<ReferenceBy> list : uptoCombinationGenerator) {
			if (list.size() == 1) {
				if (list.get(0).canExistByItself())
					result.add(list);
			} else if (isCompatible(list))
				result.add(list);
		}
		return result;
	}

	private static boolean isCompatible(List<ReferenceBy> list) {
		for (ReferenceBy aReference : list) {
			for (ReferenceBy otherReference : list) {
				if (aReference != otherReference)
					if (!aReference.isCompatibleWith(otherReference))
						return false;
			}
		}
		return true;
	}

	public String javadoc() {
		return "the " + argumentName() + " on the widget.";
	}

	public String paramJavaDoc() {
		return "@param " + argumentName() + " " + javadoc() + "\n";
	}

}
