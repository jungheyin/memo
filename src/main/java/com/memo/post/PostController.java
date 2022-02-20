package com.memo.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {

	@RequestMapping("/post_list_view")
	public String postView() {
		return "/template/layout";
	}
}
