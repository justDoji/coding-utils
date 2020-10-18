package be.sddevelopment.utils.testing;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

/**
 * <p>Description of file/class</p>
 *
 * @author : <a href="https://github.com/justDoji" target="_blank">Stijn Dejongh</a>
 * @created : 18.10.20, Sunday
 **/
public class ReplaceUnderscoredCamelCasing extends ReplaceUnderscores {

  public ReplaceUnderscoredCamelCasing() {
    super();
  }

  @Override
  public String generateDisplayNameForClass(Class<?> testClass) {
    return this.replaceCapitals(super.generateDisplayNameForClass(testClass));
  }

  @Override
  public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
    return this.replaceCapitals(super.generateDisplayNameForNestedClass(nestedClass));
  }

  @Override
  public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
    return this.replaceCapitals(super.generateDisplayNameForMethod(testClass, testMethod));
  }

  private String replaceCapitals(String name) {
    name = name.replaceAll("([A-Z])", " $1").toLowerCase();
    name = name.replaceAll("([0-9]+)", " $1");
    name = name.replace("given", "GIVEN");
    name = name.replace("when", "WHEN");
    name = name.replace("then", "THEN");
    name = name.replace("and", "AND");
    return name;
  }

}
