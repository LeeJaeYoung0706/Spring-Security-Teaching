package com.example.security_study.dto;

import com.example.security_study.domain.enums.AuthoritiesEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveMemberDTO {

    @Pattern(regexp = "^[a-zA-Z0-9]*$" , message = "영어 , 숫자 조합으로 아이디를 입력해주세요.")
    private String userId;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[A-Za-z\\d]{9,20}$|^(?=.*\\d)(?=.*[a-z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[a-z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*\\d)(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Za-z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Za-z!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$"
            , message = "특수문자, 소문자 , 대문자 , 숫자중 3가지를 포함, 9글자 이상 20글자 이하로 비밀번호를 작성해주세요")
    private String password;
    @NotNull
    private String name;

}
