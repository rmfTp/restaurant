
package org.koreait.admin.member.controllers;

import org.koreait.admin.global.controllers.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/member")
public class MemberController extends CommonController {

    @Override
    public String mainCode() {
        return "member";
    }

    @GetMapping({"", "/list"})
    public String list(Model model) {
        commonProcess("list", model);

        return "admin/member/list";
    }

    /**
     * 컨트롤러 공통 처리
     *
     * @param code
     * @param model
     */
    private void commonProcess(String code, Model model) {

    }
}
