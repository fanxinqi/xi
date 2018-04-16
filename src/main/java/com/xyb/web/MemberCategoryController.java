package com.xyb.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController     // @RestController返回的是json数据，@Controller返回的是跳转控制(有模版引擎时使用)
@RequestMapping("/member_category") // 方法上的@RequestMapping用于区分不同的模块
public class MemberCategoryController {
}
