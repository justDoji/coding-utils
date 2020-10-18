package be.sddevelopment.utils.validation;

import be.sddevelopment.utils.validation.Failure.FailureBuilder;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>Description of file/class</p>
 *
 * @author : <a href="https://github.com/justDoji" target="_blank">Stijn Dejongh</a>
 * @created : 18.10.20, Sunday
 **/
@Data
@AllArgsConstructor
public class ValidationRule<T> {

  private Function<T, Boolean> assertion;
  private Function<T, Function<FailureBuilder, FailureBuilder>> failureCreator;


}
