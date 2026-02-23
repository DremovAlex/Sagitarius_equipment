package oriseus.Sagitarius_equipment.utilities;

import java.util.function.Function;

import javafx.util.StringConverter;

public class Converters {
	
	public static <T> StringConverter<T> simpleConverter(
	        Function<T, String> mapper
	) {
	    return new StringConverter<>() {

	        @Override
	        public String toString(T object) {
	            return object == null ? "" : mapper.apply(object);
	        }

	        @Override
	        public T fromString(String string) {
	            return null;
	        }
	    };
	}
	

    public static <T> StringConverter<T> displayNameConverter(
            Function<T, String> toStringFunc
    ) {
        return new StringConverter<>() {
            @Override
            public String toString(T object) {
                return object == null ? "" : toStringFunc.apply(object);
            }

            @Override
            public T fromString(String string) {
                return null; 
            }
        };
    }
}
