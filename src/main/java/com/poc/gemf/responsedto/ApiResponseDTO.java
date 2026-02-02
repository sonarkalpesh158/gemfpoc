package com.poc.gemf.responsedto;


import com.poc.gemf.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO <T>{
        private String status;
        private String message;
        private T data;

        public ApiResponseDTO(Constants statusEnum, String message, T data) {
            this.status = statusEnum.getValue(); // Extracts string automatically
            this.message = message;
            this.data = data;
    }
}
