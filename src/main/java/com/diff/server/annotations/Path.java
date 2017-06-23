package com.diff.server.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Path {

	public static enum Method {
		GET {
			@Override
			public String toString() {
				return "GET";
			}
		},
		POST {
			@Override
			public String toString() {
				return "POST";
			}
		},
		PUT {
			@Override
			public String toString() {
				return "PUT";
			}
		},
		DELETE {
			@Override
			public String toString() {
				return "DELETE";
			}
		}

	}

	public String name();

	public Method method() default Method.GET;
}
