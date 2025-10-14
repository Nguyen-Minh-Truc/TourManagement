package  com.J2EE.TourManagement.Util.error;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.J2EE.TourManagement.Model.RestResponse;

@RestControllerAdvice
public class GlobalException {

  @ExceptionHandler(value =
                        {
                            UsernameNotFoundException.class,
                            BadCredentialsException.class,
                        })
  public ResponseEntity<RestResponse<Object>>
  handleBlogAlreadyExistsException(Exception exception) {
    RestResponse<Object> res = new RestResponse<Object>();
    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
    res.setError(exception.getMessage());
    res.setMessage("thông tin đăng nhập không hợp lệ.");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RestResponse<Object>>
  validationError(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    final List<FieldError> fieldErrors = result.getFieldErrors();

    RestResponse<Object> res = new RestResponse<>();
    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
    res.setError(ex.getBody().getDetail());

    List<String> errors =
        fieldErrors.stream().map(FieldError::getDefaultMessage).toList();

    // Nếu có nhiều lỗi thì trả nguyên list, nếu 1 lỗi thì chỉ lấy lỗi đầu tiên
    res.setMessage(errors.size() > 1 ? errors : errors.get(0));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }

 

  @ExceptionHandler(InvalidException.class)
  public ResponseEntity<RestResponse<Object>>
  handleIdInvalidException(InvalidException ex) {
    RestResponse<Object> res = new RestResponse<>();
    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
    res.setError("Conflict");
    res.setMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }
}
