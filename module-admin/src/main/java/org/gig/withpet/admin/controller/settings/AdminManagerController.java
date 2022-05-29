package org.gig.withpet.admin.controller.settings;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.core.domain.user.administrator.AdministratorService;
import org.gig.withpet.core.domain.user.administrator.dto.AdminSearchDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : JAKE
 * @date : 2022/05/29
 */
@Controller
@RequestMapping("settings/admin-manager")
@Slf4j
@RequiredArgsConstructor
public class AdminManagerController {

    private final AdministratorService administratorService;

    @GetMapping
    public String list(AdminSearchDto searchDto, Model model) {
        model.addAttribute("pages", administratorService.getAdminPageListBySearch(searchDto));
        model.addAttribute("condition", searchDto);
        return "settings/administrator/list";
    }
}
