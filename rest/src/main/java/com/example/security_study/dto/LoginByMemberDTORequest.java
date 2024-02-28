package com.example.security_study.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginByMemberDTORequest {

    @Length(max = 20 , min = 6)
    @NotNull
    public String userId;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[A-Za-z\\d]{9,20}$|^(?=.*\\d)(?=.*[a-z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[a-z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*\\d)(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Za-z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Za-z!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$"
            , message = "특수문자, 소문자 , 대문자 , 숫자중 3가지를 포함, 9글자 이상 20글자 이하로 비밀번호를 작성해주세요")
    public String password;

}
