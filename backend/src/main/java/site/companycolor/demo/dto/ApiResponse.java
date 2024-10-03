package site.companycolor.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@NoArgsConstructor
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private T data;
    private String message;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, data, null);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(SUCCESS_STATUS, data, message);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(ERROR_STATUS, null, message);
    }

    public static ApiResponse<?> createSuccessWithNoContent() {
        return new ApiResponse<>(SUCCESS_STATUS, null, null);
    }

}