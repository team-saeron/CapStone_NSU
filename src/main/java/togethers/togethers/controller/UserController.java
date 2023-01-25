//package togethers.togethers.controller;
//
//import io.swagger.annotations.ApiParam;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import togethers.togethers.data.dto.MypageResultDto;
//import togethers.togethers.data.dto.SignUpResultDto;
//import togethers.togethers.service.MypageService;
//import togethers.togethers.service.UserService;
//
//@Controller
//@RequiredArgsConstructor
//public class UserController {
//
//    private final MypageService mypageService;
//    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
//
//    @PostMapping(value="/introduction")
//    public MypageResultDto userDetailForm(
//            @ApiParam(value="ID", required = true) @RequestParam String id,
//            @ApiParam(value="자기소개", required = true) @RequestParam String selfIntro,
//            @ApiParam(value="희망 룸메이트", required = true) @RequestParam String wish_roomate,
//            @ApiParam(value="월세", required = true) @RequestParam int monthly_fee,
//            @ApiParam(value="보증금", required = true) @RequestParam int lease_fee,
// //           @ApiParam(value="MBTI", required = true) @RequestParam int mbti,
//            @ApiParam(value="반려견 여부", required = true) @RequestParam int pet,
//            @ApiParam(value="흡연", required = true) @RequestParam int smoking,
//            @ApiParam(value="성별", required = true) @RequestParam boolean sex,
//            @ApiParam(value="생활 패턴", required = true) @RequestParam boolean lifeCycle,
//            @ApiParam(value="월 전세", required = true) @RequestParam boolean roomType
//    ){
//        LOGGER.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, role : {}, password : {}, email : {}, birth : {}, nickname : {}, phoneNum : {}",id,selfIntro, wish_roomate, roomType, pet, smoking,sex, lifeCycle, monthly_fee, lease_fee);
//        MypageResultDto mypageResultDto = mypageService.userDetailForm(id,selfIntro, wish_roomate, roomType, pet, smoking,sex, lifeCycle, monthly_fee, lease_fee);
//
//        LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", id);
//        return mypageResultDto;
//    }
//
//}
