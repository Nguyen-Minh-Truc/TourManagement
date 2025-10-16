package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Model.DTO.LoginDTO;
import com.J2EE.TourManagement.Model.DTO.ResLoginDTO;
import com.J2EE.TourManagement.Model.User;
import com.J2EE.TourManagement.Service.UserSer;
import com.J2EE.TourManagement.Util.SecurityUtil;
import com.J2EE.TourManagement.Util.annotation.ApiMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final SecurityUtil securityUtil;
  private final UserSer userService;

  @Value("${mt.jwt.refresh-token-validity-in-seconds}")
  private long refreshTokenExpiration;

  public AuthController(
      AuthenticationManagerBuilder authenticationManagerBuilder,
      SecurityUtil securityUtil, UserSer userService) {
    this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.securityUtil = securityUtil;
    this.userService = userService;
  }

  @PostMapping("/login")
  @ApiMessage("Đăng nhập thành công")
  public ResponseEntity<ResLoginDTO> login(@RequestBody LoginDTO loginDTO) {
    // Nạp input gồm username/password vào Security
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
                                                loginDTO.getPassword());

    // xác thực người dùng => cần viết hàm loadUserByUsername
    Authentication authentication =
        authenticationManagerBuilder.getObject().authenticate(
            authenticationToken);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    ResLoginDTO resLoginDTO = new ResLoginDTO();
    User currentUser = this.userService.getUserByName(loginDTO.getUsername());
    ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
        currentUser.getId(), currentUser.getEmail(), currentUser.getFullName());

    String access_token = this.securityUtil.createAccessToken(
        authentication.getName(), userLogin);
    resLoginDTO.setAccessToken(access_token);

    resLoginDTO.setUserLogin(userLogin);

    // create refresh Token
    String refreshToken = this.securityUtil.createRefreshToken(
        loginDTO.getUsername(), resLoginDTO);

    // update user
    this.userService.UpdateRefreshToken(refreshToken, loginDTO.getUsername());

    //  set cookies

    ResponseCookie responseCookie =
        ResponseCookie.from("refresh_Token", refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(refreshTokenExpiration)
            .build();
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
        .body(resLoginDTO);
  }

  @GetMapping("/auth/account")
  @ApiMessage("fetch Account")
  public ResponseEntity<?> getAccount() {
    String email = this.securityUtil.getCurrentUserLogin().isPresent()
                       ? this.securityUtil.getCurrentUserLogin().get()
                       : "";

    User currentUser = this.userService.getUserByName(email);
    ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
        currentUser.getId(), currentUser.getEmail(), currentUser.getFullName());
    return ResponseEntity.ok().body(userLogin);
  }

  @GetMapping("/auth/refresh")
  @ApiMessage("get user by refresh toke")
  public ResponseEntity<?>
  getRefreshToken(@CookieValue(name = "refresh_Token") String refresh_token) {
    Jwt decodedToken = this.securityUtil.checkValidRefreshToken(refresh_token);

    String email = decodedToken.getSubject();

    User user =
        this.userService.getUserByRefreshTokenAnhEmail(refresh_token, email);

    ResLoginDTO resLoginDTO = new ResLoginDTO();
    User currentUser = this.userService.getUserByName(email);
    ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
        currentUser.getId(), currentUser.getEmail(), currentUser.getFullName());

    String access_token = this.securityUtil.createAccessToken(email, userLogin);
    resLoginDTO.setAccessToken(access_token);

    resLoginDTO.setUserLogin(userLogin);

    // create refresh Token
    String newRefreshToken =
        this.securityUtil.createRefreshToken(email, resLoginDTO);

    // update user
    this.userService.UpdateRefreshToken(newRefreshToken, email);

    //  set cookies
    ResponseCookie responseCookie =
        ResponseCookie.from("refresh_Token", newRefreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(refreshTokenExpiration)
            .build();
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
        .body(resLoginDTO);
  }
}
