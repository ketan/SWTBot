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
			return other != LABEL && other != TOOLTIP && other != MNEMONIC && other != MESSAGE;
		}

		public String matcherMethod() {
			return "withText(" + argumentName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		}

		public String methodNameSuffix() {
			return ""; //$NON-NLS-1$
		}
	},
	MNEMONIC(100) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return other != LABEL && other != TOOLTIP && other != TEXT && other != MESSAGE;
		}

		public String matcherMethod() {
			return "withMnemonic(" + argumentName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		}

		public String methodNameSuffix() {
			return ""; //$NON-NLS-1$
		}

		public String argumentName() {
			return "mnemonicText"; //$NON-NLS-1$
		}
	},
	LABEL(100) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return other != TEXT && other != TOOLTIP && other != MNEMONIC && other != MESSAGE;
		}

		public String matcherMethod() {
			return "withLabel(" + argumentName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	},
	TOOLTIP(90) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return other != TEXT && other != LABEL && other != MNEMONIC  && other != MESSAGE;
		}

		public String matcherMethod() {
			return "withTooltip(" + argumentName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	},
	MESSAGE(90) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return other != TEXT && other != LABEL && other != MNEMONIC && other != TOOLTIP && other != IN_GROUP;
		}

		public String matcherMethod() {
			return "withMessage(" + argumentName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	},
	ID_KEY_VALUE(100) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return false;
		}

		public String methodArgument() {
			return "String key, String value"; //$NON-NLS-1$
		}

		public String matcherMethod() {
			return "withId(key, value)"; //$NON-NLS-1$
		}

		public String paramJavaDoc() {
			return "@param key the key set on the widget.\n" + "@param value the value for the key.\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}

		public String methodNameSuffix() {
			return "WithId"; //$NON-NLS-1$
		}

		public String argumentName() {
			return "key/value"; //$NON-NLS-1$
		}
	},
	ID_VALUE(100) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return false;
		}

		public String methodArgument() {
			return "String value"; //$NON-NLS-1$
		}

		public String matcherMethod() {
			return "withId(value)"; //$NON-NLS-1$
		}

		public String paramJavaDoc() {
			return "@param value the value for the key {@link " + SWTBotPreferences.class.getName() + "#DEFAULT_KEY}.\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}

		public String methodNameSuffix() {
			return "WithId"; //$NON-NLS-1$
		}

		public String argumentName() {
			return "value"; //$NON-NLS-1$
		}
	},
	NONE(100) {
		protected boolean isCompatibleWith(ReferenceBy other) {
			return false;
		}

		public String methodArgument() {
			return ""; //$NON-NLS-1$
		}

		public String methodNameSuffix() {
			return ""; //$NON-NLS-1$
		}

		public String matcherMethod() {
			return ""; //$NON-NLS-1$
		}

		public String paramJavaDoc() {
			return ""; //$NON-NLS-1$
		}
	},
	IN_GROUP(80) {
		public String methodNameSuffix() {
			return "InGroup"; //$NON-NLS-1$
		}

		public String matcherMethod() {
			return "inGroup(inGroup)"; //$NON-NLS-1$
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
		return "with the specified <code>" + argumentName() + "</code>"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public String methodArgument() {
		return "String " + argumentName(); //$NON-NLS-1$
	}

	public String argumentName() {
		return StringUtils.toCamelCase(name().toLowerCase());
	}

	public String methodNameSuffix() {
		return "With" + StringUtils.capitalize(name().toLowerCase()); //$NON-NLS-1$
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
		return "the " + argumentName() + " on the widget."; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public String paramJavaDoc() {
		return "@param " + argumentName() + " " + javadoc() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

}
