package com.fxg.house.viewer.api;

/**
 * <p>
 * 错误应答码定义
 * </p>
 */
public final class HttpStatus {

  private HttpStatus() {}

  /**
   * {@code 200 成功}
   */
  public static final int OK = 200;

  /**
   * {@code 400 客户端参数错误}
   */
  public static final int PARAM_ERROR = 400;
  
  /**
   * {@code 401 未登陆}
   */
  public static final int UN_LOGIN = 401;

  /**
   * {@code 403 鉴权失败}
   */
  public static final int AUTH_FAIL = 403;

  /**
   * {@code 404 资源不存在}
   */
  public static final int NOT_EXIST = 404;

  /**
   * {@code 500 系统内部错}
   */
  public static final int SERVER_ERROR = 500;

  /**
   * {@code 502 未知错误}
   */
  public static final int UKNOWN_ERROR = 502;
  
  /**
   * {@code 601 用户已注册}
   */
  public static final int SIGNED = 601;
  
  /**
   * {@code 602 用户未注册}
   */
  public static final int UN_SIGNED = 602;
  
  /**
   * {@code 603 用户未实名认证}
   */
  public static final int UN_VERI_ID = 603;
  
  /**
   * {@code 604 用户状态失效}
   */
  public static final int USER_DISABLE = 604;
  
}
